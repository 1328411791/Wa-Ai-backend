package org.talang.wabackend.common;

import cn.dev33.satoken.stp.StpInterface;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.talang.wabackend.model.generator.User;
import org.talang.wabackend.service.RoleService;
import org.talang.wabackend.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private RoleService roleService;

    @Resource
    private UserService userService;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // TODO: 待添加缓存
        Integer userId = Integer.parseInt((String) loginId);
        User user = userService.getById(userId);
        List<String> roleList = new ArrayList<>();
        roleList.add(roleService.getById(user.getRole()).getRoleName());
        return roleList;
    }
}
