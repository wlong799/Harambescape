import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.HashSet;

class Game {
    private static final int NUM_LEVELS = 3;
    private static final KeyCode CHEAT_KEY = KeyCode.CONTROL;

    private Scene scene;
    private Group sceneRoot;
    private int sceneWidth, sceneHeight;

    private Level currentLevel;
    private Background background;
    private Harambe harambe;

    private HashSet<KeyCode> pressedKeys;

    Game(int width, int height) {
        sceneWidth = width;
        sceneHeight = height;
        sceneRoot = new Group();
        scene = new Scene(sceneRoot, sceneWidth, sceneHeight);

        currentLevel = new Level(1, sceneHeight);
        Image bgImage = new Image(getClass().getClassLoader().getResourceAsStream("images/city.png"));
        background = new Background(bgImage, currentLevel.getWidth(), currentLevel.getHeight());
        harambe = new Harambe(0, 0);
        sceneRoot.getChildren().add(background);
        sceneRoot.getChildren().add(currentLevel);
        sceneRoot.getChildren().add(harambe);

        pressedKeys = new HashSet<KeyCode>();
        scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
    }

    Scene getScene() {
        return scene;
    }

    public void update(double elapsedTime) {
        resolveKeyPresses();
        harambe.updateMovement(currentLevel);
        scrollLevel();
        if (harambe.getX() + harambe.getWidth() >= currentLevel.getWidth()) {
            nextLevel();
        }
    }

    void resolveKeyPresses() {
        if (pressedKeys.contains(CHEAT_KEY)) {
            resolveCheatCode();
            return;
        }

        if (pressedKeys.contains(KeyCode.D) && !pressedKeys.contains(KeyCode.A)) {
            harambe.moveRight();
        } else if (pressedKeys.contains(KeyCode.A) && !pressedKeys.contains(KeyCode.D)) {
            harambe.moveLeft();
        } else {
            harambe.stall();
        }

        if (pressedKeys.contains(KeyCode.W)) {
            harambe.jump();
        }
        if (pressedKeys.contains(KeyCode.SPACE)) {
            harambe.throwBanana();
        }
    }

    void resolveCheatCode() {
        if (pressedKeys.contains(KeyCode.D)) {
            harambe.setX(harambe.getX() + 100);
            harambe.setY(0);
        }
        if (pressedKeys.contains(KeyCode.A)) {
            harambe.setX(harambe.getX() - 100);
            harambe.setY(0);
        }
    }

    void scrollLevel() {
        double offset = sceneWidth * 1.0 / 3;
        if (harambe.getX() < offset) {
            return;
        }
        sceneRoot.setLayoutX(-1 * harambe.getX() + offset);
        double minLayout = -1 * currentLevel.getWidth() + sceneWidth;
        if (sceneRoot.getLayoutX() < minLayout) {
            sceneRoot.setLayoutX(minLayout);
        }
    }

    void nextLevel() {
        sceneRoot.getChildren().clear();
        harambe.setX(0);
        harambe.setY(0);
        int levelNum = currentLevel.getLevelNumber();
        if (levelNum == NUM_LEVELS) {
            System.exit(0);
        }
        currentLevel = new Level(levelNum + 1, sceneHeight);
        sceneRoot.getChildren().add(background);
        sceneRoot.getChildren().add(currentLevel);
        sceneRoot.getChildren().add(harambe);
        sceneRoot.setLayoutX(0);
    }
}

