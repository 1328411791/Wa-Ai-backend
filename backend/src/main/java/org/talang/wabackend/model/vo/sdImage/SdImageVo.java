package org.talang.wabackend.model.vo.sdImage;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.talang.wabackend.model.vo.user.UserVo;

import java.util.Date;

@Data
public class SdImageVo {
    private String id;

    private Integer checkpointModelId;

    private Integer vaeModelId;

    private String params;

    private String imageUrl;

    private UserVo user;

    private Boolean isLiked;

    private Long liked;

    private Date updateTime;

    private Date createTime;
}
