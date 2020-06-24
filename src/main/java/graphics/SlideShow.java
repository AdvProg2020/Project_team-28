package graphics;

import controller.Database;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SlideShow {
    private ArrayList<Node> pages = new ArrayList<>();
    public Pane root;

    public void initialize () throws InterruptedException, IOException {
        for (Product product : Database.getAllProductAds()) {
            addPage(new ProductAdsThumb(product));
        }
        playSlideShow();
    }

    private FadeTransition getFadeTransition (Node node, Interpolator interpolator, String kind) {
        FadeTransition fade = new FadeTransition();
        fade.setNode(node);
        fade.setInterpolator(interpolator);
        fade.setDuration(Duration.millis(1000));
        if (kind.equals("out")) {
            fade.setFromValue(1);
            fade.setToValue(0);
        }else if (kind.equals("in")){
            fade.setFromValue(0);
            fade.setToValue(1);
        }
        fade.setCycleCount(1);
        return fade;
    }

    public void addPage (Node page) {
        pages.add(page);
    }

    public void playSlideShow () {
        final int[] index = {0};
        root.getChildren().add(pages.get(index[0]));
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (index[0] != 0)
                        root.getChildren().remove(pages.get(index[0] - 1));
                    else
                        root.getChildren().remove(pages.get(pages.size() - 1));
                    Node pageToRemove, pageToAdd;
                    pageToRemove = pages.get(index[0]);
                    if (index[0] != pages.size() - 1)
                        pageToAdd = pages.get(index[0] + 1);
                    else {
                        index[0] = -1;
                        pageToAdd = pages.get(0);
                    }
//                    System.out.println("round: " + index[0]);
//                    System.out.println(root.getChildren());
                    FadeTransition out = getFadeTransition(pageToRemove, Interpolator.EASE_OUT, "out");
                    FadeTransition in = getFadeTransition(pageToAdd, Interpolator.EASE_IN, "in");
                    root.getChildren().add(pageToAdd);
                    SequentialTransition transition = new SequentialTransition(out, in);
                    transition.play();
                    index[0]++;
                });
            }
        }, 0, 7000);
    }
}
