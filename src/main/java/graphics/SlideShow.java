package graphics;

import controller.Database;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.Main;
import model.Product;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SlideShow extends Pane {
    private ArrayList<Node> pages = new ArrayList<>();
    private final int[] index = {0};

    public SlideShow() throws MalformedURLException {
        setMaxHeight(300);
        setMaxWidth(300);

        FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/fxml/SlideShow.fxml").toURI().toURL());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                Product product = ((ProductAdsThumb) pages.get(index[0])).product;
                FXMLLoader fxmlLoader = Main.setMainStage(product.getName(), "src/main/resources/fxml/ProductPage.fxml");
                ((ProductPage) fxmlLoader.getController()).setProduct(product);
            }
        });
    }

    public void initialize() throws InterruptedException, IOException {
        if (Database.getAllProductAds().size() == 0) {
            addPage(null);
        }
        for (Product product : Database.getAllProductAds()) {
            addPage(new ProductAdsThumb(product));
        }
        playSlideShow();
    }

    private FadeTransition getFadeTransition(Node node, Interpolator interpolator, String kind) {
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
        getChildren().add(pages.get(index[0]));
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (index[0] != 0)
                        getChildren().remove(pages.get(index[0] - 1));
                    else
                        getChildren().remove(pages.get(pages.size() - 1));
                    Node pageToRemove, pageToAdd;
                    pageToRemove = pages.get(index[0]);
                    if (index[0] != pages.size() - 1)
                        pageToAdd = pages.get(index[0] + 1);
                    else {
                        index[0] = -1;
                        pageToAdd = pages.get(0);
                    }
//                    System.out.println("round: " + index[0]);
//                    System.out.println(getChildren());
                    FadeTransition out = getFadeTransition(pageToRemove, Interpolator.EASE_OUT, "out");
                    FadeTransition in = getFadeTransition(pageToAdd, Interpolator.EASE_IN, "in");
                    getChildren().add(pageToAdd);
                    SequentialTransition transition = new SequentialTransition(out, in);
                    transition.play();
                    index[0]++;
                });
            }
        }, 0, 7000);
    }
}
