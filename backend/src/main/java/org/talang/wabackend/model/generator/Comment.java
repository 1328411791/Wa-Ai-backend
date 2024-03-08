package org.talang.wabackend.model.generator;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * 评论表(Comment)表实体类
 *
 * @author Polister
 * @since 2024-03-07 19:04:46
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sd_comment")
public class Comment  {
@TableId
    private Long id;

//评论类型（0代表帖子评论，1代表模型评论）
    private String type;
//所属帖子或模型id
    private Long articleId;
//根评论id
    private Long rootId;
//评论内容
    private String content;
//所回复的目标评论的userid
    private Long toCommentUserId;
//回复目标评论id
    private Long toCommentId;
    //回复用户id（谁创造的这条评论）
    private Long commentUserId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
//删除标志（0代表未删除，1代表已删除）
    private Integer isDelete;


}
