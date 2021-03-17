package com.naver.dgkim1007;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naver.dgkim1007.dao.BuyDao;
import com.naver.dgkim1007.dao.DistributionDao;
import com.naver.dgkim1007.dao.ProductDao;
import com.naver.dgkim1007.dao.VenderDao;
import com.naver.dgkim1007.entities.Balance;
import com.naver.dgkim1007.entities.Buy;
import com.naver.dgkim1007.entities.Product;
import com.naver.dgkim1007.entities.Vender;

@Controller
public class DistributionController {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	Vender vender;

	@Autowired
	Buy buy;

	public Model selectDateForCalandar(Model model) {
		String yyyys[] = new String[5];
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		int start = year - 2;
		for (int i = 0; i < yyyys.length; i++) {
			yyyys[i] = start++ + "";
		}

		String mms[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
		String dds[] = new String[31];
		for (int i = 0; i < 31; i++) {
			dds[i] = String.format("%02d", i + 1);
		}

		String thisyyyy = year + "";

		String thismm = String.format("%02d", cal.get(Calendar.MONTH) + 1);
		String thisdd = String.format("%02d", cal.get(Calendar.DATE));
		model.addAttribute("yyyys", yyyys);
		model.addAttribute("mms", mms);
		model.addAttribute("dds", dds);
		model.addAttribute("thisyyyy", thisyyyy);
		model.addAttribute("thismm", thismm);
		model.addAttribute("thisdd", thisdd);
		return model;
	}

	@RequestMapping(value = "/finalDistribution")
	public String finalDistribution(Model model, Locale locale) {
		model = selectDateForCalandar(model);
		return "distribution/final_distribution";
	}

	@RequestMapping(value = "/yyyyFinal")
	public String yyyyFinal(Model model, Locale locale, @RequestParam String yyyy) throws Exception {
		DistributionDao dao = sqlSession.getMapper(DistributionDao.class);
		ProductDao daoproduct = sqlSession.getMapper(ProductDao.class);
		daoproduct.yyProductFinal();
		String nextyyyy = Integer.parseInt(yyyy) + 1 + "";
		dao.balanceYYFinalBeforeDelete(nextyyyy);
		ArrayList<Balance> balances = dao.balanceList(yyyy);
		dao.balanceYYFinal(balances);

		return "redirect:index";
	}

	@RequestMapping(value = "/mmFinal")
	public String mmFinal(Model model, @RequestParam String yyyy, @RequestParam String mm) throws Exception {
		DistributionDao dao = sqlSession.getMapper(DistributionDao.class);
		ProductDao daoproduct = sqlSession.getMapper(ProductDao.class);
		daoproduct.mmProductFinal();

		String nextmm = String.format("%02d", Integer.parseInt(mm) + 1);
		HashMap hash = new HashMap();
		hash.put("yyyy", yyyy);
		hash.put("columnpre", "prebalance" + nextmm);
		hash.put("columnbalance", "balance" + nextmm);
		dao.balanceMMFinal(hash);

		return "redirect:index";
	}

	@RequestMapping(value = "/ddFinal")
	public String ddFinal(Model model, Locale locale) throws Exception {
		ProductDao daoproduct = sqlSession.getMapper(ProductDao.class);
		daoproduct.ddProductFinal();
		return "redirect:index";
	}

	@RequestMapping(value = "/buyNew", method = RequestMethod.POST)
	@ResponseBody
	public Buy buyNew(@RequestParam String vendcode, Model model) throws Exception {
		BuyDao dao = sqlSession.getMapper(BuyDao.class);
		model = selectDateForCalandar(model);
		Calendar cal = Calendar.getInstance();
		String yyyy = cal.get(Calendar.YEAR) + "";
		String mm = String.format("%02d", cal.get(Calendar.MONTH) + 1);
		String dd = String.format("%02d", cal.get(Calendar.DATE));
		VenderDao daovender = sqlSession.getMapper(VenderDao.class);
		vender = daovender.selectOne(vendcode);
		buy.setVendcode(vendcode);
		buy.setVendname(vender.getName());
		buy.setYyyy(yyyy);
		buy.setMm(mm);
		buy.setDd(dd);
		int no = dao.getMaxNo(buy);
		int hang = 1;
		buy.setNo(no);
		buy.setHang(hang);
		return buy;
	}

	@RequestMapping(value = "/buyInsert", method = RequestMethod.GET)
	public String buyInsert(Locale locale, Model model) throws Exception {

		model = selectDateForCalandar(model);

		buy.setVendcode("0000");
		buy.setYyyy((String) model.getAttribute("thisyyyy"));
		buy.setMm((String) model.getAttribute("thismm"));
		buy.setDd((String) model.getAttribute("thisdd"));

		VenderDao dao = sqlSession.getMapper(VenderDao.class);
		ProductDao dao1 = sqlSession.getMapper(ProductDao.class);
		ArrayList<Vender> venders = dao.selectAll();
		ArrayList<Product> products = dao1.selectAll();

		model.addAttribute("venders", venders);
		model.addAttribute("buy", buy);
		model.addAttribute("products", products);
		return "distribution/buy_insert";
	}

	@RequestMapping(value = "/buyInsertSave", method = RequestMethod.POST)
	public String buyInsertSave(Model model, @ModelAttribute Buy buy) throws Exception {
		ProductDao daoproduct = sqlSession.getMapper(ProductDao.class);
		BuyDao daobuy = sqlSession.getMapper(BuyDao.class);
		daobuy.insertRow(buy);

		buy.setColumnname("buy" + buy.getMm());
		daoproduct.buyProductAdd(buy);

		buy.setDealname("deal" + buy.getMm());
		buy.setBalancename("balance" + buy.getMm());
		daobuy.buyBalanceAdd(buy);

		model = selectDateForCalandar(model);
		VenderDao daovender = sqlSession.getMapper(VenderDao.class);
		ArrayList<Vender> venders = daovender.selectAll();
		model.addAttribute("venders", venders);

		ArrayList<Product> products = daoproduct.selectAll();

		ArrayList<Buy> buyrollups = daobuy.selectBuyRollup(buy);
		model.addAttribute("buyrollups", buyrollups);
		buy.setHang(daobuy.getMaxHang(buy));

		model.addAttribute("buy", buy);
		model.addAttribute("products", products);
		return "distribution/buy_insert";
	}

	@RequestMapping(value = "/buyFind", method = RequestMethod.POST)
	public String buyFind(Locale locale, Model model, @RequestParam String hiddenvendname,
			@RequestParam String vendcodefind, @RequestParam String yyyyfind, @RequestParam String mmfind,
			@RequestParam String ddfind) throws Exception {
		model.addAttribute("vendcodefind", vendcodefind);
		model.addAttribute("yyyyfind", yyyyfind);
		model.addAttribute("mmfind", mmfind);
		model.addAttribute("ddfind", ddfind);

		VenderDao dao = sqlSession.getMapper(VenderDao.class);
		ProductDao dao1 = sqlSession.getMapper(ProductDao.class);
		ArrayList<Vender> venders = dao.selectAll();
		ArrayList<Product> products = dao1.selectAll();
		BuyDao daobuy = sqlSession.getMapper(BuyDao.class);

		ArrayList<Buy> buyrollups = daobuy.selectBuyFindRollup(model);
		model.addAttribute("buyrollups", buyrollups);
		model = selectDateForCalandar(model);
		model.addAttribute("venders", venders);
		buy.setVendname(hiddenvendname);
		model.addAttribute("buy", buy);
		model.addAttribute("products", products);

		return "distribution/buy_insert";
	}

	@RequestMapping(value = "/buyRowItemSelectedAjax", method = RequestMethod.POST)
	@ResponseBody
	public Buy buyRowItemSelectedAjax(@RequestParam int seq) throws Exception {
		BuyDao daobuy = sqlSession.getMapper(BuyDao.class);
		Buy buy = daobuy.selectOne(seq);
		return buy;
	}

	@RequestMapping(value = "/buyUpdateJsonAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map buyUpdateJsonAjax(@RequestBody Map<String, Object> json) throws Exception {
		BuyDao daobuy = sqlSession.getMapper(BuyDao.class);

		json.put("dealname", "deal" + json.get("mm"));
		json.put("columnname", "buy" + json.get("mm"));
		json.put("balancename", "balance" + json.get("mm"));

		daobuy.updateRow(json);
		ProductDao daoproduct = sqlSession.getMapper(ProductDao.class);
		daoproduct.buyProductSub(json);
		daoproduct.buyProductAddJson(json);
		int price = Integer.parseInt((String) json.get("price"));
		int qty = Integer.parseInt((String) json.get("qty"));
		json.put("qty", qty);
		json.put("price", price);
		daobuy.buyBalanceSub(json);
		daobuy.buyBalanceAjaxAdd(json);
		return json;
	}

	@RequestMapping(value = "/buyRowItemDeleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public String buyRowItemDeleteAjax(@RequestParam int seq) throws Exception {
		BuyDao daobuy = sqlSession.getMapper(BuyDao.class);
		daobuy.deleteAjax(seq);
		return "y";
	}

	@RequestMapping(value = "/balanceSelectyy", method = RequestMethod.GET)
	public String balanceSelectyy(Locale locale, Model model) throws Exception {
		String yyyys[] = new String[5];
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		int start = year - 2;
		for (int i = 0; i < yyyys.length; i++) {
			yyyys[i] = start++ + "";
		}
		String thisyyyy = year + "";
		model.addAttribute("yyyys", yyyys);
		model.addAttribute("thisyyyy", thisyyyy);
		return "distribution/balance_selectyy";
	}

	@RequestMapping(value = "/balanceList", method = RequestMethod.POST)
	public String balanceList(Locale locale, Model model, @RequestParam String yyyy) throws Exception {
		DistributionDao dao = sqlSession.getMapper(DistributionDao.class);
		ArrayList<Balance> balances = dao.balanceList(yyyy);
		model.addAttribute("balances", balances);
		return "distribution/balance_list";
	}

	@RequestMapping(value = "/balanceListGet", method = RequestMethod.GET)
	public String balanceListGet(Locale locale, Model model, @RequestParam String yyyy) throws Exception {
		DistributionDao dao = sqlSession.getMapper(DistributionDao.class);
		ArrayList<Balance> balances = dao.balanceList(yyyy);
		model.addAttribute("balances", balances);
		return "distribution/balance_list";
	}

	@RequestMapping(value = "/balanceUpdate", method = RequestMethod.GET)
	public String venderUpdate(Locale locale, Model model, @RequestParam String yyyy, @RequestParam String vendcode)
			throws Exception {
		HashMap hash = new HashMap();
		hash.putIfAbsent("yyyy", yyyy);
		hash.putIfAbsent("vendcode", vendcode);
		DistributionDao dao = sqlSession.getMapper(DistributionDao.class);
		Balance row = dao.balanceSelectOne(hash);
		model.addAttribute("row", row);
		return "distribution/balance_update";
	}

	@RequestMapping(value = "/balanceUpdateSave", method = RequestMethod.POST)
	public String venderUpdateSave(Model model, @ModelAttribute Balance balance, RedirectAttributes redirect)
			throws Exception {
		DistributionDao dao = sqlSession.getMapper(DistributionDao.class);
		dao.balanceUpdateRow(balance);
		redirect.addAttribute("yyyy", balance.getYyyy());
		return "redirect:balanceListGet";
	}

	@RequestMapping(value = "/balanceUpdateAjax", method = RequestMethod.POST)
	@ResponseBody
	public String balanceUpdateAjax(@RequestParam String yyyy, @RequestParam String vendcode, @RequestParam int balance)
			throws Exception {
		DistributionDao dao = sqlSession.getMapper(DistributionDao.class);
		HashMap hash = new HashMap();
		hash.putIfAbsent("yyyy", yyyy);
		hash.putIfAbsent("vendcode", vendcode);
		hash.putIfAbsent("balance", balance);

		int result = dao.balanceUpdateAjax(hash);
		if (result > 0) {
			return "y";
		} else {
			return "n.";
		}
	}

	@RequestMapping(value = "/balanceDeleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public String balanceDeleteAjax(@RequestParam String yyyy, @RequestParam String vendcode) throws Exception {
		DistributionDao dao = sqlSession.getMapper(DistributionDao.class);
		HashMap hash = new HashMap();
		hash.putIfAbsent("yyyy", yyyy);
		hash.putIfAbsent("vendcode", vendcode);

		int result = dao.balanceDeleteAjax(hash);
		if (result > 0) {
			return "y";
		} else {
			return "n.";
		}
	}

	@RequestMapping(value = "/productSelectedAjax", method = RequestMethod.POST)
	@ResponseBody
	public Product productSelectedAjax(@RequestParam String code) throws Exception {
		ProductDao dao = sqlSession.getMapper(ProductDao.class);
		Product product = dao.selectOne(code);
		return product;
	}
}
