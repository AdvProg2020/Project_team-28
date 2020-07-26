package graphics;

import controller.Database;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import main.Main;
import model.Chat;
import model.ChatMessage;

import java.util.Objects;

public class ChatBox extends VBox {
    private Chat chat;

    ScrollPane scrollPane;
    VBox messagesBox;
    TextArea textArea;


    public void setChat(Chat chat) {
        this.chat = chat;

        textArea.setDisable(true);
        messagesBox.getChildren().clear();

        if (chat == null)
            return;

        textArea.setDisable(false);

        for (ChatMessage message : chat.getMessages()) {
            addMessageToBox(message);
        }

        StringBuilder members = new StringBuilder();
        for (String username : chat.getUsers()) {
            members.append(username).append(", ");
        }

        messagesBox.getChildren().add(new Label("Chat members:"));
        messagesBox.getChildren().add(new Label(members.substring(0, members.length() - 2)));

    }

    public void updateChat() {
        System.out.println("setting chat messages...");
        if (getChat() != null)
            chat = Database.getChatById(getChat().getId());
        setChat(chat);
    }

    public ChatBox() {
        this.minWidth(400);
        this.maxWidth(400);
        messagesBox = new VBox();
        messagesBox.setPrefWidth(390);
        messagesBox.setStyle("\n" +
                "    -fx-padding: 10px;\n" +
                "    -fx-spacing: 10px; -fx-alignment: top-center");

        scrollPane = new ScrollPane(messagesBox);
        scrollPane.setPrefHeight(250);

        textArea = new TextArea();
        textArea.setDisable(true);
        textArea.setPromptText("Press enter to send...");
        textArea.setPrefHeight(50);

        this.getChildren().addAll(textArea, scrollPane);


        textArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();
                if (event.isShiftDown()) {
                    textArea.appendText(System.getProperty("line.separator"));
                } else {

                    // get chat from server
                    chat = Database.getChatById(chat.getId());
                    assert chat != null;
                    // add message to chat
                    chat.addMessage(new ChatMessage(Main.controller.getUser().getId(), textArea.getText()));
                    // write chat into server
                    try {
                        Database.update(chat, chat.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    this.updateChat();
                    textArea.setText("");
                }
            }
        });
    }

    private void addMessageToBox(ChatMessage message) {
        messagesBox.getChildren().add(0, new MessagePane(message));
    }

    public Chat getChat() {
        return chat;
    }
}
