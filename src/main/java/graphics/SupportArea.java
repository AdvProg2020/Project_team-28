package graphics;

import com.jfoenix.controls.JFXButton;
import controller.Database;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import model.Chat;
import model.ChatMessage;
import model.Supporter;
import model.User;

import java.util.ArrayList;

public class SupportArea {
    public VBox onlineBox;
    public ChatBox chatBox;
    public HBox chatListBox;

    ArrayList<String> chatList = new ArrayList<>();

    public void initialize() {
        try {
            ArrayList<String> users = new ArrayList<>();
            //TODO: users = Main.controller.getOnlineUsers();

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
                        chat.addUser(Main.controller.getUser().getId());
                        chat.addUser(userId);
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

            //TODO: chatList = Main.controller.getPersonalChats();

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
        System.out.println("reloading chat area...");
        initialize();
        chatBox.updateChat();
        chatBox.setChat(chatBox.getChat());
    }
}
