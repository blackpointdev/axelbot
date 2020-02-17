package com.blackpoint.axel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Chat {
    private long id;
    @JsonProperty("first_name")
    private String firstName;
    private String type;

    public Chat() {}
}
