package com.naver.dgkim1007;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.naver.dgkim1007.dao.BoardDao;
import com.naver.dgkim1007.entities.Board;
import com.naver.dgkim1007.entities.BoardPaging;

@Controller
public class BoardController {
	@Autowired
	private SqlSession sqlSession;

	@Autowired
	Board board;

	@Autowired
	BoardPaging boardpaging;

	public static String find;

	@RequestMapping(value = "/boardWrite", method = RequestMethod.GET)
	public String boardWrite(Locale locale, Model model) {
		System.out.println();
		return "board/board_write";
	}

	@RequestMapping(value = "/boardWriteSave", method = RequestMethod.POST)
	public String boardWriteSave(Model model, @ModelAttribute Board board,
			@RequestParam("b_attachfile") MultipartFile b_attachfile, HttpServletRequest request) throws Exception {
		String filename = b_attachfile.getOriginalFilename();
		String path = "D:/UTIL/SPRINGBOOTSOURCE/eyeconspringboot_17/src/main/resources/static/uploadattachs/";
		String realpath = "/uploadattachs/";
		if (!filename.equals("")) {
			byte bytes[] = b_attachfile.getBytes();
			try {
				BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(path + filename));
				output.write(bytes);
				output.flush();
				output.close();
				board.setB_attach(realpath + filename);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		String ip = getIp(request);
		board.setB_inputip(ip);
		SimpleDateFormat df = new SimpleDateFormat("yyyy??? MM??? dd??? hh??? mm:ss");
		Date date = new Date();
		String today = df.format(date);
		board.setB_inputtime(today);
		BoardDao dao = sqlSession.getMapper(BoardDao.class);
		dao.insertRow(board);

		return "redirect:boardPageList";
	}

	@RequestMapping(value = "/boardDetail", method = RequestMethod.GET)
	public String boardDetail(Model model, @RequestParam int b_seq, HttpSession session) throws Exception {
		BoardDao dao = sqlSession.getMapper(BoardDao.class);
		board = dao.selectOne(b_seq);
		String cursession = (String) session.getAttribute("sessionemail");
		if (!cursession.equals(board.getB_email())) {
			dao.addHit(b_seq);
		}

		model.addAttribute("board", board);
		return "board/board_detail";
	}

	@RequestMapping(value = "/boardUpdateSave", method = RequestMethod.POST)
	public String boardUpdateSave(Model model, @ModelAttribute Board board) throws Exception {
		BoardDao dao = sqlSession.getMapper(BoardDao.class);
		dao.updateRow(board);
		return "redirect:boardPageList";
	}

	@RequestMapping(value = "/replyWrite", method = RequestMethod.GET)
	public String replyWrite(Model model, @RequestParam int b_seq) throws Exception {
		BoardDao dao = sqlSession.getMapper(BoardDao.class);
		board = dao.selectOne(b_seq);
		board.setB_title("[??????] " + board.getB_title());
		model.addAttribute("board", board);
		return "board/board_reply";
	}

	@RequestMapping(value = "/replyWriteSave", method = RequestMethod.POST)
	public String replyWriteSave(Model model, @ModelAttribute Board board,
			@RequestParam("b_attachfile") MultipartFile b_attachfile, HttpServletRequest request) throws Exception {
		String filename = b_attachfile.getOriginalFilename();
		String path = "D:/UTIL/SPRINGBOOTSOURCE/eyeconspringboot_17/src/main/resources/static/uploadattachs/";
		String realpath = "/uploadattachs/";
		if (!filename.equals("")) {
			byte bytes[] = b_attachfile.getBytes();
			try {
				BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(path + filename));
				output.write(bytes);
				output.flush();
				output.close();
				board.setB_attach(realpath + filename);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		String ip = getIp(request);
		board.setB_inputip(ip);
		SimpleDateFormat df = new SimpleDateFormat("yyyy??? MM??? dd??? hh??? mm:ss");
		Date date = new Date();
		String today = df.format(date);
		board.setB_inputtime(today);
		BoardDao dao = sqlSession.getMapper(BoardDao.class);
		dao.insertReplyRow(board);

		return "redirect:boardPageList";
	}

	@RequestMapping(value = "/boardDelete", method = RequestMethod.GET)
	public String boardDelete(@RequestParam int b_seq) throws Exception {
		BoardDao dao = sqlSession.getMapper(BoardDao.class);
		dao.deleteRow(b_seq);
		return "redirect:boardPageList";
	}

	@RequestMapping(value = "/boardPageList", method = RequestMethod.GET)
	public String boardPageList(Locale locale, Model model) throws Exception {
		BoardDao dao = sqlSession.getMapper(BoardDao.class);

		int pageSize = 10;
		int page = 1;
		int startrow = (page - 1) * pageSize;
		int endrow = 10;
		if (boardpaging.getFind() == null) {
			boardpaging.setFind("");
		}
		boardpaging.setStartrow(startrow);
		boardpaging.setEndrow(endrow);
		int rowcount = dao.selectCountFirst();
		int absPage = 1;
		if (rowcount % pageSize == 0)
			absPage = 0;

		int pageCount = rowcount / pageSize + absPage;
		int pages[] = new int[pageCount];
		for (int i = 0; i < pageCount; i++) {
			pages[i] = i + 1;
		}

		ArrayList<Board> boards = dao.selectPageList(boardpaging);

		model.addAttribute("boards", boards);
		model.addAttribute("pages", pages);
		return "board/board_page_list";
	}

	@RequestMapping(value = "/findListBoard", method = RequestMethod.POST)
	public String findListBoard(Locale locale, Model model, @RequestParam String find) throws Exception {
		BoardDao dao = sqlSession.getMapper(BoardDao.class);

		int pageSize = 10;
		int page = 1;
		int startrow = (page - 1) * pageSize;
		int endrow = 10;
		this.find = find;
		boardpaging.setFind(find);
		if (boardpaging.getFind() == null) {
			boardpaging.setFind("");
		}
		boardpaging.setStartrow(startrow);
		boardpaging.setEndrow(endrow);
		int rowcount = dao.selectCount(boardpaging);
		int absPage = 1;
		if (rowcount % pageSize == 0)
			absPage = 0;

		int pageCount = rowcount / pageSize + absPage;

		int pages[] = new int[pageCount];
		for (int i = 0; i < pageCount; i++) {
			pages[i] = i + 1;
		}

		ArrayList<Board> boards = dao.findListBoard(boardpaging);
		model.addAttribute("boards", boards);
		model.addAttribute("pages", pages);
		model.addAttribute("find", find);
		return "board/board_page_list";
	}

	@RequestMapping(value = "/boardPageSelect", method = RequestMethod.GET)
	public String boardPageSelect(Locale locale, Model model, @RequestParam int page) throws Exception {
		BoardDao dao = sqlSession.getMapper(BoardDao.class);
		int pageSize = 10;
		int startrow = (page - 1) * pageSize;
		int endrow = 10;

		boardpaging.setFind(find);
		if (boardpaging.getFind() == null) {
			boardpaging.setFind("");
		}
		boardpaging.setStartrow(startrow);
		boardpaging.setEndrow(endrow);
		int rowcount = dao.selectCount(boardpaging);
		System.out.println("select count:" + rowcount);
		int absPage = 1;
		if (rowcount % pageSize == 0)
			absPage = 0;

		int pageCount = rowcount / pageSize + absPage;

		int pages[] = new int[pageCount];
		for (int i = 0; i < pageCount; i++) {
			pages[i] = i + 1;
		}

		ArrayList<Board> boards = dao.findListBoard(boardpaging);
		model.addAttribute("boards", boards);
		model.addAttribute("pages", pages);
		model.addAttribute("find", find);
		return "board/board_page_list";
	}

	private String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP"); // ?????????
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	@RequestMapping(value = "/fileDownload")
	@ResponseBody
	public void fileDownload(@RequestParam String b_attach, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("UTF-8");
		File file = new File("D:/UTIL/SPRINGBOOTSOURCE/eyeconspringboot_17/src/main/resources/static" + b_attach);

		// ?????? ????????? ?????????
		String oriFileName = file.getName();
		String filePath = "D:/UTIL/SPRINGBOOTSOURCE/eyeconspringboot_17/src/main/resources/static/uploadattachs/";
		InputStream in = null;
		OutputStream os = null;
		File newfile = null;
		boolean skip = false;
		String client = "";

		// ????????? ?????? ???????????? ??????
		try {
			newfile = new File(filePath, oriFileName);
			in = new FileInputStream(newfile);
		} catch (FileNotFoundException fe) {
			skip = true;
		}

		client = request.getHeader("User-Agent");
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Description", "HTML Generated Data");

		if (!skip) {
			// IE
			if (client.indexOf("MSIE") != -1) {
				response.setHeader("Content-Disposition", "attachment; filename=\""
						+ java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
				// IE 11 ??????.
			} else if (client.indexOf("Trident") != -1) {
				response.setHeader("Content-Disposition", "attachment; filename=\""
						+ java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
			} else {
				// ?????? ????????? ??????
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + new String(oriFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
				response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
			}
			response.setHeader("Content-Length", "" + file.length());
			os = response.getOutputStream();
			byte b[] = new byte[(int) file.length()];
			int leng = 0;
			while ((leng = in.read(b)) > 0) {
				os.write(b, 0, leng);
			}
		} else {
			response.setContentType("text/html;charset=UTF-8");
			System.out.println("<script language='javascript'>alert('????????? ?????? ??? ????????????');history.back();</script>");
		}
		in.close();
		os.close();
	}
}
