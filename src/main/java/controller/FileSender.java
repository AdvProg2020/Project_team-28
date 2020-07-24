package controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FileSender extends Thread {
    private final Object lock = new Object();
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private AtomicInteger connectedClients = new AtomicInteger(0);
    private HashMap<String, Integer> accounts = new HashMap<>();

    @Override
    public void run() {
        try {
            startServer();
        } catch (Exception e) {
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            run();
        }
    }

    private void startServer() throws Exception {
        int port = 2000;
        for (; port < 5000; port++) {
            try {
                serverSocket = new ServerSocket(port);
                break;
            } catch (IOException ex) {
                continue; // try next port
            }
        }
        Database.sendPOSTsellerPort(port);
        new Thread(this::listen).start();
    }

    private void listen() {
        while (true) {
            try {
                Socket client = serverSocket.accept();
                connectedClients.incrementAndGet();
                new Thread(() -> {
                    try {
                        handleClient(client);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleClient(Socket client) throws IOException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            Socket threadClient = client;
            //PrintWriter clientOut = new PrintWriter(threadClient.getOutputStream(), true);
            BufferedReader clientIn = new BufferedReader(new InputStreamReader(threadClient.getInputStream()));
            //clientOut.println("connected");
            clientIn.readLine();

            ServerSocket servsock = serverSocket;

            String input = clientIn.readLine();

            File myFile = new File(input);
            byte[] myByteArray = new byte[(int) myFile.length()];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            bis.read(myByteArray, 0, myByteArray.length);
            os = client.getOutputStream();
            System.out.println("Sending " + input + "(" + myByteArray.length + " bytes)");
            os.write(myByteArray, 0, myByteArray.length);
            os.flush();
            System.out.println("Done.");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) bis.close();
            if (os != null) os.close();
            if (client != null) client.close();
        }
    }
}