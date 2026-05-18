package com.shanzhu.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanzhu.health.entity.User;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author shanzhu
 * @since 2026-02-20
 */
public interface UserMapper extends BaseMapper<User> {
    //这个方法是用来根据用户ID查询其拥有的角色名称列表的
    public List<String> getRoleNameByUserId(Integer userId);

}
