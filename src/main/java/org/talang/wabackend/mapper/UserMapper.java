package org.talang.wabackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.talang.wabackend.model.generator.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author lihan
* @description 针对表【sd_user(user)】的数据库操作Mapper
* @createDate 2024-02-20 04:15:20
* @Entity org.talang.wabackend.model.generator.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




