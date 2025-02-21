package com.example.forum.service.impl;

import com.example.forum.common.model.User;
import com.example.forum.common.utils.encryption.DigestEncryption;
import com.example.forum.common.utils.encryption.UUIDUTIL;
import com.example.forum.service.IUserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class UserServiceImplTest {
   @Resource
   private IUserService iUserService;
    @Autowired
    private UserServiceImpl userServiceImpl;

   @Test
    void createNormalUser(){
        User user = new User();
        user.setUsername("lisi3");
        user.setNickname("lisi3");
        String password = "123456";
        String s = UUIDUTIL.UUID_32();
        String s1 = DigestEncryption.md5(password, s);
        user.setPassword(s1);
        user.setSalt(s);

       iUserService.CreateNormalUser(user);

        System.out.println(user.toString());
    }

    @Test
    void login(){

        User lisi = iUserService.login("lisi3", "123456");
        System.out.println(lisi);
    }

    @Test
    void modifyInfo(){
       User user = new User();
       user.setId(11L);
       user.setUsername("lisi10");
       userServiceImpl.modifyInfo(user);
    }
}