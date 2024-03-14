package org.talang.wabackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.talang.wabackend.mapper.RoleMapper;
import org.talang.wabackend.model.generator.Role;
import org.talang.wabackend.service.RoleService;

/**
 * @author lihanyu
 * @description 针对表【sd_role】的数据库操作Service实现
 * @createDate 2024-03-14 13:11:53
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

}




