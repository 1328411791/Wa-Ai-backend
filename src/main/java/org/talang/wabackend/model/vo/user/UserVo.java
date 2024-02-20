package org.talang.wabackend.model.vo.user;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class UserVo{
    private Integer id;

    private String userName;

    private String nickName;

    private String email;

    private String avatar;

    private String description;
}
