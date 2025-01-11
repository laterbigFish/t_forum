package com.example.forum.controller;

import com.example.forum.common.Result;
import com.example.forum.common.enmu.ResultCode;
import com.example.forum.common.model.User;
import com.example.forum.common.utils.encryption.DigestEncryption;
import com.example.forum.common.utils.encryption.UUIDUTIL;
import com.example.forum.service.IUserService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//返回的一个数据Controller

/**
 *
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private IUserService iUserService;
    /**
     * @param username 用户名
     * @param nickname 用户名称
     * @param password  密码
     * @param passwordRepeat 确认密码
     * @return
     */
    @PostMapping("/register")
  public Result register( @RequestParam("username") @NotNull String username,
                          @RequestParam("nickname") @NotNull  String nickname,
                          @RequestParam("password") @NotNull String password,
                          @RequestParam("passwordRepeat") @NotNull String passwordRepeat){

        if(!password.equals(passwordRepeat)) {
            return Result.FAIL(ResultCode.FAILED_TWO_PWD_NOT_SAME);
        }
        //进行注册
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        //处理密码    生成盐
        String salt = UUIDUTIL.UUID_32();
        //与原密码合并并进行加密
        String newPasswrod = DigestEncryption.md5(password, salt);
        user.setPassword(newPasswrod);
        user.setSalt(salt);
        iUserService.CreateNormalUser(user);

        return Result.SUCCESS();
    }


}
