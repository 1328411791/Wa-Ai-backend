package org.talang.wabackend.model.vo.posting;

import lombok.Data;

@Data
public class PostingVoLiteTemp {
    private int id;
    private int userId;
    private String title;
    private int favorite;
    private int commentNumber;
    private String images;
}
