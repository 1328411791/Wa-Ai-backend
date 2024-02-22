package org.talang.wabackend.model.vo.model;

import lombok.Data;

import java.util.List;

@Data
public class HomePageModelVo {

    private Integer id;

    private String title;

    private String type;

    private String body;

    private List<String> imagesUrl;

    private Integer likeCount;

}
