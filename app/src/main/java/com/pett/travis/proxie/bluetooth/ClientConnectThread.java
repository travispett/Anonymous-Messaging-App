package com.pett.travis.proxie.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

import com.pett.travis.proxie.R;
import com.pett.travis.proxie.StartActivity;

public class ClientConnectThread extends Thread{

    private Context context = null;
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;

    public ClientConnectThread(Context context, BluetoothDevice device) {

        this.context = context;
        BluetoothSocket tmp = null;
        mmDevice = device;

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            String uuidString = context.getString(R.string.uuid);
            UUID uuid = UUID.fromString(uuidString);

            tmp = mmDevice.createInsecureRfcommSocketToServiceRecord(uuid);
        }
        catch (IOException e) {
            Log.d(StartActivity.TAG, "error creating client socket");
        }

        mmSocket = tmp;
    }

    public void run() {

        try {
            Log.d(StartActivity.TAG, "connecting to server");
            mmSocket.connect();
        }
        catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            Log.d(StartActivity.TAG, "error connecting to server");
            closeSocket();
            return;
        }

        Log.d(StartActivity.TAG, "connection established.  starting exchange");
        new ClientExchangeThread(mmSocket).start();
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void closeSocket() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.d(StartActivity.TAG, "closing client socket");
        }
    }

}
