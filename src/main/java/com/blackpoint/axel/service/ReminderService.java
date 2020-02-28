package com.blackpoint.axel.service;

import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class ReminderService {
    static class ReminderTask extends TimerTask {

        private final MessageService messageService;
        private final long userId;
        private final String title;

        public ReminderTask(MessageService messageService, String title, long userId) {
            this.messageService = messageService;
            this.userId = userId;
            this.title = title;
        }

        @Override
        public void run() {
            messageService.sendMessage(userId, "You have reminder! " + title);
        }
    }

    public Date setReminder(
            String time,
            String title,
            MessageService messageService,
            long userId
    ) throws NumberFormatException {

        String[] timeStringSplit = time.split(":");

        Calendar calendar = Calendar.getInstance();

        if (timeStringSplit.length == 3) {
            calendar.set(Calendar.SECOND, Integer.parseInt(timeStringSplit[2]));
        }
        else if (timeStringSplit.length == 2) {
            calendar.set(Calendar.SECOND, 0);
        }
        else {
            throw new NumberFormatException();
        }

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeStringSplit[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeStringSplit[1]));

        Date date = calendar.getTime();

        Timer timer = new Timer();
        timer.schedule(new ReminderTask(messageService, title, userId), date);

        return date;
    }


    // KIND OF A TIMER FUNCTIONALITY. LEFT IT HERE FOR FUTURE UPDATES.
//    public void setReminder(int seconds, MessageService messageService, long userId) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.SECOND, seconds);
//
//        Date date  = calendar.getTime();
//
//        Timer timer = new Timer();
//        timer.schedule(new ReminderTask(messageService, userId), date);
//    }


}
