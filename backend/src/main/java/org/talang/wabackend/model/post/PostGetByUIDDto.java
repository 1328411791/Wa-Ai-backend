package org.talang.wabackend.model.post;

import lombok.Data;

import java.util.Date;

@Data
public class PostGetByUIDDto {
    private Integer userId;
    private Date startTimestamp;
    private Date endTimestamp;
    private String searchQuery;
    private Integer page;
    private Integer pageSize;
}
