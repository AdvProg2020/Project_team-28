package graphics;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Product;

import java.io.File;
import java.nio.file.Paths;

public class Rating extends HBox {
    double rate;
    Star[] stars = new Star[5];
    Label label = new Label("");

    public Rating() {
        for (int i = 0; i < 5; i++)
            stars[i] = new Star();

        //setAlignment(Pos.CENTER);
        getChildren().addAll(stars);
        getChildren().add(label);
        label.setStyle("-fx-text-fill: #868686; ");
        setSize(20);
    }

    public Rating setRate (Product product) {
        this.rate = product.getAverageScore();
        for (int i = 0; i < 5; i++) {
            stars[i].setFull(rate >= i + 1);
        }
        label.setText(" " + product.getAllScores().size() + " ratings");
        return this;
    }

    public Rating setSize(double height) {
        for (Star star : stars) {
            star.setFitHeight(height);
        }
        label.setStyle(label.getStyle() + "-fx-font-size: " + height * 2 / 3 + "; ");
        return this;
    }

    static class Star extends ImageView {
        final String star1 = "src/main/resources/images/star-full.png";
        final String star0 = "src/main/resources/images/star-empty.png";

        public Star() {
            super();
            setFull(false);
            setPreserveRatio(true);
        }

        public void setFull (boolean full) {
            String imageAddress;
            if (full)
                imageAddress = new File(star1).getAbsolutePath();
            else
                imageAddress = new File(star0).getAbsolutePath();
            setImage(new Image(Paths.get(imageAddress).toUri().toString()));
        }
    }
}
