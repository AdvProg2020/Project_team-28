package graphics;

import com.google.gson.JsonElement;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import controller.Database;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RequestRow extends RecursiveTreeObject<RequestRow> {
    StringProperty title = new SimpleStringProperty();
    StringProperty owner = new SimpleStringProperty();
    StringProperty status = new SimpleStringProperty();
    JsonElement jsonElement;

    public RequestRow(String type) {
        title.setValue(type);
    }

    public RequestRow(JsonElement json) {
        title.setValue(json.getAsJsonObject().get("request-type").getAsString());
        owner.setValue(Database.getUserById(json.getAsJsonObject().get("owner").getAsString()).getUsername());
        status.setValue(json.getAsJsonObject().get("requestStatus").getAsString());
        jsonElement = json;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getOwner() {
        return owner.get();
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
    }

    public StringProperty ownerProperty() {
        return owner;
    }
}
