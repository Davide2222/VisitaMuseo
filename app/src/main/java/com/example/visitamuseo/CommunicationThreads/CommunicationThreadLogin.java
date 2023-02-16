package com.example.visitamuseo.CommunicationThreads;

import android.util.Log;

import com.example.visitamuseo.model.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class CommunicationThreadLogin {
    private final String host = "192.168.178.28";
    final private int port = 10006;
    private String userData;
    String TAG = "Log - CommunicationThreadSenderUserForLogin";

    public CommunicationThreadLogin(String userData) {
        this.userData = userData;
    }

    public User checkUser() {
        User user = null;
        try {
            InetAddress serverAddress = InetAddress.getByName(host);
            if (serverAddress.isReachable(8000)) {
                Socket socket = new Socket(serverAddress, port);
                //Invio dati
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                out.println(userData);
                Log.d(TAG, "In attesa della risposta dalla socket");
                //Ricezione risposta
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String userData = reader.readLine();
                if (userData != "NoMatch") {
                    //è stata trovata una coorispondenza nel db
                    String[] fields = userData.split(",");
                    user = new User(fields[0], fields[1], fields[2]);
                }
                Log.d(TAG, userData);
                out.close();
                socket.close();
            } else {
                Log.d(TAG, "Il server non è raggiungibile! Non è stato possibile inviare i dati");
            }
        } catch (IOException io) {
            Log.d(TAG, io.getLocalizedMessage());
        }
        return user;
    }
}