package org.talang.wabackend.model.vo.model;

import lombok.Data;

@Data
public class ModelFavoriteVo {
    private Integer id;
    private String title;
    private Integer authorId;
    private String authorNickName;
    private String fileName;
    private Long numRuns;
    private Long numFavours;
    private boolean isLike;
    private Long numComment;
    private Integer numLiked;
}
