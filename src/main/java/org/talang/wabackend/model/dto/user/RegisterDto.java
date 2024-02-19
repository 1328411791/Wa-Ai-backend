package org.talang.wabackend.model.dto.user;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class RegisterDto {
    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "nick_name")
    private String nickName;

    @TableField(value = "email")
    private String email;

    @TableField(value = "password")
    private String password;

    @TableField(value = "password")
    private String rePassword;
}
