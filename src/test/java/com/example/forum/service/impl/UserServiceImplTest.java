package com.example.forum.service.impl;

import com.example.forum.common.model.User;
import com.example.forum.common.utils.encryption.DigestEncryption;
import com.example.forum.common.utils.encryption.UUIDUTIL;
import com.example.forum.service.IUserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {
   @Resource
   private IUserService iUserService;
    @Test
    void createNormalUser(){
        User user = new User();
        user.setUsername("长安3");
        user.setNickname("1234");
        String password = "123456";
        String s = UUIDUTIL.UUID_32();
        String s1 = DigestEncryption.md5(password, s);
        user.setPassword(s1);
        user.setSalt(s);

       iUserService.CreateNormalUser(user);

        System.out.println(user.toString());
    }
}