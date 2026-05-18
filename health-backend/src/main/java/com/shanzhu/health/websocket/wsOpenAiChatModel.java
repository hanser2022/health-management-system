package com.shanzhu.health.websocket;

import com.shanzhu.health.config.AiEnabledCondition;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Conditional(AiEnabledCondition.class)
public class wsOpenAiChatModel {

    @Autowired
    private OpenAiChatModel openAiChatModel;

    public Flux<String> stream(String msg) {
        return openAiChatModel.stream(msg);
    }
}
