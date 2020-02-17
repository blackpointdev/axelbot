package com.blackpoint.axel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnswerMessage {
    @JsonProperty("chat_id")
    private long chatId;
    @JsonProperty("text")
    private String content;

    public AnswerMessage() {}

    public AnswerMessage(long chatId, String content) {
        this.chatId = chatId;
        this.content = content;
    }
}
