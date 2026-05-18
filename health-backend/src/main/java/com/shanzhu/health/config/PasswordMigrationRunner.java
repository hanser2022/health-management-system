package com.shanzhu.health.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shanzhu.health.entity.User;
import com.shanzhu.health.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PasswordMigrationRunner implements CommandLineRunner {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        List<User> users = userMapper.selectList(null);
        for (User user : users) {
            String password = user.getPassword();
            if (password != null && !password.startsWith("$2a$")) {
                String encoded = passwordEncoder.encode(password);
                user.setPassword(encoded);
                userMapper.updateById(user);
                log.info("Migrated password for user: {}", user.getUsername());
            }
        }
        log.info("Password migration check completed.");
    }
}
