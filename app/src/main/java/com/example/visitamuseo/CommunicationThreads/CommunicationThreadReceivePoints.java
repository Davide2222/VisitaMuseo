package com.example.visitamuseo.CommunicationThreads;

public class CommunicationThreadReceivePoints {
   /* private final String host ="13.95.3.73";
    final private int port =61440;
    String TAG = "Log - CommunicationThreadReceivePoints";

    public ArrayList<Pothole> getPotholes() {
        ArrayList<Pothole> potholes = null;
        try {
            InetAddress serverAddr = InetAddress.getByName(host);
            if (serverAddr.isReachable(4000)) {
                potholes = new ArrayList<>();
                Socket socket = new Socket(serverAddr, port);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String responseBuffer;
                while (true){
                    responseBuffer = reader.readLine();
                    responseBuffer = responseBuffer.replace("\u0000", "");
                    if (responseBuffer.isEmpty() || responseBuffer.equals("Done")){
                        Log.d(TAG, "Terminati i dati da ricevere");
                        break;
                    }
                    String[] fields = responseBuffer.split(";");
                    potholes.add(new Pothole(fields[0],Double.parseDouble(fields[1]), Double.parseDouble(fields[2]), Integer.parseInt(fields[3])));
                }
                Log.d(TAG, "Chiusura della socket");
                socket.close();
            } else {
                Log.d(TAG, "Il server non è raggiungibile!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return potholes;
    }*/
}
