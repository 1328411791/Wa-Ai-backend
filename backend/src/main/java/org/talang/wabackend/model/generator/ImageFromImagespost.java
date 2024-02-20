package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName sd_image_from_imagespost
 */
@TableName(value = "sd_image_from_imagespost")
@Data
public class ImageFromImagespost implements Serializable {
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
    @TableField(value = "sdimage_id")
    private String sdimageId;
    /**
     *
     */
    @TableField(value = "imagespost_")
    private String imagespost_;
}