package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "sd_static_image")
public class StaticImage {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @TableField(value = "image_name")
    private String imageName;

    @TableField(value = "file_path")
    private String filePath;

    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "hash")
    private String hash;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
