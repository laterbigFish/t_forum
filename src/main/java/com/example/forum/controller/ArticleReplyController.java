package com.example.forum.controller;

import com.example.forum.common.Result;
import com.example.forum.common.config.AppConfig;
import com.example.forum.common.enmu.ResultCode;
import com.example.forum.common.model.Article;
import com.example.forum.common.model.ArticleReply;
import com.example.forum.common.model.User;
import com.example.forum.service.IArticleReplyService;
import com.example.forum.service.IArticleService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reply")
public class ArticleReplyController {

    @Resource
    private IArticleReplyService articleReplyService;

    @Resource
    private IArticleService articleService;
    @PostMapping("/create")
    public Result create( HttpServletRequest request, @NotNull@RequestParam("articleId") Long articleId,
                                  @NotNull @RequestParam("content") String content){

        //判断用户是否被禁言
        HttpSession session = request.getSession(false);

        User attribute = (User)session.getAttribute(AppConfig.USER_SESSION);
        if(attribute.getState()==1) return Result.FAIL(ResultCode.FAILED_USER_BANNED);


        Article article = articleService.selectByPrimaryKey(articleId);

        if(article==null || article.getDeleteState()!=null && article.getDeleteState()==1 || article.getState()==1){
            log.info("article为空或者已删除");
            return Result.FAIL(ResultCode.FAILED);
        }


        ArticleReply articleReply = new ArticleReply();
        articleReply.setContent(content);
        articleReply.setPostUserId(attribute.getId());
        articleReply.setArticleId(articleId);

        articleReplyService.create(articleReply);
        return Result.SUCCESS();
    }

    @GetMapping("/getReplies")
    public Result<List<ArticleReply>> getRepliesByArticleId(@RequestParam("articleId") @NotNull Long articleId){

        //校验帖子是否存在
        Article article = articleService.selectByPrimaryKey(articleId);
        if(article==null || article.getState()==1) return Result.FAIL(ResultCode.FAILED);

        //查询回复的帖子

        List<ArticleReply> articleReplies = articleReplyService.selectByArticleId(articleId);

        if(articleReplies==null) articleReplies = new ArrayList<>();
        return Result.SUCCESS(articleReplies);
    }
}
