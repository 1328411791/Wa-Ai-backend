package org.talang.wabackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.talang.wabackend.model.dto.user.ForgetPasswordDto;
import org.talang.wabackend.model.dto.user.PutUserInformationDto;
import org.talang.wabackend.model.dto.user.RegisterDto;
import org.talang.wabackend.model.generator.User;
import org.talang.wabackend.model.vo.user.UserVo;

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

    UserVo getUserVo(Integer userId);

    String getUsernameById(Integer userId);

    boolean putUserInformation(int userId, PutUserInformationDto putUserInformationDto);

    String getUserNickNameById(Integer userId);

}
