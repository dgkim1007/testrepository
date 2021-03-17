package com.naver.dgkim1007;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naver.dgkim1007.dao.ProductDao;
import com.naver.dgkim1007.entities.Product;

@Controller
public class ProductController {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	Product product;

	@RequestMapping(value = "/productcodeConfirmAjax", method = RequestMethod.POST)
	@ResponseBody
	public String codeConfirmAjax(@RequestParam String code) throws Exception {
		ProductDao dao = sqlSession.getMapper(ProductDao.class);
		Product data = dao.selectOne(code);
		String row = "y";
		if (data == null) {
			row = "n";
		}
		return row;
	}

	@RequestMapping(value = "/productInsert", method = RequestMethod.GET)
	public String productInsert(Locale locale, Model model) {
		return "product/product_insert";
	}

	@RequestMapping(value = "/product_insertSave", method = RequestMethod.POST)
	public String product_insertSave(Model model, @ModelAttribute Product product) throws Exception {
		ProductDao dao = sqlSession.getMapper(ProductDao.class);

		dao.insertRow(product);
		return "index";
	}

	@RequestMapping(value = "/productUpdate", method = RequestMethod.GET)
	public String productUpdate(Locale locale, Model model, @RequestParam String code) throws Exception {

		ProductDao dao = sqlSession.getMapper(ProductDao.class);
		Product row = dao.selectOne(code);
		model.addAttribute("row", row);
		return "product/product_update";
	}

	@RequestMapping(value = "/productUpdateSave", method = RequestMethod.POST)
	public String productUpdateSave(Model model, @ModelAttribute Product product) throws Exception {
		ProductDao dao = sqlSession.getMapper(ProductDao.class);
		dao.updateRow(product);
		return "redirect:productList";
	}

	@RequestMapping(value = "/productList", method = RequestMethod.GET)
	public String productList(Locale locale, Model model) throws Exception {
		ProductDao dao = sqlSession.getMapper(ProductDao.class);
		ArrayList<Product> products = dao.selectAll();
		model.addAttribute("products", products);
		return "product/product_list";
	}

	@RequestMapping(value = "/productUpdateAjax", method = RequestMethod.POST)
	@ResponseBody
	public String productUpdateAjax(@RequestParam String code, int stock) throws Exception {
		ProductDao dao = sqlSession.getMapper(ProductDao.class);
		product.setCode(code);
		HashMap hash = new HashMap();
		hash.putIfAbsent("code", code);
		hash.putIfAbsent("stock", stock);
		int result = dao.updateAjax(hash);
		if (result > 0) {
			return "y";
		} else {
			return "n.";
		}
	}

	@RequestMapping(value = "/productDeleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public String productDeleteAjax(@RequestParam String code) throws Exception {
		ProductDao dao = sqlSession.getMapper(ProductDao.class);
		int result = dao.deleteAjax(code);
		if (result > 0) {
			return "y";
		} else {
			return "n.";
		}
	}

}
