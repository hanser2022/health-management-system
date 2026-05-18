package com.shanzhu.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.health.entity.User;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author shanzhu
 * @since 2026-02-20
 */
public interface IUserService extends IService<User> {

    Map<String, Object> login(User user);

    Map<String, Object> getUserInfo(String token);

    void logout(String token);

    boolean addUser(User user);

    User getUserById(Integer id);

    void updateUser(User user);

    void deletUserById(Integer id);

    Map<String, Object> register(User register);


    Map<String, Object> getUserId();

    Map<String, Object> getBodyInfo();

    boolean updateuser(User user);

    Map<String, Object> Wxlogin(User user);

    Map<String, Object> WxgetUserId(String token);
}
