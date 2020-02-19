package com.blackpoint.axel.service;

import com.blackpoint.axel.model.TelegramMessages.Update;
import org.slf4j.LoggerFactory;

public class Logger {
    private org.slf4j.Logger logger;

    public Logger() {
        this.logger = LoggerFactory.getLogger(Logger.class);
    }

    public void displayUpdateMessageContent(Update update) {
        String template = "[ %s %s - %s] | %s";

            logger.info(
                    String.format(template,
                            update.getMessage().getFrom().getFirstName(),
                            update.getMessage().getFrom().getLastName(),
                            update.getMessage().getChat().getId(),
                            update.getMessage().getContent()));
        }

    public void info(String msg) { logger.info(msg); }

    public void error(String msg) { logger.error(msg); }

    public void warn(String msg) { logger.warn(msg); }
}
