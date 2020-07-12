package model;

import java.io.*;
import java.net.Socket;

public class BankAPI {
    private final int bankPort = 2222;
    private final String bankIP = "127.0.0.1";
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public BankAPI() throws IOException {
        socket = new Socket(bankIP, bankPort);
        input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    private String sendMessage (String message) throws IOException {
        output.writeUTF(message);
        output.flush();
        return input.readUTF();
    }

    private String createReceipt (String token, String receiptType, long money, String sourceId,
                                  String destId) throws IOException {
        StringBuilder stringBuilder = new StringBuilder("create_receipt").append(" ")
                .append(token).append(" ")
                .append(receiptType).append(" ")
                .append(money).append(" ")
                .append(sourceId).append(" ")
                .append(destId).append(" ")
                .append(money).append("_").append(receiptType).append("_from_")
                .append(sourceId).append("_to_").append(destId);
        return sendMessage(stringBuilder.toString());
    }

    private String payReceipt (String receiptId) throws IOException {
        return sendMessage("pay " + receiptId);
    }

    public String createBankAccount (User user) throws IOException {
        StringBuilder message = new StringBuilder("create_account ");
        message.append(user.getFirstName()).append(" ")
                .append(user.getSurname()).append(" ")
                .append(user.getUsername()).append(" ")
                .append(user.getPassword()).append(" ")
                .append(user.getPassword()).append(" ");
        return sendMessage(message.toString());
    }

    public String getToken (String username, String password) throws IOException {
        return sendMessage("get_token " + username + " " + password);
    }

    public String moveBalance (String token, long money, String sourceId, String destId) throws IOException {
        String receiptId = createReceipt(token, "move", money, sourceId, destId);
        return payReceipt(receiptId);
    }

    public String depositBalance (String token, long money, String destId) throws IOException {
        String receiptId = createReceipt(token, "deposit", money, "-1", destId);
        return payReceipt(receiptId);
    }

    public String withdrawBalance (String token, long money, String sourceId) throws IOException {
        String receiptId = createReceipt(token, "withdraw", money, sourceId, "-1");
        return payReceipt(receiptId);
    }

    public void finish () throws IOException {
        sendMessage("exit");
    }
}
