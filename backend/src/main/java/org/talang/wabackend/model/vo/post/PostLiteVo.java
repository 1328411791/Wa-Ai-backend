package org.talang.wabackend.model.vo.post;

import lombok.Data;

import java.util.List;

@Data
public class PostLiteVo {

    private int id;

    private int userId;

    private String userNickName;

    private String title;

    private int numFavours;

    private int numLiked;

    private int numComment;

    private List<String> sdimageIdList;
}