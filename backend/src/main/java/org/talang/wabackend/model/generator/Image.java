package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName sd_image
 */
@TableName(value = "sd_image")
@Data
public class Image implements Serializable {
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
    @TableField(value = "params")
    private String params;
    /**
     *
     */
    @TableField(value = "publish_time")
    private Date publishTime;
    /**
     *
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     *
     */
    @TableField(value = "update_time")
    private Date updateTime;
    /**
     *
     */
    @TableField(value = "status")
    private String status;
    /**
     *
     */
    @TableField(value = "author_id")
    private Integer authorId;
    /**
     *
     */
    @TableField(value = "createdfrom")
    private String createdrom;
    /**
     *
     */
    @TableField(value = "introduce")
    private String introduce;
    /**
     *
     */
    @TableField(value = "image_id")
    private Integer imageId;
}