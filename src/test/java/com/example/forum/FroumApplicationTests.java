package com.example.forum;

import com.example.forum.common.Result;
import com.example.forum.common.dao.UserMapper;
import com.example.forum.common.enmu.ResultCode;
import com.example.forum.common.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/test")
@SpringBootTest
class FroumApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads(){
        User user = userMapper.selectByPrimaryKey(1L);
        System.out.println(user.toString());
        System.out.println(user.getUsername());
    }
    @RequestMapping("/test1")

    public Result t1(){
        Result result = new Result();
        result.setMessage("测试成功");
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }
    @Test
     public void fucn3(){
        System.out.println("landward");
    }
}
