package org.talang.wabackend.model.post;

import lombok.Data;

import java.util.List;

@Data
public class PostAddDto {
    private String title;
    private List<String> sdImages;
    private String body;
}
