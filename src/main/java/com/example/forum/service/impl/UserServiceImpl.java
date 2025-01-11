package com.example.forum.service.impl;

import com.example.forum.common.Result;
import com.example.forum.common.dao.UserMapper;
import com.example.forum.common.enmu.ResultCode;
import com.example.forum.common.exception.ForumException;
import com.example.forum.common.model.User;
import com.example.forum.service.IUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public void CreateNormalUser( User user ){
        //做一个非空校验
        if(user==null || !StringUtils.hasLength(user.getUsername())
           || !StringUtils.hasLength(user.getNickname())
                || !StringUtils.hasLength(user.getPassword())
           || !StringUtils.hasLength(user.getSalt())){
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.toString());
            //抛出异常
            throw new ForumException(Result.FAIL(ResultCode.FAILED_PARAMS_VALIDATE));  //抛出异常
        }
      //查询用户名
        User exist = userMapper.selectByUserName(user.getUsername());
        //判断数据是否存在
        if(exist!=null){
            log.warn(Result.FAIL(ResultCode.FAILED_NOT_EXISTS).toString());
            throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));
        }
        //新增用户  添加默认值
        user.setGender((byte)2);
        user.setArticleCount(0);
        user.setIsAdmin((byte)0);
        user.setState((byte)0);
        user.setDeleteState((byte)0);
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        //进行插入数据库

        int insert = userMapper.insert(user);

        if(insert!=1){
            log.info(ResultCode.FAILED_CREATE.toString());
            throw new ForumException(Result.FAIL(ResultCode.FAILED_CREATE.toString()));
        }

        log.info("用户添加成功{}", ResultCode.SUCCESS.toString());
    }
}
