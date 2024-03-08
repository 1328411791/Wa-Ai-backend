package org.talang.wabackend.model.vo.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 主回复Vo类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommentVo {
    private Long id;

    //评论内容
    private String content;
    //回复用户id（谁创造的这条评论）
    private Long commentUserId;
    //点赞数
    private Integer numLiked;
    //回复者名称
    private String commentUserName;
    // 子回复列表
    private List<ToCommentVo> reply;
    // 子回复数量
    private Integer numReply;
}
