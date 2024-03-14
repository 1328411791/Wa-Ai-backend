package org.talang.wabackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.talang.wabackend.model.generator.Role;

/**
 * @author lihanyu
 * @description 针对表【sd_role】的数据库操作Mapper
 * @createDate 2024-03-14 13:11:53
 * @Entity org.talang.wabackend.model.generator.Role
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}




