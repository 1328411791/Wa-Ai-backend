package org.talang.wabackend.model.dto.user;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LoginDto {

    private String userName;

    private String email;

    private String password;

    @Builder.Default
    private boolean rememberMe = true;
}
