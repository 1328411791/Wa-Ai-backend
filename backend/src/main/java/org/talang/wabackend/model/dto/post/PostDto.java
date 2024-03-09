package org.talang.wabackend.model.dto.post;

import lombok.Data;

import java.util.List;

@Data
public class PostDto {
    private String title;
    private List<String> sdImages;
    private String body;
}
