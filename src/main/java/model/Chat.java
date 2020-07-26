package model;

import controller.Database;

import java.util.*;

public class Chat implements BaseModel {
    private final String id;
    boolean isPublic = false;
    private final Set<String> users = new HashSet<>();
    private final ArrayList<ChatMessage> messages = new ArrayList<>();
    private final Set<String> usernames = new HashSet<>();

    public Chat() {
        this.id = UUID.randomUUID().toString();
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public void addUser(String userId) {
        users.add(userId);
        usernames.add(Objects.requireNonNull(Database.getUserById(userId)).getUsername());
    }

    private boolean hasUser(String userId) {
        return isPublic || users.contains(userId);
    }

    public void addMessage(ChatMessage message) {
        if (hasUser(message.getSenderId()))
            messages.add(message);
    }

    public List<ChatMessage> getMessages(String userId) {
        if (!hasUser(userId))
            return new ArrayList<>();

        return messages;
    }

    public List<ChatMessage> getMessages(String userId, int from) {
        if (!hasUser(userId))
            return new ArrayList<>();

        if (from >= messages.size())
            from = 0;
        return messages.subList(from, messages.size());
    }

    public String getId() {
        return id;
    }

    public Set<String> getUsers() {
        return users;
    }

    public Set<String> getUsernames() {
        return usernames;
    }
}
