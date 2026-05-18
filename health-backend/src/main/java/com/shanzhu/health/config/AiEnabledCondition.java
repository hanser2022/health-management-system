package com.shanzhu.health.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class AiEnabledCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String enabled = context.getEnvironment().getProperty("health.ai.enabled", "true");
        if (!"true".equalsIgnoreCase(enabled)) {
            return false;
        }
        String apiKey = context.getEnvironment().getProperty("spring.ai.openai.api-key", "");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
