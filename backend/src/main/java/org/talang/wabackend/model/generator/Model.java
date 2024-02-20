package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName sd_model
 */
@TableName(value = "sd_model")
@Data
public class Model implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     *
     */
    @TableField(value = "title")
    private String title;
    /**
     *
     */
    @TableField(value = "type")
    private String type;
    /**
     *
     */
    @TableField(value = "body")
    private String body;
    /**
     *
     */
    @TableField(value = "publish_time")
    private Date publishTime;
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
    @TableField(value = "author_id")
    private Integer author_id;
    /**
     *
     */
    @TableField(value = "status")
    private String status;
    /**
     *
     */
    @TableField(value = "filename")
    private String filename;
}