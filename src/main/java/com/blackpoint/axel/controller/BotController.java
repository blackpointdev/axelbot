package com.blackpoint.axel.controller;

import com.blackpoint.axel.service.CredentialsService;
import com.blackpoint.axel.service.MessageService;
import com.blackpoint.axel.model.TelegramMessages.Response;
import com.blackpoint.axel.model.TelegramMessages.Update;
import com.blackpoint.axel.service.Logger;
import com.blackpoint.axel.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

@RestController
public class BotController {
    private Logger logger;
    private MessageService messageService;
    CredentialsService credentialsService;
    private HashMap<String, String> tokens;

    public BotController() {
        logger = new Logger();

        try {
            credentialsService = new CredentialsService();
            tokens = credentialsService.
                    getTokenFromEnvVar("TELEGRAM_API_TOKEN", "WEATHER_API_TOKEN");

            if (tokens == null) {
                tokens = credentialsService.
                        getTokenFromFile("token.properties", "TELEGRAM_API_TOKEN", "WEATHER_API_TOKEN");
            }

            logger.info("Telegram API token loaded: " + tokens.get("TELEGRAM_API_TOKEN"));
            logger.info("OpenWeather API token loaded: " + tokens.get("WEATHER_API_TOKEN"));

            messageService = new MessageService(tokens.get("TELEGRAM_API_TOKEN"));
            logger.info("Started new MessageService with given Telegram Api token.");
        }
        catch (IOException e) {
            logger.error("Failed to load preferences: " + e.getMessage());
        }
    }

    @PostMapping("/")
    public void handleRequest(@RequestBody Update update)
    {
        logger.displayUpdateMessageContent(update);
        try {
            String[] message = update.getMessage().getContent().split(" ", 3);
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
                    WeatherController weatherController = new WeatherController(tokens.get("WEATHER_API_TOKEN"));
                    logger.info(weatherController.getWeather(message[1]));
                    messageService.sendMessage(
                            update.getMessage().getChat().getId(),
                            weatherController.getWeather(message[1])
                    );
                    return;

                case "/time":
                    Calendar now = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

                    messageService.sendMessage(
                            update.getMessage().getChat().getId(),
                            "Current system time: " + simpleDateFormat.format(now.getTime())
                            );
                    break;

                case "/reminder":
                    if (message.length < 3) {
                        logger.error("[ Axel ] | Wrong reminder parameters");
                        messageService.sendMessage(
                                update.getMessage().getChat().getId(),
                                "Please, specify time and title of reminder, ex. '/reminder 12:34:00 Shopping'"
                        );
                        return;
                    }
                    logger.info("[ Axel ] | /reminder request detected, setting reminder for: " + message[1]);
                    ReminderService reminderService = new ReminderService();
                    try {
                        reminderService.setReminder(message[1], message[2], messageService, update.getMessage().getChat().getId());
                        messageService.sendMessage(
                                update.getMessage().getChat().getId(),
                                "Your reminder is set to " + message[1]);
                    }
                    catch (ParseException e) {
                        logger.error("[ Axel ] | Argument parser error: " + e.getMessage());
                    }
                    break;

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
