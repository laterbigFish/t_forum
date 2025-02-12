package com.example.forum.service.impl;

import com.example.forum.common.model.Board;
import com.example.forum.service.IBoardService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BoartServiceImplTest {
    @Resource
    IBoardService iBoardService;
    @Test
    void selectByNum(){
        List<Board> boards = iBoardService.selectByNum(5);
        for(Board cur:boards){
            System.out.println(cur);
        }
    }

    @Test
    @Transactional  //表示操作完成之后回滚数据库操作 以免污染到我们数据库中的数据

    void addOneArticleCountById(){
        iBoardService.addOneArticleCountById(1L);
    }
}