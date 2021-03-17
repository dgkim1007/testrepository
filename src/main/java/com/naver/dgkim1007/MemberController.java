package com.naver.dgkim1007;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.naver.dgkim1007.dao.MemberDao;
import com.naver.dgkim1007.entities.Member;

@Controller
public class MemberController {
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	Member member;

	@RequestMapping(value = "/emailConfirmAjax", method = RequestMethod.POST)
	@ResponseBody
	public String emailConfirmAjax(@RequestParam String email) throws Exception {
		MemberDao dao = sqlSession.getMapper(MemberDao.class);
		Member data = dao.selectOne(email);
		String row = "y";
		if (data == null) {
			row = "n";
		}
		return row;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Locale locale, Model model) {
		return "login/login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, Locale locale) {
		session.invalidate();
		return "redirect:index";
	}

	@RequestMapping(value = "/loginUp", method = RequestMethod.POST)
	public String loginup(Locale locale, Model model, @ModelAttribute Member member, HttpSession session)
			throws Exception {
		MemberDao dao = sqlSession.getMapper(MemberDao.class);
		Member data = dao.selectOne(member.getEmail());
		if (data == null) {
			return "login/login_fail";
		} else {
			boolean passchk = BCrypt.checkpw(member.getPassword(), data.getPassword());
			if (passchk) {
				String curlang = (String) session.getAttribute("lang");
				if (curlang == null || curlang.equals("")) {
					session.setAttribute("lang", "kr");
					locale = Locale.KOREAN;
				}
				session.setAttribute("sessionemail", data.getEmail());
				session.setAttribute("sessionname", data.getName());
				session.setAttribute("sessionphoto", data.getPhoto());
				session.setAttribute("sessionlevel", data.getMemlevel());
				return "redirect:index";
			} else {
				return "login/login_fail";
			}
		}
	}

	@RequestMapping(value = "/memberInsert", method = RequestMethod.GET)
	public String memberInsert(Locale locale, Model model) {
		return "member/member_insert";
	}

	@RequestMapping(value = "/member_insertSave", method = RequestMethod.POST)
	public String member_insertSave(Model model, @ModelAttribute Member member) throws Exception {
		if (member.getPhoto() == null) {
			member.setPhoto("/images/noimage.png");
		}
		MemberDao dao = sqlSession.getMapper(MemberDao.class);

		String encodepassword = hashPassword(member.getPassword());
		member.setPassword(encodepassword);
		String authNum = randomNum();
		String content = "회원가입을 환영합니다."+" 인증번호 :"+authNum;
		
		sendEmail( member.getEmail(), content, authNum);
		
		dao.insertRow(member);
		return "index";
	}

	private String hashPassword(String plainTextPassword) {
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

	@RequestMapping(value = "/memberUpdate", method = RequestMethod.GET)
	public String memberUpdate(Locale locale, Model model, HttpSession session) throws Exception {
		String email = (String) session.getAttribute("sessionemail");

		MemberDao dao = sqlSession.getMapper(MemberDao.class);
		Member row = dao.selectOne(email);
		model.addAttribute("row", row);
		return "member/member_update";
	}

	@RequestMapping(value = "/memberUpdateSave", method = RequestMethod.POST)
	public String memberUpdateSave(Model model, @ModelAttribute Member member,
			@RequestParam("imgfile") MultipartFile imgfile) throws Exception {
		String filename = imgfile.getOriginalFilename();
		String path = "D:/UTIL/SPRINGBOOTSOURCE/eyeconspringboot_17/src/main/resources/static/uploadimages/";
		String realpath = "/uploadimages/";
		
		if (filename.equals("")) {
			member.setPhoto(member.getOldphoto());
		} else {
			String cutemail = member.getEmail().substring(0, member.getEmail().indexOf("@"));
			byte bytes[] = imgfile.getBytes();
			try {
				BufferedOutputStream output = new BufferedOutputStream(
						new FileOutputStream(path + cutemail + filename));
				output.write(bytes);
				output.flush();
				output.close();
				member.setPhoto(realpath + cutemail + filename);
				if (!member.getPassword().equals(member.getOldpassword())) {
					String encodepassword = hashPassword(member.getPassword());
					member.setPassword(encodepassword);
				}

				MemberDao dao = sqlSession.getMapper(MemberDao.class);
				dao.updateRow(member);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return "index";
	}

	@RequestMapping(value = "/memberList", method = RequestMethod.GET)
	public String memberList(Locale locale, Model model) throws Exception {
		MemberDao dao = sqlSession.getMapper(MemberDao.class);
		ArrayList<Member> members = dao.selectAll();
		model.addAttribute("members", members);
		return "member/member_list";
	}

	@RequestMapping(value = "/memberUpdateAjax", method = RequestMethod.POST)
	@ResponseBody
	public String memberUpdateAjax(@RequestParam String email, int level) throws Exception {
		MemberDao dao = sqlSession.getMapper(MemberDao.class);
		member.setEmail(email);
		member.setMemlevel(level);
		int result = dao.updateAjax(member);
		if (result > 0) {
			return "y";
		} else {
			return "n.";
		}
	}

	@RequestMapping(value = "/memberDeleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public String memberDeleteAjax(@RequestParam String email) throws Exception {
		MemberDao dao = sqlSession.getMapper(MemberDao.class);
		int result = dao.deleteAjax(email);
		if (result > 0) {
			return "y";
		} else {
			return "n.";
		}
	}

	private void sendEmail(String email, String content, String authNum) {
		String host = "smtp.gmail.com";
		String subject = "mysite 인증번호";
		String fromName = "관리자";
		String from = "ezos1007@gmail.com";
		String to1 = email;
		try {
			Properties props = new Properties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", host);
			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.port", "587"); // or 465
			props.put("mail.smtp.user", from);
			props.put("mail.smtp.auth", "true");

			Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("ezpos1007", "xcibgvrlyaoaccqj"); // 위 gmail에서 생성된 비밀번호 넣는다
				}
			});
			Message msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(from, MimeUtility.encodeText(fromName, "UTF-8", "B")));

			InternetAddress[] address1 = { new InternetAddress(to1) };
			msg.setRecipients(Message.RecipientType.TO, address1);
			msg.setSubject(subject);
			msg.setSentDate(new java.util.Date());
			msg.setContent(content, "text/html;charset=euc-kr");
			Transport.send(msg);
		} catch (Exception e) {
		}
	}
	
	String randomNum() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i <= 3; i++) {
			int n = (int) (Math.random() * 10);
			buffer.append(n);
		}
		return buffer.toString();
	}


}
