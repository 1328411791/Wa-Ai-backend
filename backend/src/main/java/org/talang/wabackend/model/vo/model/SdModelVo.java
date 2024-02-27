package org.talang.wabackend.model.vo.model;

import lombok.Data;

import java.util.Date;

@Data
public class SdModelVo {
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String title;
    /**
     *
     */
    private String type;
    /**
     *
     */
    private String body;
    /**
     *
     */
    private Date publishTime;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    private String nickName;
    /**
     *
     */
    private String status;
    /**
     *
     */
    private String filename;
}
