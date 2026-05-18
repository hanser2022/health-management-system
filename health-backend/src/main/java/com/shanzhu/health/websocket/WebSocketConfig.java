package com.shanzhu.health.websocket;


import com.shanzhu.health.config.AiEnabledCondition;
import com.shanzhu.health.service.IBodyNotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSocket
@Conditional(AiEnabledCondition.class)
public class WebSocketConfig implements WebSocketConfigurer {

    private final wsOpenAiChatModel openAiChatModel;
    private final IBodyNotesService bodyNotesService;


    @Autowired
    public WebSocketConfig(wsOpenAiChatModel openAiChatModel, IBodyNotesService bodyNotesService) {
        this.openAiChatModel = openAiChatModel;
        this.bodyNotesService = bodyNotesService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandler(openAiChatModel, bodyNotesService), "/ws/chat")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }
}
