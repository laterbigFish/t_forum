package com.example.forum.service.impl;

import com.example.forum.common.model.Message;
import com.example.forum.service.IMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class MessageServiceImplTest {
    @Resource
    ObjectMapper objectMapper;
    @Resource
    IMessageService messageService;

    @Test
    void sendById(){
        Long userId = 17L;
        Long id = 15L;
        messageService.sendById(userId,id,"第一次的私信");

    }

    @Test
    void selectByReceiveUserIdInt(){
        System.out.println(messageService.selectByReceiveUserId(17L));
    }


    @Test
    void getAllMessage() throws JsonProcessingException{
        System.out.println(objectMapper.writeValueAsString(messageService.getAllMessage(17L)));
    }

    @Test
    void updateByMessageId(){
        messageService.updateByMessageId(1L);
    }

    @Test
    void reply(){
        Message message = new Message();
        message.setPostUserId(17L);
        message.setReceiveUserId(15L);
        message.setContent("我是快乐的小鱼");

        messageService.reply(3L,message);
    }
}