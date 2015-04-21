package com.pett.travis.proxie.message_service;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by Travis on 4/8/2015.
 */
public class MessageService {
    private static MessageListAdapter adapter;
    private static ArrayList<Message> messages;

    public MessageService(MessageListAdapter adapter, ArrayList<Message> messages) {
        this.adapter = adapter;
        this.messages = messages;
    }

    public void addMessage(Message message) {
        long expirationTime = message.getExpirationTime();

        if(expirationTime <= System.currentTimeMillis())
            return;

        messages.add(message);
        adapter.notifyDataSetChanged();

        Timer timer = new Timer();
        RemoveMessageTask task = new RemoveMessageTask(message, this);
        long milliseconds = expirationTime - System.currentTimeMillis();
        timer.schedule(task, milliseconds);

        Toast.makeText(adapter.getContext(), "Setting timer: " + milliseconds, Toast.LENGTH_SHORT).show();
    }

    public void removeMessage(Message message) {
        int index = messages.indexOf(message);
        messages.remove(index);
        adapter.notifyDataSetChanged();

        Toast.makeText(adapter.getContext(), "Removing message", Toast.LENGTH_SHORT).show();
    }
}
