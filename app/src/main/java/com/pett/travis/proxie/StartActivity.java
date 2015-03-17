package com.pett.travis.proxie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class StartActivity extends Activity {

    private static boolean userLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra("loginSuccessful"))
            userLoggedIn = true;
        if (userLoggedIn) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

}
