package com.shanzhu.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.health.entity.Role;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author shanzhu
 * @since 2026-02-20
 */
public interface IRoleService extends IService<Role> {

    boolean addRole(Role role);

    Role getRoleById(Integer id);

    void updateRole(Role role);

    void deleteRoleById(Integer id);
}
