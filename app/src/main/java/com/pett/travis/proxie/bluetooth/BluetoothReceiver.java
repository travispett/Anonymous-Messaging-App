package com.pett.travis.proxie.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.pett.travis.proxie.StartActivity;

import java.util.ArrayList;

public class BluetoothReceiver extends BroadcastReceiver {
    //public static ArrayList<BluetoothDevice> bluetoothDeviceArrayList;
    BluetoothService bluetoothService = null;

    public BluetoothReceiver(BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //bluetoothDeviceArrayList = new ArrayList<BluetoothDevice>();
        switch (action) {

            case BluetoothDevice.ACTION_FOUND:
                Log.d(StartActivity.TAG, "ACTION_FOUND");
                BluetoothDevice remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                bluetoothService.peerFound(remoteDevice);
                //bluetoothDeviceArrayList.add(remoteDevice);
                break;

            case BluetoothAdapter.ACTION_SCAN_MODE_CHANGED:
                int scanMode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, -1);
                Log.d(StartActivity.TAG, "scan mode: " + scanMode);

                if (scanMode == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                    Log.d(StartActivity.TAG, "device connectable and discoverable");
                    bluetoothService.startServerIfNotRunning();
                }

                break;

            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
