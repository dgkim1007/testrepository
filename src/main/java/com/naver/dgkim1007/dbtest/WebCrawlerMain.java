package com.naver.dgkim1007.dbtest;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebCrawlerMain {
	public static void main(String args[]) {
		try {
			Connection.Response response = (Response) Jsoup
					.connect("https://dhlottery.co.kr/gameResult.do?method=byWin").method(Connection.Method.GET)
					.execute();
			Document document = response.parse();

			Element element = document.select("meta[name=description]").first();
			String str = element.attr("content");
			String lottoinning = str.substring(5, 8);
			String lottonostr = str.substring(10, 35);
			lottonostr = lottonostr.replace("+", ",");
			lottonostr = lottonostr.replace(".", "");
			lottonostr = lottonostr.replace("당첨번호", "");

			String html = document.html();
			String text = document.text();
			System.err.println("type:" + str);
			System.out.println("inning:" + lottoinning);
			System.out.println("lottonostr:" + lottonostr);

		} catch (IOException e1) {

			e1.printStackTrace();
		}
	}

}
