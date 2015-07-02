package com.pett.travis.proxie.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;

import com.pett.travis.proxie.StartActivity;

public class BluetoothService extends Service {

    private BluetoothAdapter bluetoothAdapter = null;
    private BroadcastReceiver bluetoothReceiver = null;
    private boolean serverIsRunning = false;

    @Override
    public void onCreate() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "device does not support bluetooth", Toast.LENGTH_SHORT).show();
            return;
        }

        bluetoothAdapter.setName(StartActivity.PROXIE_SEND_ONLY);

        if (!bluetoothAdapter.isEnabled())
            bluetoothAdapter.enable();

        if(bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
            discoverableIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(discoverableIntent);
        } else {
            startServerIfNotRunning();
        }

        bluetoothReceiver = new BluetoothReceiver(this);
        registerReceiver(bluetoothReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        registerReceiver(bluetoothReceiver, new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED));

        Log.d(StartActivity.TAG, "starting discovery service");
        bluetoothAdapter.startDiscovery();

        //TODO: Create a user preference to set the frequency of the discovery
        Timer timer = new Timer();
        StartDiscoveryTask task = new StartDiscoveryTask(bluetoothAdapter);
        timer.schedule(task, 6000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (bluetoothAdapter == null)
            stopSelf(startId);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "stopping bluetooth service", Toast.LENGTH_SHORT).show();

        bluetoothAdapter.setName(StartActivity.PROXIE_NO_SERVICE);

        if (bluetoothAdapter.isDiscovering())
            bluetoothAdapter.cancelDiscovery();

        unregisterReceiver(bluetoothReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    public void peerFound(BluetoothDevice remoteDevice) {
        Log.d(StartActivity.TAG, "peer found. canceling discovery");
        bluetoothAdapter.cancelDiscovery();

        String remoteDeviceName = remoteDevice.getName();
        if (remoteDeviceName == null)
            return;
        if (!remoteDeviceName.equalsIgnoreCase(StartActivity.PROXIE_SEND_AND_RECV))
            return;

        String remoteDeviceAddress = remoteDevice.getAddress();
        String hostAddress = bluetoothAdapter.getAddress();

        if (remoteDeviceAddress.compareTo(hostAddress) > 0) {
            Log.d(StartActivity.TAG, "connecting to server");
            new ClientConnectThread(getApplicationContext(), remoteDevice).start();
        }
        else {
            Log.d(StartActivity.TAG, "waiting for connection");
        }
    }

    public boolean serverIsRunning() {
        return serverIsRunning;
    }

    public void setServerIsRunning(boolean state) {
        serverIsRunning = state;
    }

    public void startServerIfNotRunning() {
        if (serverIsRunning == false)
            startServer();
    }

    public void startServer() {
        new ServerAcceptThread(this).start();
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }



}
