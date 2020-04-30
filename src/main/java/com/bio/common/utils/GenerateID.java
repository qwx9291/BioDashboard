package com.bio.common.utils;

import java.util.UUID;

/**
 *  生成ID工具
 */
public class GenerateID {
    public static String getId(){
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
           String i = "G:\\QMDownload\\Hotfix\\upload\\20200421\\客户问题1_CY2020042137401265.png";
        System.out.println(i.substring(i.lastIndexOf("\\")+1, i.lastIndexOf("_")) + i.substring(i.lastIndexOf("."), i.length()));

    }
}
