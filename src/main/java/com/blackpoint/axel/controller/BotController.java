package com.blackpoint.axel.controller;

import com.blackpoint.axel.service.PreferenceLoaderService;
import com.blackpoint.axel.model.TelegramMessages.AnswerMessage;
import com.blackpoint.axel.model.TelegramMessages.Response;
import com.blackpoint.axel.model.TelegramMessages.Update;
import com.blackpoint.axel.service.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
public class BotController {
    private final String telegramApiToken;
    private final String weatherApiToken;
    private Logger logger;

    public BotController() {
        logger = new Logger();
        String telegramApiToken;
        String weatherApiToken;
        try {
            PreferenceLoaderService preferenceLoaderService = new PreferenceLoaderService("token.properties");
            telegramApiToken = preferenceLoaderService.telegramApiToken();
            logger.info("Telegram API token loaded: " + telegramApiToken);
            weatherApiToken = preferenceLoaderService.getWeatherApiToken();
            logger.info("OpenWeather API token loaded: " + weatherApiToken);
        }
        catch (IOException e) {
            logger.error("Failed to read token. " + e);
            telegramApiToken = null;
            weatherApiToken = null;
        }
        this.telegramApiToken = telegramApiToken;
        this.weatherApiToken = weatherApiToken;
    }

    @PostMapping("/")
    public void handleRequest(@RequestBody Update update)
    {
        logger.displayUpdateMessageContent(update);

        String[] message = update.getMessage().getContent().split(" ");
        switch(message[0]) {
            case "/weather":
                if (message.length != 2) {
                    logger.error("[ Axel ] | No city specified.");
                    return;
                }
                logger.info("[ Axel ] | /weather request detected, for city: " + message[1]);
                WeatherController weatherController = new WeatherController(weatherApiToken);
                logger.info(weatherController.getWeather(message[1]));
                return;
            default:
                logger.info("Default case over here");
                break;
        }

        logger.info("[ Axel ] | Responding...");
        AnswerMessage answerMessage = new AnswerMessage(
                update.getMessage().getChat().getId(),
                new StringBuffer(update.getMessage().getContent()).reverse().toString()
        );
        RestTemplate restTemplate = new RestTemplate();
        try {
            Response response = restTemplate.postForObject(telegramApiToken + "sendMessage", answerMessage, Response.class);
            if(response == null) {
                throw new NullPointerException();
            }
            logger.info("[ Axel ] | Responded with: " + response.getResult().getContent());
        }
        catch(HttpClientErrorException| NullPointerException e) {
            logger.error("[ Axel ] | Request error: " + e.getMessage());
        }
    }
}
