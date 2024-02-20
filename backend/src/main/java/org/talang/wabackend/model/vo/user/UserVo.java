package org.talang.wabackend.model.vo.user;

import lombok.Data;

@Data
public class UserVo {
    private Integer id;

    private String userName;

    private String nickName;

    private String email;

    private String avatar;

    private String description;
}
