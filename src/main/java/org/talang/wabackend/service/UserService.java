package org.talang.wabackend.service;

import org.springframework.data.relational.core.sql.In;
import org.talang.wabackend.model.dto.user.ForgetPasswordDto;
import org.talang.wabackend.model.dto.user.RegisterDto;
import org.talang.wabackend.model.generator.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author lihan
* @description 针对表【sd_user(user)】的数据库操作Service
* @createDate 2024-02-20 04:15:20
*/
public interface UserService extends IService<User> {

    Integer loginByUserName(String userName, String password);

    Integer loginByEmail(String email, String password);

    User getByUserName(String userName);

    boolean register(RegisterDto registerDto);

    User forgetPassword(ForgetPasswordDto forgetPasswordDto);
}
