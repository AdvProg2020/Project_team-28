package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
                new Thread(() -> handleClient(client)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleClient(Socket client) {
        try {
            Socket threadClient = client;
            PrintWriter clientOut = new PrintWriter(threadClient.getOutputStream(), true);
            BufferedReader clientIn = new BufferedReader(new InputStreamReader(threadClient.getInputStream()));
            clientOut.println("connected");
            clientIn.readLine();

            String input = clientIn.readLine();


            while (true) {
                input = clientIn.readLine();
                if (input == null) break;
                String[] data = input.split("``");
                String user = data[0];
                int value = Integer.parseInt(data[1]);
                synchronized (lock) {
                    System.out.println("got" + input);
                    if (!accounts.containsKey(data[0])) {
                        accounts.put(user, 0);
                    }
                    System.out.println("user" + data[0] + " " + accounts.get(data[0]));
                    if (accounts.get(user) >= -value) {
                        accounts.put(user, accounts.get(user) + value);
                    }
                }
                clientOut.println("got it");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}