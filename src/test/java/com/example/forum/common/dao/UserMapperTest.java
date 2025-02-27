package com.example.forum.common.dao;

import com.example.forum.common.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserMapperTest {
    @Resource
    private UserMapper userMapper;

    @Test
    void selectByUserName(){
//        User user = userMapper.selectByUserName("111");
        User user = userMapper.selectByPrimaryKey(1L);

        System.out.println(user);
    }
}