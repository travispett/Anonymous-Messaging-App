package com.pett.travis.proxie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pett.travis.proxie.message_service.Message;
import com.pett.travis.proxie.message_service.MessageDialog;
import com.pett.travis.proxie.message_service.MessageListAdapter;
import com.pett.travis.proxie.message_service.MessageService;
import com.pett.travis.proxie.util.SettingsActivity;

import java.util.ArrayList;


public class ForumActivity extends ActionBarActivity {

    public static ArrayList<Message> messages;
    private MessageService mService;
    public static String EXTRA_MESSAGE_POSITION_KEY;
    public static int COMPOSE_MESSAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        messages = new ArrayList<Message>();
        MessageListAdapter adapter = new MessageListAdapter(this, android.R.layout.simple_list_item_1, messages);
        ListView listView = (ListView) findViewById(R.id.forumListView);
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener messageClickedListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent i = new Intent(parent.getContext(), MessageDialog.class);
                i.putExtra(ForumActivity.EXTRA_MESSAGE_POSITION_KEY, position);
                startActivity(i);
            }
        };
        listView.setOnItemClickListener(messageClickedListener);

        mService = new MessageService(adapter, messages);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forum, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.forum_action_settings:
                startSettings();
                return true;
            case R.id.forum_action_contacts:
                startContacts();
                return true;
            case R.id.forum_action_map:
                startMap();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void startSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void startContacts () {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

    public void startMap() {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void startCompose(View view) {
        Intent intent = new Intent(this, ComposeActivity.class);
        startActivityForResult(intent, COMPOSE_MESSAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COMPOSE_MESSAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Message message = data.getParcelableExtra(ComposeActivity.COMPOSE_MESSAGE_REQUEST);
                mService.addMessage(message);
            }
        }
    }
}
