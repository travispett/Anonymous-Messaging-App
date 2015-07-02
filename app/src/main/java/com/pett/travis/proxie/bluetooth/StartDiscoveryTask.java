package com.pett.travis.proxie.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class StartDiscoveryTask extends TimerTask{

    private BluetoothAdapter bluetoothAdapter = null;
    private Handler handler = null;

    public StartDiscoveryTask(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
        this.handler = new Handler();
    }

    @Override
    public void run() {
        handler.post(new Runnable() {
            public void run() {
                if (!bluetoothAdapter.isDiscovering())
                    bluetoothAdapter.startDiscovery();

                //TODO: Create a user preference to set the frequency of the discovery
                Timer timer = new Timer();
                StartDiscoveryTask task = new StartDiscoveryTask(bluetoothAdapter);
                timer.schedule(task, 10000);
            }
        });
    }
}

