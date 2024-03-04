package org.talang.wabackend.model.dto.posting;

import lombok.Data;

@Data
public class CreatePostingDto {
    private String title;
    private String[] imagesArray;
}
