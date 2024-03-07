package org.talang.wabackend.model.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class RegisterDto {

    private String userName;

    private String nickName;

    private String email;

    private String emailCode;

    private String password;

    private String rePassword;

}
