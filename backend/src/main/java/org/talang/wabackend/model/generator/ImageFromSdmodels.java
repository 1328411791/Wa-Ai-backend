package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName sd_image_from_sdmodels
 */
@TableName(value = "sd_image_from_sdmodels")
@Data
public class ImageFromSdmodels implements Serializable {
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
    @TableField(value = "sdmodel_id")
    private Integer sdmodelId;
}