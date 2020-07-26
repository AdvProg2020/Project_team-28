package controller;

import com.google.gson.JsonObject;
import model.Product;
import model.Seller;

import java.io.*;
import java.net.Socket;

public class FileGetter {

    public final static int FILE_SIZE = 6022386; // file size temporary hard coded
    public static int SOCKET_PORT = 13267;      // you may change this
    public static String SERVER = "127.0.0.1";  // localhost
    // different name because i don't want to
    // overwrite the one used by server...
    public static String
            FILE_TO_RECEIVED = "c:/temp/source-downloaded.pdf";  // you may change this, I give a
    private static String SELLER_TOKEN;
    private static String FILE_ADDRESS;
    // should bigger than the file to be downloaded

    public static void getProduct(Product product) throws Exception {
        Seller seller = product.getSellers().get(0);
        FILE_ADDRESS = product.getFileAddress();
        FILE_TO_RECEIVED = "C:\\Users\\morabba\\myDownloads\\download";
        JsonObject jsonObject = Database.sendGET("p2p?seller=" + seller.getUsername());
        SOCKET_PORT = Integer.parseInt(jsonObject.get("port").getAsString());
        SERVER = jsonObject.get("ip").getAsString();
        SELLER_TOKEN = jsonObject.get("sellerToken").getAsString();
        get();
    }

    public static void get() throws IOException {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try {
            sock = new Socket(SERVER, SOCKET_PORT);
            System.out.println("Connecting...");
            PrintWriter clientOut = new PrintWriter(sock.getOutputStream(), true);

            clientOut.println(FILE_ADDRESS);
            System.out.println("client: " + FILE_ADDRESS);
            // receive file
            byte[] mybytearray = new byte[FILE_SIZE];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream(FILE_TO_RECEIVED);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            current = bytesRead;

            do {
                bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0) current += bytesRead;
            } while (bytesRead > -1);

            bos.write(mybytearray, 0, current);
            bos.flush();
            System.out.println("File " + FILE_TO_RECEIVED
                    + " downloaded (" + current + " bytes read)");
        } finally {
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (sock != null) sock.close();
        }
    }

}