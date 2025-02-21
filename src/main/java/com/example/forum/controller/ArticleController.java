package com.example.forum.controller;

import com.example.forum.common.Result;
import com.example.forum.common.config.AppConfig;
import com.example.forum.common.enmu.ResultCode;
import com.example.forum.common.exception.ForumException;
import com.example.forum.common.model.Article;
import com.example.forum.common.model.Board;
import com.example.forum.common.model.User;
import com.example.forum.service.IArticleService;
import com.example.forum.service.IBoardService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private IBoardService iBoardService;
    @Resource
    private IArticleService articleService;

    /**
     *  发布新帖
     * @param boardId 板块id
     * @param title 标题
     * @param content 内容
     */
    @PostMapping("/create")
    public Result create( HttpServletRequest request, @RequestParam("boardId") @NotNull Long boardId,
                          @RequestParam("title") @NotNull String title,
                          @RequestParam("content") @NotNull String content){
        //判断用户是否被禁言
        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute(AppConfig.USER_SESSION);

        if(user.getState()==1){
            //用户被禁言
            return Result.FAIL(ResultCode.FAILED_USER_BANNED);
        }
        //板块校验
        Board board = iBoardService.selectByPrimaryKey(boardId);

        if(board==null || board.getState()==1 || board.getDeleteState()==1){
           log.info(ResultCode.FAILED_BOARD_BANNED.toString());
            return Result.FAIL(ResultCode.FAILED_BOARD_BANNED);
        }
        //封装文章
        Article article = new Article();
                article.setTitle(title); //标题
                article.setContent(content); //正文
                article.setUserId(user.getId()); //作者id
                article.setBoardId(boardId);// 板块id
                articleService.create(article);
        //响应成功
        return Result.SUCCESS(ResultCode.SUCCESS);
    }
    @GetMapping("/getAllByBoardId")
   public Result<List<Article>> getAllByBoard(@RequestParam(value = "boardId",required = false) Long boardId ){
        List<Article> articles = articleService.selectAllByUser();

        if(articles==null) articles = new ArrayList<>();

        if(boardId!=null)   articles = articleService.selectAllByBoardId(boardId);

        return Result.SUCCESS(articles);
   }

    /**
     * 根据帖子id获取详情
     * @param request
     * @param id
     * @return
     */
   @GetMapping("/details")

    public Result<Article> details( HttpServletRequest request, @RequestParam("id") @NotNull Long id, HttpSession httpSession ){
       HttpSession session = request.getSession(false);
       //此时的状态是已经登陆了  已经统一处理过了

       User user = (User)session.getAttribute(AppConfig.USER_SESSION);

       Article article = articleService.selectDetailById(id);

       if(article==null){
           article.setOwn(false);
           throw new ForumException(Result.FAIL(ResultCode.FAILED));
       }

       if(user.getId().equals(article.getUserId())){
           article.setOwn(true);
       }
       return Result.SUCCESS(article);
   }

   @PostMapping("/modify")

    public Result modify(HttpServletRequest request,  //过去当前登录的用户
                                  @RequestParam("id")@NotNull Long id,
                                  @RequestParam("title")@NotNull String title,
                                  @RequestParam("content")@NotNull String content){

       //判断是否被禁言 和 是不是 作者本人
       HttpSession session = request.getSession(false);

       User user = (User)session.getAttribute(AppConfig.USER_SESSION);

       if(user.getState()==1 || user.getDeleteState()==1) return Result.FAIL(ResultCode.FAILED_USER_BANNED);

       Article article = articleService.selectByPrimaryKey(id);

       if(article==null) throw new ForumException(Result.FAIL(ResultCode.FAILED_NOT_EXISTS));
       //判断发送者是否是帖子
       if(article.getUserId()!=user.getId()) throw new ForumException(Result.FAIL(ResultCode.FAILED_FORBIDDEN));

       //判断帖子的状态

       if(article.getState()==1) throw new ForumException(Result.FAIL(ResultCode.FAILED_FORBIDDEN));
       //进行更新操作
       articleService.modify(id,title,content);

       return Result.SUCCESS();
   }

    /**
     * 此处的点赞操作还可以进行拓展   一个用户只能点击一次
     * @param request
     * @param id
     * @return
     */
   @PostMapping("/thumbsUp")
    public Result thumbsUp( HttpServletRequest request,@RequestParam("id")@NotNull Long id){
       //检测用户的状态
       HttpSession session = request.getSession(false);
       User user = (User) session.getAttribute(AppConfig.USER_SESSION);

       if(user.getState()==1) return Result.FAIL(ResultCode.FAILED_USER_BANNED);

       //判断一下帖子的状态
       Article article = articleService.selectByPrimaryKey(id);

       if(article==null) return Result.FAIL(ResultCode.FAILED_NOT_EXISTS);

       //也可以判断是否是该作者  如果是该作者做不能进行点赞
       if(article.getUserId().equals(user.getId())) return Result.FAIL(ResultCode.FAILED);

       articleService.thumbsUpById(id);
       //返回结果
       return Result.SUCCESS();
   }
   @PostMapping("/delete")
    public Result deleteById(HttpServletRequest request,@RequestParam("id") @NotNull Long id){

       HttpSession session = request.getSession(false);

       User user = (User)session.getAttribute(AppConfig.USER_SESSION);

       Article article = articleService.selectByPrimaryKey(id);

       if(article==null || article.getDeleteState()!=null && article.getDeleteState()==1) return Result.FAIL(ResultCode.FAILED_NOT_EXISTS);

       if(!article.getUserId().equals(user.getId())) return Result.FAIL(ResultCode.FAILED);

       articleService.deleteById(id);

       return Result.SUCCESS();
   }

   @GetMapping("getAllByUserId")

    public Result<List<Article>> getAllByUserId(HttpServletRequest httpServletRequest,@RequestParam(value = "userId",required = false) Long userId){

       if(userId==null){
           HttpSession session = httpServletRequest.getSession(false);
           User user = (User)session.getAttribute(AppConfig.USER_SESSION);
           userId = user.getId();
       }
       List<Article> articles = articleService.selectByUserId(userId);
       return Result.SUCCESS(articles);
   }
}
