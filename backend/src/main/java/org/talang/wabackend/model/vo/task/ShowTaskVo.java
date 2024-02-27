package org.talang.wabackend.model.vo.task;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class ShowTaskVo {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private String id;
    /**
     *
     */
    private Integer userId;

    private String nickName;
    /**
     * 类型
     */
    private String type;
    /**
     * 优先度
     */
    private Integer priority;
    /**
     * 0 待进行 1 正在运行 2已完成 3 错误
     */
    private Integer status;
    /**
     *
     */
    private String imageURL;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;
}
