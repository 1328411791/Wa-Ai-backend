package org.talang.wabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.dto.comment.CommentAddDto;
import org.talang.wabackend.service.CommentService;

@RestController
@RequestMapping("/comment")
@Tag(name = "评论服务", description = "评论相关接口")
public class CommentController {

    @Resource
    private CommentService commentService;
    @Operation(description = "添加评论")
    @PostMapping("/")
    Result addComment(@RequestBody CommentAddDto commentAddDto) {
        return commentService.addComment(commentAddDto);
    }

    @Operation(description = "删除评论")
    @DeleteMapping("/{commentId}")
    Result deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }

    @Operation(description = "分页获取评论")
    @GetMapping("/")
    Result getCommentsByPage(String type, Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.getCommentsByPage(type, articleId, pageNum, pageSize);
    }
}
