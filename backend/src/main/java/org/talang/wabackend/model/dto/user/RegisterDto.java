package org.talang.wabackend.model.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

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
