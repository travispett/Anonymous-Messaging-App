package com.pett.travis.proxie.message_service;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pett.travis.proxie.R;

public class MessageEditor extends Fragment {

    public MessageEditor() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message_editor, container, false);
    }

}
