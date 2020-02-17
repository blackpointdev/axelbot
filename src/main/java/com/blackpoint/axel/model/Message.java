package com.blackpoint.axel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Message {
    private long message_id;
    private User from;
    private Chat chat;
    private String date;
    @JsonProperty("text")
    private String content;

    public Message() {}
}
