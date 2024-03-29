package org.talang.wabackend.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.talang.wabackend.aop.annotation.RateLimiter;
import org.talang.wabackend.common.Result;
import org.talang.wabackend.constant.UserRedisConstant;
import org.talang.wabackend.exception.BusinessErrorCode;
import org.talang.wabackend.exception.BusinessException;
import org.talang.wabackend.mapper.UserMapper;
import org.talang.wabackend.model.dto.user.ForgetPasswordDto;
import org.talang.wabackend.model.dto.user.PutUserInformationDto;
import org.talang.wabackend.model.dto.user.RegisterDto;
import org.talang.wabackend.model.generator.User;
import org.talang.wabackend.model.vo.user.UserVo;
import org.talang.wabackend.sd.ImageComponent;
import org.talang.wabackend.service.UserService;
import org.talang.wabackend.util.MailComponent;
import org.talang.wabackend.util.RedisStrategyComponent;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private ImageComponent imageComponent;

    @Resource
    private RedisStrategyComponent redisStrategyComponent;

    // 初始头像设置
    @Value("${user-config.default-avatar-id}")
    private String defaultAvatarId;

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
        user.setAvatar(defaultAvatarId);

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

    @Deprecated
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

    @Override
    public Result updateAvatar(MultipartFile img) {

        int loginId = StpUtil.getLoginIdAsInt();
        User user = this.getById(loginId);

        String oldAvatarStaticImageId = user.getAvatar();
        String newStaticImageId;
        try {
            newStaticImageId = imageComponent.saveImage(img.getBytes(), loginId);
        } catch (Exception e) {
            throw new RuntimeException("保存图片失败");
        }

        imageComponent.removeImage(oldAvatarStaticImageId);
        user.setAvatar(newStaticImageId);
        this.updateById(user);

        return Result.success();
    }

    @RateLimiter(key = "getUserById")
    @Override
    public User getById(Serializable id) {
        try {
            return redisStrategyComponent.queryWithPassThrough(UserRedisConstant.USER_PREFIX
                    , id, super::getById, 60L, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new BusinessException(BusinessErrorCode.UnknownErr, "获取用户信息失败");
        }
    }

    @Override
    public boolean updateById(User entity) {
        // 删除缓存
        String key = UserRedisConstant.USER_PREFIX + entity.getId();
        stringRedisTemplate.delete(key);
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(User entity) {
        // 删除缓存
        String key = UserRedisConstant.USER_PREFIX + entity.getId();
        stringRedisTemplate.delete(key);

        return super.removeById(entity);
    }
}




