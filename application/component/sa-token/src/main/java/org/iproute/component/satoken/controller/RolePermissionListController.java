package org.iproute.component.satoken.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RolePermissionListController
 *
 * @author devops@kubectl.net
 * @since 2023/6/8
 */
@RestController
@RequestMapping("/rp")
public class RolePermissionListController {

    // 获取所有的权限列表  ---- http://localhost:8081/rp/roleList
    @RequestMapping("/roleList")
    public List<String> roleList() {
        return StpUtil.getRoleList();
    }

    // 获取所有的权限列表  ---- http://localhost:8081/rp/permissionList
    @RequestMapping("/permissionList")
    public List<String> permissionList() {
        return StpUtil.getPermissionList();
    }

}
