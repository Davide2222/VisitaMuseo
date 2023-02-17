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

public class CommunicationThreadRegister {
    private final String host = "192.168.178.51";
    final private int port = 61440;
    private String userData;
    String TAG = "Log - CommunicationThreadSenderUserForSignIn";

    public CommunicationThreadRegister(String userData) {
        this.userData = userData;
    }

    public boolean registerUser() {
        try {
            InetAddress serverAddress = InetAddress.getByName(host);
            if (serverAddress.isReachable(8000)) {
                Socket socket = new Socket(serverAddress, port);
                //Invio dati
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                out.println("SIGNUP:"+userData);
                Log.d(TAG, "In attesa della risposta dalla socket");
                //Ricezione risposta
               BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
               String response = reader.readLine();
                out.close();
                socket.close();
                response = response.replace("\u0000", "");
                System.out.println(response);
                return response.equals("User added");
            } else {
                Log.d(TAG, "Il server non è raggiungibile! Non è stato possibile inviare i dati");
            }
        } catch (IOException io) {
            Log.d(TAG, io.getLocalizedMessage());
        }
        return false;
    }

}
