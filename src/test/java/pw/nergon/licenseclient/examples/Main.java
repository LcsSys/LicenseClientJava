package pw.nergon.licenseclient.examples;

import pw.nergon.licenseclient.LicenseClient;

public class Main {

    public static void main(String[] args) {
        LicenseClient client = new LicenseClient("localhost",5678);
        client.configSSL("trustStorePass", "trustStore", "keyStorePass", "keyStore");
        if (!client.checkKey("DDDDDDDDDDDDDDD", true)) {
            System.out.println("LicenseKey invalid");
            System.exit(1);
        }
        System.out.println("LicenseKey accepted!");

    }

}
