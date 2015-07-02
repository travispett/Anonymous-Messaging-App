package com.pett.travis.proxie;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.pett.travis.proxie.message_service.ProxieMessage;
import com.pett.travis.proxie.message_service.MessageEditor;
import com.pett.travis.proxie.util.SettingsActivity;


public class ComposeActivity extends ActionBarActivity {
    public static String COMPOSE_MESSAGE_REQUEST = "COMPOSED_MESSAGE";
    private static final int NUM_PAGES = 2;
    private ViewPager mPager = null;
    private PagerAdapter mPagerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        mPager = (ViewPager)findViewById(R.id.composeViewPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
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
            startSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void cancel(View view) {
        NavUtils.navigateUpFromSameTask(this);
    }

    public void startSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void sendMessage(View view){
        EditText editTextTo = (EditText)findViewById(R.id.composeActivityToField);
        EditText editTextMessage = (EditText)findViewById(R.id.composeActivityMessageField);
        String toText = editTextTo.getText().toString();
        String messageText = editTextMessage.getText().toString();
        if(TextUtils.isEmpty(toText))
            toText = ProxieMessage.BROADCAST_MESSAGE;

        //LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        //Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng latLng = new LatLng(0, 0);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int numHops = Integer.parseInt(sharedPreferences.getString("maxNumberHopsEditTextKey", "1"));
        String messageLife = sharedPreferences.getString("messageLifeEditTextKey", "30");
        long expirationTime = System.currentTimeMillis() + (Long.valueOf(messageLife)*1000);

        ProxieMessage message = new ProxieMessage(messageText, ProxieMessage.ANONYMOUS, toText, latLng, numHops, expirationTime, 1);
        Intent intent = new Intent();
        intent.putExtra(COMPOSE_MESSAGE_REQUEST, message);
        this.setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if(mPager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position){
            if(position == 0)
                return new MessageEditor();
            else {
                //                return new MessageSettings();
                return new PreferenceFragment() {
                    @Override
                    public void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        addPreferencesFromResource(R.xml.preferences);
                    }
                };
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}