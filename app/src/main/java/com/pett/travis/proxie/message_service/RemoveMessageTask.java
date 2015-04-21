package com.pett.travis.proxie.message_service;

import java.util.TimerTask;
import android.os.Handler;

/**
 * Created by Travis on 4/8/2015.
 */
public class RemoveMessageTask extends TimerTask {
    private Message message;
    private Handler handler;
    private MessageService messageService;

    public RemoveMessageTask(Message message, MessageService messageService) {
        this.message = message;
        this.handler = new Handler();
        this.messageService = messageService;
    }

    @Override
    public void run() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                messageService.removeMessage(message);
            }
        });
    }
}


