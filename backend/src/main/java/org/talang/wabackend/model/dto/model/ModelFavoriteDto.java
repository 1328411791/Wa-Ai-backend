package org.talang.wabackend.model.dto.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class ModelFavoriteDto {
    @NotNull
    private String type;
    private Date startTimestamp;
    private Date endTimestamp;
    private String searchQuery;
    @NotNull
    private Integer page;
    @NotNull
    private Integer pageSize;
}
