package com.blackpoint.axel;

import com.blackpoint.axel.model.AnswerMessage;
import com.blackpoint.axel.model.Response;
import com.blackpoint.axel.model.Update;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
public class BotController {
    private final String apiURI;

    public BotController() {
        String apiURI;
        try {
            PreferenceLoader preferenceLoader = new PreferenceLoader("token.properties");
            apiURI = preferenceLoader.getToken();
            System.out.println("Token loaded: " + apiURI);
        }
        catch (IOException e) {
            System.out.println("Failed to read token. " + e);
            apiURI = null;
        }
        this.apiURI = apiURI;
    }

    @PostMapping("/")
    public void displayMessages(@RequestBody Update update)
    {
        String template = "[ %s %s - %s] | %s";
        System.out.println(
                String.format(template,
                update.getMessage().getFrom().getFirstName(),
                update.getMessage().getFrom().getLastName(),
                update.getMessage().getChat().getId(),
                update.getMessage().getContent()));

        System.out.println("[ Axel ] | Responding...");
        AnswerMessage answerMessage = new AnswerMessage(update.getMessage().getChat().getId(), update.getMessage().getContent());
        RestTemplate restTemplate = new RestTemplate();
        try {
            Response response = restTemplate.postForObject(apiURI + "sendMessage", answerMessage, Response.class);
            System.out.println("[ Axel ] | Responded with " + response);
        }
        catch(HttpClientErrorException e) {
            System.out.println("[ Axel ] | Request error: " + e.getMessage());
        }
    }
}
