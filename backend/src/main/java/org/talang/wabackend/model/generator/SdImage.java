package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 
 * @TableName sd_image
 */
@TableName(value ="sd_image")
@Data
public class SdImage implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private String id;

    /**
     * 
     */
    @TableField(value = "checkpoint_model_id")
    private Integer checkpointModelId;

    /**
     * 
     */
    @TableField(value = "vae_model_id")
    private Integer vaeModelId;

    /**
     * 
     */
    @TableField(value = "params")
    private String params;

    /**
     * 
     */
    @TableField(value = "static_image_id")
    private String staticImageId;
    /*
    *
     */
    @TableField(value = "liked")
    private Long liked;
    /**
     * 
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}