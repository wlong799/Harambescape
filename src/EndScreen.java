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

/**
 * End game mode to be played after beating main game. Harambe can move around
 * screen and catch bananas to grow larger. Game ends whenever player decides
 * to press space
 * @author Will Long
 */
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

    /**
     * Initializes end game of specified size and background. Harambe is created
     * and informational text is provided. List of bananas is prepared.
     * @param width is width of screen.
     * @param height is height of screen.
     * @param image is background image.
     */
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

    /**
     * Updates the end game mode. Checks whether or not to spawn a banana.
     * Updates Harambe and all relevant bananas, and checks if Harambe has
     * caught a banana.
     * @param elapsedTime is time spent updating.
     */
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

    /**
     * Creates a banana at a random position on top of screen.
     */
    private void spawnBanana() {
        int xSpawn = random.nextInt(screenWidth);
        EndBanana banana = new EndBanana(xSpawn, 0);
        bananaList.add(banana);
        getChildren().add(banana);
    }

    /**
     * Checks whether Harambe has caught a banana. If he has, removes it from
     * the screen and causes him to grow.
     */
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

