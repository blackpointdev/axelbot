package com.blackpoint.axel.controller;

import com.blackpoint.axel.service.CredentialsService;
import com.blackpoint.axel.service.MessageService;
import com.blackpoint.axel.model.TelegramMessages.Response;
import com.blackpoint.axel.model.TelegramMessages.Update;
import com.blackpoint.axel.service.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class BotController {
    private Logger logger;
    private MessageService messageService;
    CredentialsService credentialsService;

    public BotController() {
        logger = new Logger();

        try {
            credentialsService = new CredentialsService("token.properties");

            logger.info("Telegram API token loaded: " + credentialsService.getTelegramApiToken());
            logger.info("OpenWeather API token loaded: " + credentialsService.getWeatherApiToken());

            messageService = new MessageService(credentialsService.getTelegramApiToken());
            logger.info("Started new MessageService with given Telegram Api token.");
        }
        catch (IOException e) {
            logger.error("Failed to load preferences.");
        }
    }

    @PostMapping("/")
    public void handleRequest(@RequestBody Update update)
    {
        logger.displayUpdateMessageContent(update);
        try {
            String[] message = update.getMessage().getContent().split(" ", 2);
            switch(message[0]) {
                case "/weather":
                    if (message.length < 2) {
                        logger.error("[ Axel ] | No city specified.");
                        messageService.sendMessage(
                                update.getMessage().getChat().getId(),
                                "Please, specify which city do you want a forecast for, ex. \"/weather Kraków\""
                        );
                        return;
                    }
                    logger.info("[ Axel ] | /weather request detected, for city: " + message[1]);
                    WeatherController weatherController = new WeatherController(credentialsService.getWeatherApiToken());
                    logger.info(weatherController.getWeather(message[1]));
                    messageService.sendMessage(
                            update.getMessage().getChat().getId(),
                            weatherController.getWeather(message[1])
                    );
                    return;

                case "/help":
                    messageService.sendMessage(
                            update.getMessage().getChat().getId(),
                            "/weather [city] - Get current weather data for given city\n" +
                                    "/help - Get all bot's commands"
                    );
                    break;
                default:
                    logger.info("Default case over here");
                    Response response = messageService.sendMessage(
                            update.getMessage().getChat().getId(),
                            "Hi " + update.getMessage().getFrom().getFirstName() + "! Type /help to get list of my commands.");
                    logger.info("[ Axel ] | Responded with: " + response.getResult().getContent());
                    break;
            }
        }
        catch (NullPointerException e) {
            logger.error("[ Axel ] | Request error: " + e.getMessage());
        }
    }
}
