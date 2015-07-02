package com.pett.travis.proxie.message_service;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pett.travis.proxie.R;

public class MessageSettings extends PreferenceFragment {

    static View view = null;

    public MessageSettings() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message_settings, container, false);
        addPreferencesFromResource(R.xml.preferences);
        return view;
    }

}
