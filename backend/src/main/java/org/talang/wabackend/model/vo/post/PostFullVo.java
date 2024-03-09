package org.talang.wabackend.model.vo.post;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostFullVo {

    private int id;

    private int userId;

    private String userNickName;

    private String title;

    private String body;

    private int numFavours;

    private int numLiked;

    private int numComment;

    private Date createTime;

    private Date updateTime;

    private List<String> sdimageIdList;
}
