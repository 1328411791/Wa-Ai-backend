package org.talang.wabackend.model.vo.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class SelectModelVo {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     *
     */
    @TableField(value = "title")
    private String title;
    /**
     *
     */
    @TableField(value = "type")
    private String type;
    /**
     *
     */
    @TableField(value = "body")
    private String body;
    /**
     *
     */
    @TableField(value = "publish_time")
    private Date publishTime;
    /**
     *
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     *
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     *
     */
    @TableField(value = "author_id")
    private Integer author_id;
    /**
     *
     */
    @TableField(value = "status")
    private String status;
    /**
     *
     */
    @TableField(value = "filename")
    private String filename;
}
