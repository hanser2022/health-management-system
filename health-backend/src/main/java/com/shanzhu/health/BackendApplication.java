package com.shanzhu.health;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration;


@Slf4j
@SpringBootApplication(exclude = {OpenAiAutoConfiguration.class})
@MapperScan("com.shanzhu.*.mapper")
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        log.info("=====================项目后端启动成功============================");
    }
}
