package org.talang.wabackend.model.dto.user;

import lombok.Data;

@Data
public class PutUserInformationDto {

    private String nickName;

    private String email;

    private String avatar;

    private String description;

    private String gender;
}
