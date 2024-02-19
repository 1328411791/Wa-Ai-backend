package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user
 * @TableName sd_user
 */
@TableName(value ="sd_user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 
     */
    @TableField(value = "email")
    private String email;

    /**
     * 
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     *
     */
    @TableField(value = "description")
    private String description;
    /**
     * 
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

    /*
    password
     */
    @TableField(value = "password")
    private String password;

    /**
     * 
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}