package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName sd_image
 */
@TableName(value ="sd_image")
@Data
public class Image implements Serializable {
    /**
     * 
     */
    @TableField(value = "id")
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
    @TableField(value = "publish")
    private String publish;

    /**
     * 
     */
    @TableField(value = "created")
    private String created;

    /**
     * 
     */
    @TableField(value = "updated")
    private String updated;

    /**
     * 
     */
    @TableField(value = "status")
    private String status;

    /**
     * 
     */
    @TableField(value = "author_id")
    private Integer author_id;

    /**
     * 
     */
    @TableField(value = "createdfrom")
    private String createdfrom;

    /**
     * 
     */
    @TableField(value = "introduce")
    private String introduce;

    /**
     * 
     */
    @TableField(value = "image_id")
    private Integer image_id;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}