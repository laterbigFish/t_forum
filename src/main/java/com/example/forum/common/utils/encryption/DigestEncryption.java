package com.example.forum.common.utils.encryption;


import com.example.forum.common.exception.ForumException;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class DigestEncryption {
    /**
     * 加密
     *
     */
    public static String encrypt(String password) throws Exception{
        if (!StringUtils.hasLength(password)){
            throw new ForumException("密码不能为空");
        }
        //生成盐值
        String salt = UUID.randomUUID().toString().replace("-", "");
        //md5(盐值+password), 得到32位16进制的密文
        String securityPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes(StandardCharsets.UTF_8));
        //数据库中应存储 盐值和密文   得到64位16进制的数据
        return salt+securityPassword;
    }

    /**
     * 验证
     */
    public static boolean verify(String inputPassword, String sqlPassword){
        //参数校验
        if (!StringUtils.hasLength(inputPassword) || !StringUtils.hasLength(sqlPassword)){
            return false;
        }
        if (sqlPassword.length()!=64){
            return false;
        }
        //获取salt
        String salt = sqlPassword.substring(0, 32);
        //md5(盐值+inputPassword), 得到32位16进制的密文
        String securityPassword = DigestUtils.md5DigestAsHex((salt + inputPassword).getBytes(StandardCharsets.UTF_8));

        return sqlPassword.equals(salt+securityPassword);
    }

}
