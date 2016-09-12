import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class EndScreen extends Group {
    private static final int BOTTOM_PADDING = 50;

    private static final String OPTIONS = "Catch the bananas!\n"+
                                          "Press SPACE when ready to exit.";

    private ImageView background;
    private Text optionText;

    private int screenWidth, screenHeight;
    private List<EndBanana> bananaList;
    private EndHarambe endHarambe;

    private Random random;

    EndScreen(int width, int height, Image image) {
        random = new Random();
        screenWidth = width;
        screenHeight = height;
        bananaList = new ArrayList<EndBanana>();
        endHarambe = new EndHarambe(0, 0);

        background = new ImageView(image);
        background.setFitHeight(height);
        background.setPreserveRatio(true);
        background.setViewport(new Rectangle2D(0, 0, width, height));

        optionText = new Text(0, height-BOTTOM_PADDING, OPTIONS);
        optionText.setWrappingWidth(width);
        optionText.setTextAlignment(TextAlignment.CENTER);
        optionText.setFont(Font.font("Monospace", 24));

        getChildren().add(background);
        getChildren().add(optionText);
        getChildren().add(endHarambe);
    }

    int getWidth() {
        return screenWidth;
    }

    int getHeight() {
        return screenHeight;
    }

    EndHarambe getHarambe() {
        return endHarambe;
    }

    List<EndBanana> getBananaList() {
        return bananaList;
    }

    void update(double elapsedTime) {
        if (random.nextDouble() <= elapsedTime) {
            spawnBanana();
        }
        endHarambe.update(this, elapsedTime);
        for (EndBanana banana : bananaList) {
            banana.update(this, elapsedTime);
        }
        checkBananaCatches();
    }

    private void spawnBanana() {
        int xSpawn = random.nextInt(screenWidth);
        EndBanana banana = new EndBanana(xSpawn, 0);
        bananaList.add(banana);
        getChildren().add(banana);
    }

    private void checkBananaCatches() {
        List<EndBanana> caughtBananas = new ArrayList<EndBanana>();
        for (EndBanana banana : bananaList) {
            if (endHarambe.intersects(banana.getLayoutBounds())) {
                caughtBananas.add(banana);
                endHarambe.grow();
            } else if (banana.getY() > screenHeight) {
                caughtBananas.add(banana);
            }
        }
        bananaList.removeAll(caughtBananas);
        getChildren().removeAll(caughtBananas);
    }
}

