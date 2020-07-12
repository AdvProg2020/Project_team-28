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
        String stringBuilder = "create_receipt" + " " +
                token + " " +
                receiptType + " " +
                money + " " +
                sourceId + " " +
                destId + " " +
                money + "_" + receiptType + "_from_" +
                sourceId + "_to_" + destId;
        return sendMessage(stringBuilder);
    }

    private String payReceipt (String receiptId) throws IOException {
        return sendMessage("pay " + receiptId);
    }

    public String createBankAccount (User user) throws IOException {
        String message = "create_account " + user.getFirstName() + " " +
                user.getSurname() + " " +
                user.getUsername() + " " +
                user.getPassword() + " " +
                user.getPassword();
        return sendMessage(message);
    }

    public String createShopAccount () throws Exception {
        String message = "create_account " + "MyShop" + " " +
                "OnlineShop" + " " +
                "shopAccount" + " " +
                "1234" + " " +
                "1234";
        return sendMessage(message);
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
