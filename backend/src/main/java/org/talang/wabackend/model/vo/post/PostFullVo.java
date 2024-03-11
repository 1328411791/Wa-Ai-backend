package org.talang.wabackend.model.vo.post;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostFullVo {

    private Integer id;

    private Integer userId;

    private String userNickName;

    private String title;

    private String body;

    private Integer numFavours;

    private Integer numLiked;

    private Integer numComment;

    private Date createTime;

    private Date updateTime;

    private List<String> sdimageIdList;
}
