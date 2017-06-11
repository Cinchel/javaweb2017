package com.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;

@Service
@Transactional
public class TermService {
    String filepath = TermService.class.getResource("").getFile() + "term.properties";

    public String getTermSelect() {
        Calendar now = Calendar.getInstance();
        int baseYear = now.get(Calendar.YEAR) - 3;
        String term_select = "";
        for (int year = baseYear; year < baseYear + 5; year++) {
            if (now.get(Calendar.MONTH) < 5 && year == baseYear + 2) {
                term_select = term_select + "<option>" + year + " - " + (year + 1) + " - 1</option>";
                term_select = term_select + "<option selected>" + year + " - " + (year + 1) + " - 2</option>";
            } else if (now.get(Calendar.MONTH) >= 5 && year == baseYear + 3) {
                term_select = term_select + "<option selected>" + year + " - " + (year + 1) + " - 1</option>";
                term_select = term_select + "<option>" + year + " - " + (year + 1) + " - 2</option>";
            } else {
                term_select = term_select + "<option>" + year + " - " + (year + 1) + " - 1</option>";
                term_select = term_select + "<option>" + year + " - " + (year + 1) + " - 2</option>";
            }
        }
        return term_select;
    }

    public String getTermConfig(String term) throws IOException {
        Properties pps = new Properties();
        InputStream in = new BufferedInputStream(new FileInputStream(filepath));
        pps.load(in);
        String value = pps.getProperty(term);
        System.out.println(term + " = " + value);
        return value;
    }

    public void setTermConfig(String term, String baseDate) throws IOException {
        Properties pps = new Properties();
        InputStream in = null;
        in = new FileInputStream(filepath);
        //从输入流中读取属性列表（键和元素对）
        pps.load(in);
        //调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
        //强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
        OutputStream out = new FileOutputStream(filepath);
        pps.setProperty(term, baseDate);
        //以适合使用 load 方法加载到 Properties 表中的格式，
        //将此 Properties 表中的属性列表（键和元素对）写入输出流
        pps.store(out, "Update 222 " + term + " name");
    }
}
