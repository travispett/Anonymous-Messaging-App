package com.pett.travis.proxie.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

import com.pett.travis.proxie.StartActivity;
import com.pett.travis.proxie.R;

public class ServerAcceptThread extends Thread {
    private String TAG = StartActivity.TAG;
    private BluetoothService bluetoothService = null;
    private final BluetoothServerSocket mmServerSocket;

    public ServerAcceptThread(BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;

        BluetoothServerSocket tmp = null;
        try {
            Context context = bluetoothService.getApplicationContext();
            String appName = context.getString(R.string.app_name);
            String uuidString = context.getString(R.string.uuid);
            UUID uuid = UUID.fromString(uuidString);

            BluetoothAdapter bluetoothAdapter = bluetoothService.getBluetoothAdapter();
            tmp = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName, uuid);
        }
        catch (IOException e) {
            Log.d(StartActivity.TAG, "error creating server socket");
        }

        mmServerSocket = tmp;
    }

    public void run() {
        if (mmServerSocket == null) {
            return;
        }

        Log.d(TAG, "starting proxie server");
        BluetoothSocket socket = null;
        bluetoothService.setServerIsRunning(true);
        bluetoothService.getBluetoothAdapter().setName(StartActivity.PROXIE_SEND_AND_RECV);

        while (mmServerSocket != null) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                break;
            }

            if (socket != null) {
                Log.d(TAG, "accepted connection");

                new ServerExchangeThread(socket).start();
            }
        }

        bluetoothService.setServerIsRunning(false);
        bluetoothService.getBluetoothAdapter().setName(StartActivity.PROXIE_SEND_ONLY);
        cancel();
    }

    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) { }
    }

}
