package org.talang.wabackend.model.vo.post;

import lombok.Data;

import java.util.List;

@Data
public class PostLiteVo {

    private Integer id;

    private Integer userId;

    private String userNickName;

    private String title;

    private Integer numFavours;

    private Integer numLiked;

    private Integer numComment;

    private List<String> sdimageIdList;

    private boolean isFavours;

    private boolean isLiked;

}