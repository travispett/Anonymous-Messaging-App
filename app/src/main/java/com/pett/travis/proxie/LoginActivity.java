package com.pett.travis.proxie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends ActionBarActivity {
    private boolean newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedUsername = sharedPreferences.getString("usernameKey", "none");

        if(savedUsername.equalsIgnoreCase("none")) {
            Toast.makeText(getApplicationContext(), "New User", Toast.LENGTH_SHORT).show();
            newUser = true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void validateLogin(View view) {
        EditText usernameView = (EditText)findViewById(R.id.usernameEditText);
        usernameView.setError(getString(R.string.errorIncorrectLogin));
        usernameView.requestFocus();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString("usernameKey", usernameView.getText().toString()).apply();

        Intent intent = new Intent(this, StartActivity.class);
        intent.putExtra("loginSuccessful", "true");
        startActivity(intent);
    }
}
