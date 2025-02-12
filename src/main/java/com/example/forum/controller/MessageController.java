package com.example.forum.controller;

import com.example.forum.common.Result;
import com.example.forum.common.config.AppConfig;
import com.example.forum.common.enmu.ResultCode;
import com.example.forum.common.exception.ForumException;
import com.example.forum.common.model.Message;
import com.example.forum.common.model.User;
import com.example.forum.service.IMessageService;
import com.example.forum.service.IUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Resource
    IUserService userService;
    @Resource
    IMessageService messageService;
    @PostMapping("/send")

    public Result send( HttpServletRequest request, @RequestParam("receiveUserId") @NotNull Long receiveUserId,
                        @RequestParam("content")@NotNull String content ){

        HttpSession session = request.getSession(false);

        User sessionUser = (User) session.getAttribute(AppConfig.USER_SESSION);
        //不能给自己发站内信

        if(sessionUser.getId().equals(receiveUserId)){
            throw new ForumException(Result.FAIL(ResultCode.FAILED));
        }

        //查询接受者是否存在
        User user = userService.selectById(receiveUserId);

        if(user==null){
            throw new ForumException(Result.FAIL(ResultCode.FAILED));
        }
        if(user.getState()==1 || user.getDeleteState()==1){
             return Result.FAIL("接收者章台异常");
        }
        messageService.sendById(sessionUser.getId(),receiveUserId,content);

        return Result.SUCCESS();
    }

    /**
     * 查询用户站内信未读数量
     * @return
     */

    @GetMapping("getUnreadCount")
    public  Result<Integer> getUnreadCount(HttpServletRequest request){

        HttpSession session = request.getSession(false);

        User sessionUser = (User)session.getAttribute(AppConfig.USER_SESSION);

        return Result.SUCCESS(messageService.selectByReceiveUserId(sessionUser.getId()));
    }

    /**
     * 获取站内信列表
      * @param request
     * @return
     */
    @GetMapping("/getAll")
    public Result<List<Message>> getAll(HttpServletRequest request){

        HttpSession session = request.getSession(false);

        User sessionUser = (User)session.getAttribute(AppConfig.USER_SESSION);

        Long receiveUserId = sessionUser.getId();

        List<Message> allMessage = messageService.getAllMessage(receiveUserId);


        return Result.SUCCESS(allMessage);
    }

    /**
     *
      * @param id 站内信id
     * @return
     */
    @PostMapping("markRead")

    public Result markRead( HttpServletRequest request, @RequestParam("id") @NotNull Long id){
        System.out.println("进入成功");
        HttpSession session = request.getSession(false);
        User sessionUser = (User)session.getAttribute(AppConfig.USER_SESSION);

        Message message = messageService.selectById(id);

        if(message==null) return Result.FAIL(ResultCode.FAILED_NOT_EXISTS);

        //站内信不是自己的
        if(!sessionUser.getId().equals(message.getReceiveUserId())) throw new ForumException(Result.FAIL(ResultCode.FAILED));

        messageService.updateByMessageId(id);

        return Result.SUCCESS();
    }

  @PostMapping("/reply")

    public Result replyMessage(HttpServletRequest request, @RequestParam("repliedId") @NotNull Long repliedId,@RequestParam("content") @NotNull String content){

      HttpSession session = request.getSession(false);

      User sessionUser = (User)session.getAttribute(AppConfig.USER_SESSION);

      if(sessionUser.getState()==1) return Result.FAIL(ResultCode.FAILED_USER_BANNED);

      Message message1 = messageService.selectById(repliedId);

      if(message1==null || message1.getDeleteState()==1){
          return Result.FAIL(ResultCode.FAILED);
      }

      if(sessionUser.getId().equals(message1.getPostUserId())){
          return Result.FAIL("无法给自己的站内信回复");
      }
      Message message = new Message();
      message.setReceiveUserId(message1.getPostUserId());
      message.setContent(content);
      message.setPostUserId(sessionUser.getId());
      messageService.reply(repliedId,message);
      return Result.SUCCESS();
  }
}
