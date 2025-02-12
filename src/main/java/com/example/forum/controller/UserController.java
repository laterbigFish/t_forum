package com.example.forum.controller;

import com.example.forum.common.Result;
import com.example.forum.common.config.AppConfig;
import com.example.forum.common.enmu.ResultCode;
import com.example.forum.common.exception.ForumException;
import com.example.forum.common.model.User;
import com.example.forum.common.utils.encryption.DigestEncryption;
import com.example.forum.common.utils.encryption.UUIDUTIL;
import com.example.forum.service.IUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
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

    /**
     *   用户登录
     * @param username  用户名
     * @param password   密码
     * @return
     */
   @PostMapping(value = "/login",produces = "application/json")
    public Result login( HttpServletRequest httpServletRequest,
                        @RequestParam("username")@NotNull String username,
                         @RequestParam("password")@NotNull String password){
       //调用Service中的登录方法
       User user = iUserService.login(username, password);

       if(user==null){
           log.warn("登录失败{}", ResultCode.FAILED_LOGIN);
           throw new ForumException(Result.FAIL(ResultCode.FAILED_LOGIN));
       }
       //如果登陆成功 把User对象 设置到Session作用域
       HttpSession httpSession =  httpServletRequest.getSession(true); //表示没有session是自动创建一个session
       httpSession.setAttribute(AppConfig.USER_SESSION,user);
       //返回结果
       return Result.SUCCESS();
   }
   @GetMapping("/logout")
    public Result logout( HttpServletRequest httpServletRequest){
        HttpSession httpSession =  httpServletRequest.getSession(false);
       if(httpSession!=null) httpSession.invalidate();   //注销 session
       return Result.SUCCESS();
   }

    /**
     *
     * @param id
     * @param httpServletRequest
     * @return
     */
   @GetMapping("/info")
    public Result<User> getUserInfo(@RequestParam(value = "id",required = false) Long id,HttpServletRequest httpServletRequest){
       if(id==null){
            HttpSession session = httpServletRequest.getSession(false);
            return Result.SUCCESS((User) session.getAttribute(AppConfig.USER_SESSION));
       }
       User user = iUserService.selectById(id);
       if(user==null){
           return Result.FAIL(ResultCode.FAILED_USER_NOT_EXISTS);
       }
       //返回结果
       return Result.SUCCESS(user);
   }

    /**
     * 修改个人中心数据
     * @param username
     * @param nickname
     * @param gender
     * @param email
     * @param phoneNum
     * @param remark
     * @return
     */
   @PostMapping("/modifyInfo")
    public Result modifyInfo(HttpServletRequest request,
                             @RequestParam(value = "username" ,required = false) String username,
                             @RequestParam(value = "nickname",required = false)String nickname,
                             @RequestParam(value = "gender",required = false)Byte gender,
                             @RequestParam(value = "email",required = false)String email,
                             @RequestParam(value = "phoneNum",required = false)String phoneNum,
                             @RequestParam(value = "remark",required = false)String remark){

       HttpSession session = request.getSession(false);

       if(!StringUtils.hasLength(username) && !StringUtils.hasLength(nickname)  && !StringUtils.hasLength(email)
       && !StringUtils.hasLength(phoneNum) && !StringUtils.hasLength(remark) && gender==null ){

           return Result.FAIL("请输入需要修改的内容");
       }
       User sessionUser = (User)session.getAttribute(AppConfig.USER_SESSION);

       //封装对象

       User user = new User();
       user.setId(sessionUser.getId());
       user.setGender(gender);
       user.setNickname(nickname);
       user.setEmail(email);
       user.setRemark(remark);
       user.setUsername(username);
       user.setPhoneNum(phoneNum);

       iUserService.modifyInfo(user);
       //把最新的数据放到session中区

       User user1 = iUserService.selectById(sessionUser.getId());

       //更新值
       session.setAttribute(AppConfig.USER_SESSION,user1);
       return Result.SUCCESS();
   }

   @PostMapping("/modifyPwd")
   public Result modifyPwd(HttpServletRequest request,@NotNull String oldPassword,@NotNull String newPassword,@NotNull String passwordRepeat){

       if(!newPassword.equals(passwordRepeat)){
           throw new ForumException(Result.FAIL(ResultCode.FAILED));
       }


       HttpSession session = request.getSession(false);
       User attribute = (User)session.getAttribute(AppConfig.USER_SESSION);

       iUserService.modifyPwd(attribute.getId(),oldPassword,newPassword);

       return Result.SUCCESS();
   }
}
