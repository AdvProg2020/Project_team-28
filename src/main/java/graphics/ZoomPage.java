package graphics;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class ZoomPage {
    static final int size = 300;
    static double initx;
    static double inity;
    static int height;
    static int width;
    static double offSetX, offSetY, zoomlvl;
    public VBox root;
    public BorderPane imageView = new BorderPane();

    public void show(String title, String path) {
        root.setAlignment(Pos.CENTER);
        Image source = new Image(path);

        ImageView image = new ImageView(source);
        double ratio = source.getWidth() / source.getHeight();

        if (size / ratio < size) {
            width = size;
            height = (int) (size / ratio);
        }
        else if (size * ratio < size) {
            height = size;
            width = (int) (size * ratio);
        }
        else {
            height = size;
            width = size;
        }
        image.setPreserveRatio(false);
        image.setFitWidth(width);
        image.setFitHeight(height);
        height = (int) source.getHeight();
        width = (int) source.getWidth();
        HBox zoom = new HBox(10);
        zoom.setAlignment(Pos.CENTER);

        Slider zoomLvl = new Slider();
        zoomLvl.setMax(4);
        zoomLvl.setMin(1);
        zoomLvl.setMaxWidth(200);
        zoomLvl.setMinWidth(200);
        Label hint = new Label("Zoom Level");
        Label value = new Label("1.0");

        offSetX = width / 2.0;
        offSetY = height / 2.0;


        zoom.getChildren().addAll(hint, zoomLvl, value);

        Slider Hscroll = new Slider();
        Hscroll.setMin(0);
        Hscroll.setMax(width);
        Hscroll.setMaxWidth(image.getFitWidth());
        Hscroll.setMinWidth(image.getFitWidth());
        Hscroll.setTranslateY(-20);
        Slider Vscroll = new Slider();
        Vscroll.setMin(0);
        Vscroll.setMax(height);
        Vscroll.setMaxHeight(image.getFitHeight());
        Vscroll.setMinHeight(image.getFitHeight());
        Vscroll.setOrientation(Orientation.VERTICAL);
        Vscroll.setTranslateX(-20);


        BorderPane imageView = new BorderPane();
        BorderPane.setAlignment(Hscroll, Pos.CENTER);
        BorderPane.setAlignment(Vscroll, Pos.CENTER_LEFT);
        Hscroll.valueProperty().addListener(e -> {
            offSetX = Hscroll.getValue();
            zoomlvl = zoomLvl.getValue();
            double newValue = (double) ((int) (zoomlvl * 10)) / 10;
            value.setText(newValue + "");
            if (offSetX < (width / newValue) / 2) {
                offSetX = (width / newValue) / 2;
            }
            if (offSetX > width - ((width / newValue) / 2)) {
                offSetX = width - ((width / newValue) / 2);
            }

            image.setViewport(new Rectangle2D(offSetX - ((width / newValue) / 2), offSetY - ((height / newValue) / 2), width / newValue, height / newValue));
        });
        Vscroll.valueProperty().addListener(e -> {
            offSetY = height - Vscroll.getValue();
            zoomlvl = zoomLvl.getValue();
            double newValue = (double) ((int) (zoomlvl * 10)) / 10;
            value.setText(newValue + "");
            if (offSetY < (height / newValue) / 2) {
                offSetY = (height / newValue) / 2;
            }
            if (offSetY > height - ((height / newValue) / 2)) {
                offSetY = height - ((height / newValue) / 2);
            }
            image.setViewport(new Rectangle2D(offSetX - ((width / newValue) / 2), offSetY - ((height / newValue) / 2), width / newValue, height / newValue));
        });
        imageView.setCenter(image);
        imageView.setTop(Hscroll);
        imageView.setRight(Vscroll);
        zoomLvl.valueProperty().addListener(e -> {
            zoomlvl = zoomLvl.getValue();
            double newValue = (double) ((int) (zoomlvl * 10)) / 10;
            value.setText(newValue + "");
            if (offSetX < (width / newValue) / 2) {
                offSetX = (width / newValue) / 2;
            }
            if (offSetX > width - ((width / newValue) / 2)) {
                offSetX = width - ((width / newValue) / 2);
            }
            if (offSetY < (height / newValue) / 2) {
                offSetY = (height / newValue) / 2;
            }
            if (offSetY > height - ((height / newValue) / 2)) {
                offSetY = height - ((height / newValue) / 2);
            }
            Hscroll.setValue(offSetX);
            Vscroll.setValue(height - offSetY);
            image.setViewport(new Rectangle2D(offSetX - ((width / newValue) / 2), offSetY - ((height / newValue) / 2), width / newValue, height / newValue));
        });
        imageView.setCursor(Cursor.OPEN_HAND);
        image.setOnMousePressed(e -> {
            initx = e.getSceneX();
            inity = e.getSceneY();
            imageView.setCursor(Cursor.CLOSED_HAND);
        });
        image.setOnMouseReleased(e -> imageView.setCursor(Cursor.OPEN_HAND));
        image.setOnMouseDragged(e -> {
            Hscroll.setValue(Hscroll.getValue() + (initx - e.getSceneX()));
            Vscroll.setValue(Vscroll.getValue() - (inity - e.getSceneY()));
            initx = e.getSceneX();
            inity = e.getSceneY();
        });
        Label label = new Label(title);
        root.getChildren().addAll(label, imageView, zoom);

        Main.setPopupStageSize(image.getFitWidth() + 70, (image.getFitHeight()) + 150);
    }
}
