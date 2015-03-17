package com.pett.travis.proxie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pett.travis.proxie.util.LocationServer;
import com.pett.travis.proxie.util.SettingsActivity;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LocationServer locationServer = new LocationServer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocationServer locationServer = new LocationServer(this);
        locationServer.start();

        String key = getString(R.string.DisplayMarkersPreferenceKey);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean displayMarkers = sharedPreferences.getBoolean(key, false);

        Context context = getApplicationContext();
        Toast.makeText(context, "Display Markers: " + displayMarkers.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        LocationServer locationServer = new LocationServer(this);
        locationServer.stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //Context context = getApplicationContext();
        switch (id) {
            case R.id.action_settings:
                startSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void startSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
