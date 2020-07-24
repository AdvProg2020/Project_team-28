package model;

import java.util.UUID;

public class PossibleSupporter implements BaseModel {
    private String id;
    private String username;

    public PossibleSupporter() {
        this.id = UUID.randomUUID().toString();
    }

    public PossibleSupporter(String username) {
        this.username = username;
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
