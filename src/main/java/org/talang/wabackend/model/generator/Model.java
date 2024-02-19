package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName sd_model
 */
@TableName(value ="sd_model")
@Data
public class Model implements Serializable {
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}