package org.talang.wabackend.model.generator;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * user
 *
 * @TableName sd_user
 */
@TableName(value = "sd_user")
@Data
public class User implements Serializable {
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
    @TableField(value = "gender")
    private String gender;
    /**
     *
     */
    @TableLogic
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
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     *
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}