package org.talang.wabackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.talang.wabackend.model.generator.User;
import org.talang.wabackend.service.UserService;
import org.talang.wabackend.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author lihan
* @description 针对表【sd_user(user)】的数据库操作Service实现
* @createDate 2024-02-20 04:15:20
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




