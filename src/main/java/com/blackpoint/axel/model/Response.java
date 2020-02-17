package com.blackpoint.axel.model;

import lombok.Data;

@Data
public class Response {
    private boolean ok;
    private Message result;

    public Response() {}

    public Response(boolean ok, Message result) {
        this.ok = ok;
        this.result = result;
    }
}
