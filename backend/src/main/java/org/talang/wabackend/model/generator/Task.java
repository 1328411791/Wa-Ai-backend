package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName sd_task
 */
@TableName(value = "sd_task")
@Data
public class Task implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(value = "id")
    private String id;
    /**
     *
     */
    @TableField(value = "user_id")
    private Integer userId;
    /**
     *
     */
    @TableField(value = "txt2image_options")
    private String txt2imageOptions;
    /**
     *
     */
    @TableField(value = "extraimage_options")
    private String extraimageOptions;
    /**
     * 类型
     */
    @TableField(value = "type")
    private String type;
    /**
     * 优先度
     */
    @TableField(value = "priority")
    private Integer priority;
    /**
     * 0 待进行 1 正在运行 2已完成 3 错误
     */
    @TableField(value = "status")
    private Integer status;
    /**
     *
     */
    @TableField(value = "author_id")
    private Integer authorId;
    /**
     *
     */
    @TableField(value = "image_id")
    private String imageId;
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
}