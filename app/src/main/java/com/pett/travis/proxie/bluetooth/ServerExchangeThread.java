package com.pett.travis.proxie.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import com.pett.travis.proxie.ForumActivity;
import com.pett.travis.proxie.StartActivity;
import com.pett.travis.proxie.message_service.MessageService;
import com.pett.travis.proxie.message_service.ProxieMessage;
import com.proxie.SerializedMessage;

public class ServerExchangeThread extends Thread {
    private final BluetoothSocket mmSocket;

    private final ObjectOutputStream mmOutStream;
    private final ObjectInputStream mmInStream;

    public ServerExchangeThread(BluetoothSocket socket) {
        mmSocket = socket;

        ObjectOutputStream tmpOut = null;
        ObjectInputStream tmpIn = null;

        try {
            tmpOut = new ObjectOutputStream(socket.getOutputStream());
            tmpOut.flush();
            tmpIn = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e) {
            Log.d(StartActivity.TAG, "error getting server streams");
        }

        mmOutStream = tmpOut;
        mmInStream = tmpIn;
    }

    public void run() {
        try {
            Log.d(StartActivity.TAG, "writing to client");

            ArrayList<ProxieMessage> messageList = (ArrayList<ProxieMessage>) ForumActivity.messages.clone();

            ArrayList<SerializedMessage> serializedList = new ArrayList<SerializedMessage>();

            Iterator i = messageList.iterator();
            ProxieMessage message;
            SerializedMessage serializedMessage;

            while (i.hasNext()) {
                message = (ProxieMessage) i.next();
                serializedMessage = message.serialize();
                serializedList.add(serializedMessage);
            }

            Log.d(StartActivity.TAG, "writing list");
            mmOutStream.writeObject(serializedList);
            mmOutStream.flush();

            /* Read list from the client and add to listview */
            serializedList = (ArrayList<SerializedMessage>) mmInStream.readObject();

            i = serializedList.iterator();

            Log.d(StartActivity.TAG, "received list");
            while(i.hasNext()) {
                //SerializedMessage serializedMessage = (SerializedMessage) i.next();
                serializedMessage = (SerializedMessage) i.next();
                //ProxieMessage message = new ProxieMessage(serializedMessage);
                message = new ProxieMessage(serializedMessage);
                MessageService.handler.obtainMessage(0, message).sendToTarget();
            }

            //Log.d(StartActivity.TAG, "result from client: " + result);
        }
        catch (IOException e) {
            Log.d(StartActivity.TAG, "error writing to client: " + e.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.d(StartActivity.TAG, "error closing server socket");
        }
    }
}
