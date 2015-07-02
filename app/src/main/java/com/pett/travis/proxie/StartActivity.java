package com.pett.travis.proxie;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;


public class StartActivity extends Activity {

    private static boolean userLoggedIn = false;
    public static String TAG = "Proxie";
    public static String PROXIE_NO_SERVICE = "proxie";
    public static String PROXIE_SEND_ONLY = "proxie>";
    public static String PROXIE_RECV_ONLY = "proxie<";
    public static String PROXIE_SEND_AND_RECV = "proxie><";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUsername = sharedPreferences.getString("usernameKey", "none");

        if (sharedPreferences.getBoolean("stayLoggedInCheckboxKey", true)) {
            if (!savedUsername.equalsIgnoreCase("none"))
                userLoggedIn = true;
        }

        if (getIntent().hasExtra("loggedIn"))
            userLoggedIn = true;
        if (userLoggedIn) {
            Intent intent = new Intent(this, ForumActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
