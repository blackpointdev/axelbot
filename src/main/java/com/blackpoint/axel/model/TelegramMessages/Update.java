package com.blackpoint.axel.model.TelegramMessages;

import lombok.Data;

@Data
public class Update {
    private long update_id;
    private Message message;

    public Update() {}

    public Update(long update_id, Message message) {
        this.update_id = update_id;
        this.message = message;
    }
}
