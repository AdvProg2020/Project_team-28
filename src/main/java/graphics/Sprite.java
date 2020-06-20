package graphics;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Sprite {
    private ImageView imageView;
    private int totalCount;
    private int currentNumber = 0;
    private int laps = 4;
    private Rectangle2D[] clips;

    public Sprite(double imageViewHeight, int rows, int columns, int totalCount, File address, int laps) {
        Image image = new Image(address.toURI().toString());
        this.totalCount = totalCount;
        this.clips = new Rectangle2D[rows * columns];
        this.laps = laps;
        double height = image.getHeight() / rows;
        double width = image.getWidth() / columns;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                clips[i * columns + j] = new Rectangle2D(j * width, i * height,
                        width, height);
            }
        }
        imageView = new ImageView(image);
        imageView.setFitHeight(imageViewHeight);
        imageView.setPreserveRatio(true);
        update();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void update() {
        imageView.setViewport(clips[currentNumber / laps]);
        currentNumber = (currentNumber + 1) % (totalCount * laps);
    }
}
