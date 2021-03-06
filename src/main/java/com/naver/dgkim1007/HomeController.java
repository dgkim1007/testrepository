package com.naver.dgkim1007;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@RequestMapping(value = "/")
	public String home(Model model, Locale locale, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		String curlang = (String) session.getAttribute("lang");

		if (curlang == null) {
			session.setAttribute("lang", "en");
			locale = Locale.ENGLISH;
		}
		return "index";
	}

	@RequestMapping(value = "/index")
	public String index(Model model, Locale locale, HttpSession session) {
		return "index";
	}

	@RequestMapping(value = "/languageAjax")
	@ResponseBody
	public String languageAjax(Locale locale, @RequestParam String lang, HttpSession session) {
		System.out.println("------>>>>lang:" + lang);
		session.setAttribute("lang", lang);
		return (String) session.getAttribute("lang");
	}

}