package org.talang.wabackend.model.dto.user;

import lombok.Data;

@Data
public class ForgetPasswordDto {

    private String email;

    private String emailCode;
}
