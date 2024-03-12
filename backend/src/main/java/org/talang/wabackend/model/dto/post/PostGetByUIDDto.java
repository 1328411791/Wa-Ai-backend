package org.talang.wabackend.model.dto.post;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class PostGetByUIDDto {
    private Integer userId;
    private Date startTimestamp;
    private Date endTimestamp;
    private String searchQuery;
    @NotNull
    private Integer page;
    @NotNull
    private Integer pageSize;
}
