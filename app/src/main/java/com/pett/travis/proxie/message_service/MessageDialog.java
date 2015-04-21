package com.pett.travis.proxie.message_service;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.pett.travis.proxie.ForumActivity;
import com.pett.travis.proxie.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MessageDialog extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_dialog);

        int position = getIntent().getIntExtra(ForumActivity.EXTRA_MESSAGE_POSITION_KEY, -1);
        Message m = ForumActivity.messages.get(position);

        TextView textView = (TextView)findViewById(R.id.messageDialogText);
        textView.setText("Text: " + m.getText());

        textView = (TextView)findViewById(R.id.messageDialogSender);
        textView.setText("Sender: " + m.getSender());

        textView = (TextView)findViewById(R.id.messageDialogTarget);
        textView.setText("Target: " + m.getTarget());

        textView = (TextView)findViewById(R.id.messageDialogSource);
        textView.setText("Source Location: " + m.getSource().toString());

        textView = (TextView)findViewById(R.id.messageDialogExpirationTime);
        String date = new SimpleDateFormat("MM/dd/yyy HH:mm:ss", Locale.US).format(new Date(m.getExpirationTime()));
        textView.setText("Expiration Date: " + date);

        textView = (TextView)findViewById(R.id.messageDialogIsIncoming);
        textView.setText("Created by user: " + m.getCreateByUser());
    }
}
