package org.talang.wabackend.model.vo.posting;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostingVoFull {
    private int id;
    private int userId;
    private String title;
    private int favorite;
    private int commentId;
    private int commentNumber;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String images;
    private String body;
}
