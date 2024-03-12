package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "sd_post")
public class Post {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     *
     */
    @TableField(value = "user_id")
    private Integer userId;
    /**
     *
     */
    @TableField(value = "title")
    private String title;
    /**
     *
     */
    @TableField(value = "body")
    private String body;
    /**
     *
     */
    @TableField(value = "numFavours", fill = FieldFill.INSERT)
    private int numFavours;
    /**
     *
     */
    @TableField(value = "numLiked", fill = FieldFill.INSERT)
    private int numLiked;
    /**
     *
     */
    @TableField(value = "numComment", fill = FieldFill.INSERT)
    private int numComment;
    /**
     *
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     *
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     *
     */
    @TableLogic
    @TableField(value = "is_delete")
    private Integer isDelete;
}
