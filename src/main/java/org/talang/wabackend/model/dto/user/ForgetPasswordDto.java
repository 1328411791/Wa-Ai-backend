package org.talang.wabackend.model.dto.user;

import lombok.Data;

@Data
public class ForgetPasswordDto {
    private String userName;

    private String email;
}
