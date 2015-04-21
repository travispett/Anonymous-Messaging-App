package com.pett.travis.proxie.message_service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pett.travis.proxie.R;

import java.util.ArrayList;

/**
 * Created by Travis on 4/2/2015.
 */
public class MessageListAdapter extends ArrayAdapter<Message> {
    private Context context;
    private int resource;
    private ArrayList<Message> messages;

    public MessageListAdapter(Context context, int resource, ArrayList<Message> messages) {
        super(context, resource, messages);
        this.context = context;
        this.resource = resource;
        this.messages = messages;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.message_view, null);
        }
        Message message = messages.get(position);

        if (message != null) {
            TextView source = (TextView)view.findViewById(R.id.messageSourceTextView);
            source.setText("(" + message.getSender() + ")");

            TextView text = (TextView)view.findViewById(R.id.messageTextTextView);
            text.setText(message.getText());
        }
        return view;
    }
}
