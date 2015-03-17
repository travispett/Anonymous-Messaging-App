package com.pett.travis.proxie.util;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.pett.travis.proxie.R;

/**
 * Created by Travis on 3/1/2015.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }

}
