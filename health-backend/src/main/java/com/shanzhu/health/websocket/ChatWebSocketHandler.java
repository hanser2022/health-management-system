package com.shanzhu.health.websocket;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shanzhu.health.config.AiEnabledCondition;
import com.shanzhu.health.entity.BodyNotes;
import com.shanzhu.health.service.IBodyNotesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;


@Component
@Conditional(AiEnabledCondition.class)
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(ChatWebSocketHandler.class);
    
    private final wsOpenAiChatModel openAiChatModel;
    private final IBodyNotesService bodyNotesService;

    public ChatWebSocketHandler(wsOpenAiChatModel openAiChatModel, IBodyNotesService bodyNotesService) {
        this.openAiChatModel = openAiChatModel;
        this.bodyNotesService = bodyNotesService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

        String payload = message.getPayload();
        log.debug("收到消息: {}", payload);
        ChatRequest request = parseMessage(payload);
        String question = request.getText();

        Integer userId = request.getUserId();
        log.debug("解析后的userId: {}, question: {}", userId, question);
        
        if (question.equals("AI健康建议") && userId != null) {
            List<BodyNotes> bodyNotes = bodyNotesService.getBodyNotes(userId);
            log.debug("查询到的健康记录数量: {}", bodyNotes.size());
            question = constructPrompt(bodyNotes);
        }

        StringBuilder responseBuilder = new StringBuilder();

        Flux<String> aiResponse = openAiChatModel.stream(question)
                .doOnNext(chunk -> {
                    if (!"[DONE]".equalsIgnoreCase(chunk)) {
                        responseBuilder.append(chunk);
                    }
                })
                .map(chunk -> "data:" + chunk + "\n\n")
                .concatWith(Mono.just("data:[DONE]\n\n"));

        aiResponse.subscribe(response -> {
            try {
                session.sendMessage(new TextMessage(response));
            } catch (IOException e) {
                log.error("发送WebSocket消息失败", e);
            }
        }, error -> {
            log.error("AI响应处理错误", error);
        }, () -> {

        });
    }


    private ChatRequest parseMessage(String payload) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(payload, ChatRequest.class);
        } catch (Exception e) {
            log.error("解析消息失败: {}", payload, e);
            throw new RuntimeException("Invalid message format: " + payload, e);
        }
    }

    private String constructPrompt(List<BodyNotes> bodyNotes) {
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("根据以下用户的健康数据，给出健康建议，并以txt文档输出：\n");

        for (BodyNotes note : bodyNotes) {
            promptBuilder.append("用户信息：\n");
            promptBuilder.append("姓名：").append(note.getName()).append("\n");
            if (note.getAge() != null) {
                promptBuilder.append("年龄：").append(note.getAge()).append("\n");
            }
            if (note.getGender() != null) {
                promptBuilder.append("性别：").append(note.getGender()).append("\n");
            }
            if (note.getHeight() != null && note.getWeight() != null) {
                promptBuilder.append("身高：").append(note.getHeight()).append("cm\n");
                promptBuilder.append("体重：").append(note.getWeight()).append("kg\n");
                double h = note.getHeight() / 100.0;
                double bmi = note.getWeight() / (h * h);
                promptBuilder.append(String.format("BMI：%.1f\n", bmi));
            }
            if (note.getBloodSugar() != null) {
                promptBuilder.append("血糖：").append(note.getBloodSugar()).append("mmol/L\n");
            }
            if (note.getBloodPressure() != null && !note.getBloodPressure().isEmpty()) {
                promptBuilder.append("血压：").append(note.getBloodPressure()).append("mmHg\n");
            }
            if (note.getBloodLipid() != null) {
                promptBuilder.append("血脂：").append(note.getBloodLipid()).append("mmol/L\n");
            }
            if (note.getHeartRate() != null) {
                promptBuilder.append("心率：").append(note.getHeartRate()).append("次/分钟\n");
            }
            if (note.getVision() != null) {
                promptBuilder.append("视力：").append(note.getVision()).append("\n");
            }
            if (note.getSleepDuration() != null) {
                promptBuilder.append("睡眠时长：").append(note.getSleepDuration()).append("小时\n");
            }
            if (note.getSleepQuality() != null) {
                promptBuilder.append("睡眠质量：").append(note.getSleepQuality()).append("\n");
            }
            promptBuilder.append("是否抽烟：").append(note.isSmoking() ? "是" : "否").append("\n");
            promptBuilder.append("是否喝酒：").append(note.isDrinking() ? "是" : "否").append("\n");
            promptBuilder.append("是否运动：").append(note.isExercise() ? "是" : "否").append("\n");
            if (note.getFoodTypes() != null) {
                promptBuilder.append("喜好食物：").append(note.getFoodTypes()).append("\n");
            }
            if (note.getWaterConsumption() != null) {
                promptBuilder.append("饮水量：").append(note.getWaterConsumption()).append("ml/天\n");
            }
            if (note.getDate() != null) {
                promptBuilder.append("记录时间：").append(note.getDate()).append("\n\n");
            } else {
                promptBuilder.append("\n");
            }
        }

        promptBuilder.append("请根据以上数据，分析用户的健康状况，并给出具体的健康建议。");

        return promptBuilder.toString();
    }
}
