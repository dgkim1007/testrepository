// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WebparsingActionListener.java

package com.naver.dgkim1007.dbtest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.swing.JTextArea;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// Referenced classes of package gui:
//            JavaThreadSysout

public class WebparsingActionListener implements ActionListener {

    WebparsingActionListener(JTextArea textarea) {
        this.textarea = textarea;
    }

    public void actionPerformed(ActionEvent e) {
        String pattern1 = "^[0-9]{1}$";
        System.out.println("Click....");
        try{
            Document doc = Jsoup.connect("http://www.naver.com").get();
            Elements contents = doc.select("tr td.td_list_white_l_01 , td.td_list_white_c_01 , td.td_list_sky_c_01 ,td.td_list_sky_c_02");
            String line = "";
            StringBuffer sb = new StringBuffer();
            for(Iterator iterator = contents.iterator(); iterator.hasNext();)
            {
                Element content = (Element)iterator.next();
                if(Pattern.matches(pattern1, content.text()))
                {
                    sb.append((new StringBuilder(String.valueOf(line))).append("\n").toString());
                    line = "";
                }
                System.out.println((new StringBuilder("-->")).append(content.text()).append("<--").toString());
                line = (new StringBuilder(String.valueOf(line))).append(content.text()).append("|").toString();
            }

            System.out.println(sb);
        }catch(IOException e1){
            e1.printStackTrace();
        }
    }

    JTextArea textarea;
}
