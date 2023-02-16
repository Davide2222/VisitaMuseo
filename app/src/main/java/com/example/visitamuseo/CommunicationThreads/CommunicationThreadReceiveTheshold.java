package com.example.visitamuseo.CommunicationThreads;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class CommunicationThreadReceiveTheshold {
    private final String host ="10.0.2.15";
    final private int port = 61438;
    String TAG = "Log - CommunicationThreadReceiveTheshold";

    public double getTheshold() {
        String theshold = "10.000";
        try {
            InetAddress serverAddr = InetAddress.getByName(host);
            if (serverAddr.isReachable(4000)) {
                Socket socket = new Socket(serverAddr, port);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                theshold = reader.readLine();
                theshold = theshold.replace("\u0000", "");
                Log.d(TAG, "Chiusura della socket");
                socket.close();
            } else {
                Log.d(TAG, "Il server non è raggiungibile!");
            }
        } catch (IOException e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        return Double.parseDouble(theshold);
    }
}
