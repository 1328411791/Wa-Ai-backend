package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName sd_model_likes
 */
@TableName(value ="sd_model_likes")
@Data
public class ModelLikes implements Serializable {
    /**
     * 
     */
    @TableField(value = "id")
    private Integer id;

    /**
     * 
     */
    @TableField(value = "sdmodel_id")
    private String sdmodel_id;

    /**
     * 
     */
    @TableField(value = "user_id")
    private Integer user_id;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}