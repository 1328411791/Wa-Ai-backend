package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "sd_post_favorite")
public class PostFavorite {
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
    @TableField(value = "post_id")
    private Integer postId;
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
