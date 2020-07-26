package graphics;

import com.jfoenix.controls.JFXButton;
import controller.Database;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import model.Chat;
import model.Supporter;
import model.User;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Objects;

public class SupportArea {
    public VBox onlineBox;
    public ChatBox chatBox;
    public HBox chatListBox;

    ArrayList<String> chatList = new ArrayList<>();

    public void initialize() {
        System.out.println("initializing...");
        try {
            ArrayList<String> users;
            users = Database.getOnlineUsers();

            onlineBox.getChildren().clear();
            for (String userId : users) {
                User user = Database.getUserById(userId);
                if (user instanceof Supporter) {
                    Hyperlink link = new Hyperlink(user.getUsername());
                    onlineBox.getChildren().add(link);

                    link.setOnAction(event -> {
                        if (Main.controller.getUser().getType().equals("supporter"))
                            return;

                        Chat chat = new Chat();

                        // add customer and supporter to chat members
                        chat.addUser(Main.controller.getUser().getUsername());
                        chat.addUser(Objects.requireNonNull(Database.getUserById(userId)).getUsername());

                        try {
                            Database.add(chat);
                            chatList.add(chat.getId());
                            updateChatBox();
                            chatBox.setChat(chat);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }

            chatList = new ArrayList<>();

            for (Chat chat : Database.getAllChats()) {
                if (chat.hasUser(Main.controller.getUser().getUsername())) {
                    chatList.add(chat.getId());
                }
            }

            updateChatBox();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot get online users");
        }
    }

    private void updateChatBox() {
        chatListBox.getChildren().clear();
        for (int i = 0; i < chatList.size(); i++) {
            JFXButton button = new JFXButton("Chat #" + (i + 1));
            int finalI = i;
            button.setOnMouseClicked(event -> chatBox.setChat(Database.getChatById(chatList.get(finalI))));
            button.getStyleClass().add("chatButton");
            chatListBox.getChildren().add(button);
        }
    }

    public void reloadPressed() {
        System.out.println("reloading...");
        initialize();
        chatBox.updateChat();
        //chatBox.setChat(chatBox.getChat());
    }
}
