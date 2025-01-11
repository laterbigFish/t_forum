package com.example.forum.common.utils;

public class StringUtil {
    /**
     * 判断字符串是否为空
     * @param str
     * @return    ture 为空  <br/>false 不为空
     */
    public static boolean isEmpty(String str){
        return str == null || str.length() == 0;
    }

}
