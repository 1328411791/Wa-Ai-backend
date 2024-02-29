package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
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
    @TableField(value = "sdmodel_id")
    private Integer sdmodel_id;

    /**
     * 
     */
    @TableField(value = "params")
    private String params;

    /**
     * 
     */
    @TableField(value = "static_image_id")
    private String static_image_id;

    /**
     * 
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Integer update_time;

    /**
     * 
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Integer create_time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}