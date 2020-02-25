package com.blackpoint.axel.service;

import com.blackpoint.axel.model.TelegramMessages.AnswerMessage;
import com.blackpoint.axel.model.TelegramMessages.Response;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class MessageService {
    private String telegramApiToken;

    public MessageService(String telegramApiToken) {
        this.telegramApiToken = telegramApiToken;
    }

    public Response sendMessage(long receiverId, String content) throws NullPointerException {
        AnswerMessage answerMessage = new AnswerMessage(receiverId, content);
        RestTemplate restTemplate = new RestTemplate();

        Response response = restTemplate.postForObject(
                telegramApiToken + "sendMessage?parse_mode=html",
                answerMessage,
                Response.class
        );

        if (response == null) {
            throw new NullPointerException();
        }

        return response;
    }
}
