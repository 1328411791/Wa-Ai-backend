package org.talang.wabackend.model.dto.user;

import lombok.Data;

@Data
public class LoginDto {

    private String userName;

    private String email;

    private String password;
}
