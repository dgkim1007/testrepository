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

import com.naver.dgkim1007.dao.VenderDao;
import com.naver.dgkim1007.entities.Vender;

@Controller
public class VenderController {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	Vender vender;

	@RequestMapping(value = "/vendercodeConfirmAjax", method = RequestMethod.POST)
	@ResponseBody
	public String codeConfirmAjax(@RequestParam String code) throws Exception {
		VenderDao dao = sqlSession.getMapper(VenderDao.class);
		Vender data = dao.selectOne(code);
		String row = "y";
		if (data == null) {
			row = "n";
		}
		return row;
	}

	@RequestMapping(value = "/venderInsert", method = RequestMethod.GET)
	public String venderInsert(Locale locale, Model model) {
		return "distribution/vender_insert";
	}

	@RequestMapping(value = "/venderInsertSave", method = RequestMethod.POST)
	public String vender_insertSave(Model model, @ModelAttribute Vender vender) throws Exception {
		VenderDao dao = sqlSession.getMapper(VenderDao.class);
		dao.insertRow(vender);
		return "index";
	}

	@RequestMapping(value = "/venderUpdate", method = RequestMethod.GET)
	public String venderUpdate(Locale locale, Model model, @RequestParam String code) throws Exception {

		VenderDao dao = sqlSession.getMapper(VenderDao.class);
		Vender row = dao.selectOne(code);
		model.addAttribute("row", row);
		return "distribution/vender_update";
	}

	@RequestMapping(value = "/venderUpdateSave", method = RequestMethod.POST)
	public String venderUpdateSave(Model model, @ModelAttribute Vender vender) throws Exception {
		VenderDao dao = sqlSession.getMapper(VenderDao.class);
		dao.updateRow(vender);
		return "redirect:venderList";
	}

	@RequestMapping(value = "/venderList", method = RequestMethod.GET)
	public String venderList(Locale locale, Model model) throws Exception {
		VenderDao dao = sqlSession.getMapper(VenderDao.class);
		ArrayList<Vender> venders = dao.selectAll();
		model.addAttribute("venders", venders);
		return "distribution/vender_list";
	}

	@RequestMapping(value = "/venderUpdateAjax", method = RequestMethod.POST)
	@ResponseBody
	public String venderUpdateAjax(@RequestParam String code, int stock) throws Exception {
		VenderDao dao = sqlSession.getMapper(VenderDao.class);
		vender.setCode(code);
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

	@RequestMapping(value = "/venderDeleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public String venderDeleteAjax(@RequestParam String code) throws Exception {
		VenderDao dao = sqlSession.getMapper(VenderDao.class);
		int result = dao.deleteAjax(code);
		if (result > 0) {
			return "y";
		} else {
			return "n.";
		}
	}

}
