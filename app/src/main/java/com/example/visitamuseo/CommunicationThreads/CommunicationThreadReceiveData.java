package com.example.visitamuseo.CommunicationThreads;

import android.content.Context;
import android.util.Log;

import com.example.visitamuseo.model.Art;
import com.example.visitamuseo.model.Exhibition;
import com.example.visitamuseo.utils.internalDatabase.DbManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.stream.Collectors;

public class CommunicationThreadReceiveData {
    private final String host ="192.168.178.51";
    final private int port =61440;
    String TAG = "Log - CommunicationThreadReceiveData";
    private String userType;
    private Context context;

    public CommunicationThreadReceiveData(Context context,String userType) {
        this.context=context;
        this.userType = userType;
    }

    public void getData() {
        DbManager database = DbManager.getDbInstance(context);
        try {
            InetAddress serverAddress = InetAddress.getByName(host);
            if (serverAddress.isReachable(40000)) {
                Socket socket = new Socket(serverAddress, port);
                //Invio il tipo di utente che ha fatto il login
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                out.println("DATA:"+userType);
                Log.d(TAG, "In attesa della risposta dalla socket");
                //Ricezione delle mostre e opere d'arte con la descrizione personalizzata
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response=reader.lines().collect(Collectors.joining(System.lineSeparator())).replace("\u0000", "");
                System.out.println(response);

                //Il buffer non contiene un json, ma è scritto in un formato equivalente al
                // json in questo modo sfruttiamo i parser già disponibili

                JSONObject jsonObject = new JSONObject(response);
                JSONArray exhibitions = jsonObject.getJSONArray("exhibitions");
                for (int i = 0; i < exhibitions.length(); i++) {
                    JSONObject exhibition = exhibitions.getJSONObject(i);
                    String nameExhibition = exhibition.getString("name");
                    String descriptionEx = exhibition.getString("description");
                    String linkEx = exhibition.getString("link");

                    JSONArray arts = exhibition.getJSONArray("arts");
                    for (int y = 0; y < arts.length()-1; y++) {
                        JSONObject art = arts.getJSONObject(y);
                        String artName = art.getString("artName");
                        String author = art.getString("author");
                        String artDescription = art.getString("description");
                        String linkArt = art.getString("link");
                        String exhibitionContainer=nameExhibition;

                        Art tempArt=new Art(artName,author,artDescription,linkArt);
                        tempArt.setNameExhibition(exhibitionContainer);
                        database.artDao().insertArt(tempArt);
                    }

                    Exhibition tempExhibition=new Exhibition(nameExhibition,descriptionEx,linkEx);
                    database.exhibitionsDao().insertExhibition(tempExhibition);
                    database.exhibitionsDao().insertExhibition();

                }
                reader.close();
                out.close();
                socket.close();
            } else {
                Log.d(TAG, "Il server non è raggiungibile! Non è stato possibile inviare i dati");
            }
        } catch (IOException |JSONException io) {
            Log.d(TAG, io.getLocalizedMessage());
            io.printStackTrace();
        }
    }
}
