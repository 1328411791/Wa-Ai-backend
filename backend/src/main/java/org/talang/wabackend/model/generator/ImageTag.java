package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @TableName sd_image_tag
 */
@TableName(value = "sd_image_tag")
@Data
public class ImageTag implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableField(value = "id")
    private Integer id;
    /**
     *
     */
    @TableField(value = "name_en")
    private String name_en;
    /**
     *
     */
    @TableField(value = "name_cn")
    private String name_cn;
    /**
     *
     */
    @TableField(value = "number_refe")
    private Integer number_refe;
}