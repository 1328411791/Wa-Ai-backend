package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName sd_tag
 */
@TableName(value ="sd_tag")
@Data
public class Tag implements Serializable {
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}