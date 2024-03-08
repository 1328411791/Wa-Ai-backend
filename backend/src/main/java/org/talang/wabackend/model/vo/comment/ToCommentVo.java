package org.talang.wabackend.model.vo.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 子回复Vo类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ToCommentVo {
    private Long id;

    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //回复目标评论id
    private Long toCommentId;
    //回复用户id（谁创造的这条评论）
    private Long commentUserId;
    //点赞数
    private Integer numLiked;
    //回复者名称
    private String commentUserName;
    //被回复者名称
    private String toCommentUserName;
    private Date createTime;
}
