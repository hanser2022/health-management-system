package com.shanzhu.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.health.config.JwtConfig;
import com.shanzhu.health.entity.Body;
import com.shanzhu.health.entity.Menu;
import com.shanzhu.health.entity.User;
import com.shanzhu.health.entity.UserRole;
import com.shanzhu.health.mapper.UserMapper;
import com.shanzhu.health.mapper.UserRoleMapper;
import com.shanzhu.health.service.IBodyService;
import com.shanzhu.health.service.IMenuService;
import com.shanzhu.health.service.IUserRoleService;
import com.shanzhu.health.service.IUserService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shanzhu
 * @since 2026-02-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private IBodyService bodyMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private User parseCurrentUser() {
        try {
            String token = JwtConfig.getCurrentToken();
            if (token != null) {
                return jwtConfig.parseToken(token, User.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Map<String, Object> login(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);

        if (loginUser != null) {
            String storedPassword = loginUser.getPassword();
            boolean passwordMatch = false;
            
            if (storedPassword.startsWith("$2a$")) {
                passwordMatch = passwordEncoder.matches(user.getPassword(), storedPassword);
            } else {
                passwordMatch = user.getPassword().equals(storedPassword);
            }
            
            if (passwordMatch) {
                loginUser.setPassword(null);
                String token = jwtConfig.createToken(loginUser);
                Map<String, Object> data = new HashMap<>();
                data.put("token", token);
                data.put("username", loginUser.getUsername());
                data.put("id", loginUser.getId());
                return data;
            }
        }
        return null;
    }


    @Override
    public Map<String, Object> Wxlogin(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);

        if (loginUser != null) {
            String storedPassword = loginUser.getPassword();
            boolean passwordMatch = false;
            
            if (storedPassword.startsWith("$2a$")) {
                passwordMatch = passwordEncoder.matches(user.getPassword(), storedPassword);
            } else {
                passwordMatch = user.getPassword().equals(storedPassword);
            }
            
            if (passwordMatch) {
                loginUser.setPassword(null);
                String token = jwtConfig.createToken(loginUser);
                Map<String, Object> data = new HashMap<>();
                data.put("token", token);
                return data;
            }
        }
        return null;
    }


    @Override
    public Map<String, Object> getUserInfo(String token) {
        User loginUser = null;
        try {
            loginUser = jwtConfig.parseToken(token, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (loginUser != null) {
            // 如果获取到了用户信息，则组装返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("name", loginUser.getUsername());
            data.put("avatar", loginUser.getAvatar());
            data.put("id", loginUser.getId());
            // 获取用户角色列表
            List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles", roleList);

            // 获取用户菜单列表
            List<Menu> menuList = menuService.getMenuListByUserId(loginUser.getId());
            data.put("menuList", menuList);
            return data;
        }
        return null;
    }


    @Override
    public void logout(String token) {

    }

    @Override
    public boolean addUser(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        int count = this.baseMapper.selectCount(wrapper).intValue();
        if (count > 0) {
            return false;
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            this.baseMapper.insert(user);
            List<Integer> roleIdList = user.getRoleIdList();
            if (roleIdList != null) {
                for (Integer roleId : roleIdList) {
                    userRoleMapper.insert(new UserRole(null, user.getId(), roleId));
                }
            }
        }
        return true;
    }

    @Override
    public User getUserById(Integer id) {

        // 根据用户ID查询用户信息
        User user = this.baseMapper.selectById(id);
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, id);
        List<UserRole> userRoleList = userRoleMapper.selectList(wrapper); // 从用户角色表中查询出所有用户角色，并赋值给userRoleList变量

        // 将用户角色ID列表设置到用户对象中
        List<Integer> roleIdList = userRoleList.stream() // 将 userRoleList 转化为一个 Stream<UserRole> 对象，使得可以对其中的每一个元素进行操作
                .map(userRole -> {
                    return userRole.getRoleId();
                })
                .collect(Collectors.toList()); // 将每个roleId值收集到一个List<Integer>对象中，并赋值给roleIdList变量
        user.setRoleIdList(roleIdList); // 将roleIdList设置到user对象中的roleIdList属性中

        return user;
    }


    @Override
    @Transactional
    public void updateUser(User user) {
        // 更新用户表
        this.baseMapper.updateById(user);
        // 清除原有的角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, user.getId());
        userRoleMapper.delete(wrapper);
        // 设置新的角色
        List<Integer> roleIdList = user.getRoleIdList(); // 获取用户新的角色 ID 列表
        if (roleIdList != null) {
            for (Integer roleId : roleIdList) {
                // 设置新角色
                userRoleMapper.insert(new UserRole(null, user.getId(), roleId));
            }
        }
    }


    @Override
    public void deletUserById(Integer id) {
        // 通过用户ID删除用户
        this.baseMapper.deleteById(id);
        // 清除原有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, id); // 查询条件：用户ID等于id
        userRoleMapper.delete(wrapper); // 执行删除操作
    }


    @Override
    public Map<String, Object> register(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        wrapper.eq(User::getUsername, user.getUsername());
        Long count = this.baseMapper.selectCount(wrapper);
        if (count > 0) {
            map.put("fail", false);
            return map;
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAvatar("https://bpic.51yuansu.com/pic2/cover/00/35/43/58119f542530c_610.jpg");
            this.baseMapper.insert(user);
            Integer userId = user.getId();
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(3);
            boolean result = userRoleService.save(userRole);
            if (result) {
                map.put("success", true);
            } else {
                map.put("fail", false);
            }
            return map;
        }
    }


    @Override
    public Map<String, Object> getUserId() {
        User loginUser = parseCurrentUser();
        Map<String, Object> data = new HashMap<>();
        if (loginUser != null) {
            data.put("id", loginUser.getId());
            return data;
        }
        return null;
    }


    @Override
    public Map<String, Object> WxgetUserId(String token) {
        User WxloginUser = null;
        try {
            // 通过 JWT 解析 token 得到用户信息
            WxloginUser = jwtConfig.parseToken(token, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (WxloginUser != null) {
            // 如果获取到了用户信息，则组装返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("id", WxloginUser.getId());
            return data;
        }
        return null;
    }


    @Override
    public Map<String, Object> getBodyInfo() {
        User loginUser = parseCurrentUser();
        if (loginUser == null) {
            return null;
        }
        Map<String, Object> data = new HashMap<>();
        data.put("id", loginUser.getId());
        Integer pid = (Integer) data.get("id");

        List<Body> bodyList = bodyMapper.getBodyListByUserId(pid);

        Map<String, Object> result = new HashMap<>();
        result.put("bodyList", bodyList);
        return result;
    }

    @Override
    public boolean updateuser(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User existingUser = this.baseMapper.selectOne(wrapper);
        
        if (existingUser != null) {
            String storedPassword = existingUser.getPassword();
            boolean passwordMatch = false;
            
            if (storedPassword.startsWith("$2a$")) {
                passwordMatch = passwordEncoder.matches(user.getPassword(), storedPassword);
            } else {
                passwordMatch = user.getPassword().equals(storedPassword);
            }
            
            if (passwordMatch) {
                existingUser.setPassword(passwordEncoder.encode(user.getNewPassword()));
                this.baseMapper.updateById(existingUser);
                return true;
            }
        }
        return false;
    }


}
