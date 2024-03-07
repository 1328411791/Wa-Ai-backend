package org.talang.wabackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.talang.wabackend.mapper.UserMapper;
import org.talang.wabackend.model.dto.user.ForgetPasswordDto;
import org.talang.wabackend.model.dto.user.PutUserInformationDto;
import org.talang.wabackend.model.dto.user.RegisterDto;
import org.talang.wabackend.model.generator.User;
import org.talang.wabackend.model.vo.user.UserVo;
import org.talang.wabackend.service.UserService;
import org.talang.wabackend.util.MailComponent;

/**
 * @author lihan
 * @description 针对表【sd_user(user)】的数据库操作Service实现
 * @createDate 2024-02-20 04:15:20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Integer loginByUserName(String userName, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, userName);
        wrapper.eq(User::getPassword, password);
        User user = this.getOne(wrapper);
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    @Override
    public Integer loginByEmail(String email, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        wrapper.eq(User::getPassword, password);
        User user = this.getOne(wrapper);
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    @Override
    public User getByUserName(String userName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, userName);
        return this.getOne(wrapper);
    }

    @Override
    public boolean register(RegisterDto registerDto) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, registerDto.getUserName());
        if (this.getOne(wrapper) != null) {
            throw new RuntimeException("用户名已存在");
        }
        wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, registerDto.getEmail());
        if (this.getOne(wrapper) != null) {
            throw new RuntimeException("邮箱已存在");
        }

        String key = MailComponent.REGISTER_MAIL_PREFIX + registerDto.getEmail();
        String code = stringRedisTemplate.opsForValue().getAndDelete(key);
        if (code == null || !code.equals(registerDto.getEmailCode())) {
            throw new RuntimeException("验证码错误, 请重新获取验证码");
        }

        User user = new User();
        user.setUserName(registerDto.getUserName());
        user.setPassword(registerDto.getPassword());
        user.setEmail(registerDto.getEmail());
        user.setNickName(registerDto.getNickName());
        return this.save(user);
    }

    @Override
    public User forgetPassword(ForgetPasswordDto forgetPasswordDto) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, forgetPasswordDto.getEmail());
        User user = this.getOne(wrapper);

        if (user == null) {
            throw new RuntimeException("邮箱不存在");
        }

        String key = MailComponent.FORGET_MAIL_PREFIX + forgetPasswordDto.getEmail();
        String code = stringRedisTemplate.opsForValue().getAndDelete(key);
        if (code == null || !code.equals(forgetPasswordDto.getEmailCode())) {
            throw new RuntimeException("验证码错误, 请重新获取验证码");
        }

        user.setPassword(forgetPasswordDto.getPassword());
        this.updateById(user);

        return user;
    }

    @Override
    public UserVo getUserVo(Integer userId) {
        User user = this.getById(userId);
        return BeanUtil.toBean(user, UserVo.class);
    }

    @Override
    public String getUsernameById(Integer userId) {
        User user = this.getById(userId);
        return user.getUserName();
    }

    @Override
    public boolean putUserInformation(int userId, PutUserInformationDto putUserInformationDto) {
        User user = BeanUtil.toBean(putUserInformationDto, User.class);
        user.setId(userId);
        return this.updateById(user);
    }

    @Override
    public String getUserNickNameById(Integer userId) {
        User user = this.getById(userId);
        return user.getNickName();
    }
}




