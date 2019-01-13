package pw.nergon.licenseclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class LicenseClient {

    private SSLSocket socket;
    private String ip;
    private int port;
    private String response;

    public LicenseClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void configSSL(String tPass, String tPath, String kPass, String kPath) {
        System.setProperty("javax.net.ssl.trustStorePassword", tPass);
        System.setProperty("javax.net.ssl.trustStore", tPath);
        System.setProperty("javax.net.ssl.keyStore", kPath);
        System.setProperty("javax.net.ssl.keyStorePassword", kPass);
    }

    private void connect() {
        try {
            socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(InetAddress.getByName(ip), port);
            socket.setEnabledCipherSuites(socket.getSupportedCipherSuites());
            socket.startHandshake();
            System.out.println("Connected to Server");
            //startListening();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkKey(String key, boolean closeAfter) {
        connect();
        String mac = getMacAddress();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            // Get success message
            response = bufferedReader.readLine();
            JsonParser parser = new JsonParser();
            JsonObject o = parser.parse(response).getAsJsonObject();
            bufferedWriter.write("{\"key\": \""+key+"\", \"closeAfter\": "+closeAfter+", \"mac\": \""+mac+"\"}");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            response = bufferedReader.readLine();
            JsonObject object = parser.parse(response).getAsJsonObject();
            if (closeAfter) {
                socket.close();
            }
            if(object.get("success").getAsBoolean())
                return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private String getMacAddress() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ip);
            byte[] mac = networkInterface.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
            }
            return sb.toString();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        return "00:00:00:00:00:00";
    }


}
