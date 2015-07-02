package com.pett.travis.proxie.message_service;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.pett.travis.proxie.ForumActivity;
import com.pett.travis.proxie.StartActivity;
import com.pett.travis.proxie.bluetooth.BluetoothService;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by Travis on 4/8/2015.
 */
public class MessageService {
    private static MessageListAdapter adapter;
    private static ArrayList<ProxieMessage> messages;
    public static Handler handler;
    public Activity activity;

    public MessageService(ForumActivity activity, ArrayList<ProxieMessage> messages, MessageListAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
        this.messages = messages;

        handler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message inputMessage) {
                Log.d(StartActivity.TAG, "adding message to the listview");

                ProxieMessage message = (ProxieMessage)inputMessage.obj;
                addMessage(message);
            }
        };

        Intent intent = new Intent(activity.getApplicationContext(), BluetoothService.class);
        activity.startService(intent);
    }

    public void addMessage(ProxieMessage message) {
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

    public void removeMessage(ProxieMessage message) {
        int index = messages.indexOf(message);
        messages.remove(index);
        adapter.notifyDataSetChanged();

        Toast.makeText(adapter.getContext(), "Removing message", Toast.LENGTH_SHORT).show();
    }

    public void stopService() {
        Intent intent = new Intent(activity.getApplicationContext(), BluetoothService.class);
        activity.startService(intent);
    }
}
