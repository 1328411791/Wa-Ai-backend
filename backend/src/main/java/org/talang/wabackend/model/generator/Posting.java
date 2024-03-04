package org.talang.wabackend.model.generator;

import lombok.Data;

@Data
public class Posting {
    private int id;
    private int userId;
    private String title;
    private String images;
    private int favorite;
    private int commentId;
    private int commentNumber;
}
