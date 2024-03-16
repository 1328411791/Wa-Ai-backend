package org.talang.wabackend.model.generator;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * (ModelFavoriteVo)表实体类
 *
 * @author ECAMT
 * @since 2024-03-13 15:38:37
 */

@Data
@TableName(value = "sd_model_favorite")
public class ModelFavorite {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "model_id")
    private Integer modelId;

    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    @TableField(value = "is_delete")
    private Integer isDelete;

}

