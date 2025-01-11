package com.example.forum.common.utils.encryption;

public class DigestEncryption {

    //使用apche的依赖实现md5加密

    /**
     * 对字符串进行加密
     * @param Password
     * @return
     */
    public  static  String md5(String Password){
        return org.apache.commons.codec.digest.DigestUtils.md2Hex(Password);   //发挥进行加密后的原文
    }

    /**
     * 对用户密码进行加密
     * @param password
     * @param salt
     * @return
     */
    public static String md5(String password,String salt){
        return  md5(md5(password) + salt);
    }
}
