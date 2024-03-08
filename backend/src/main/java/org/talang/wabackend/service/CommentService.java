package org.talang.wabackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.model.dto.comment.CommentAddDto;
import org.talang.wabackend.model.generator.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author Polister
 * @since 2024-03-07 19:08:44
 */
public interface CommentService extends IService<Comment> {

    Result addComment(CommentAddDto commentAddDto);

    Result deleteComment(Long commentId);

    Result getCommentsByPage(String type, Long id, Integer pageNum, Integer pageSize);
}
