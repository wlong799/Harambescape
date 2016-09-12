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

    private Harambe harambe;
    private Image bgImage;
    private Background background;
    private Level currentLevel;
    boolean finishedGame;

    private HashSet<KeyCode> pressedKeys;

    Game(int width, int height) {
        sceneWidth = width;
        sceneHeight = height;
        sceneRoot = new Group();
        scene = new Scene(sceneRoot, sceneWidth, sceneHeight);

        bgImage = new Image(getClass().getClassLoader().getResourceAsStream("images/city.png"));
        StartScreen startScreen = new StartScreen(sceneWidth, sceneHeight, bgImage);
        sceneRoot.getChildren().add(startScreen);

        finishedGame = false;

        pressedKeys = new HashSet<KeyCode>();
        scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
    }

    Scene getScene() {
        return scene;
    }

    public void update(double elapsedTime) {
        if (currentLevel == null) {
            if (!finishedGame) {
                resolveStartOptions();
            } else {
                resolveEndOptions();
            }
            return;
        }

        resolveKeyPresses();
        harambe.updateMovement(currentLevel);
        scrollLevel();
        if (harambe.getX() + harambe.getWidth() >= currentLevel.getWidth()) {
            advanceLevels();
        }
    }

    void resolveStartOptions() {
        if (pressedKeys.contains(KeyCode.SPACE)) {
            advanceLevels();
        }
    }

    void resolveEndOptions() {
        if (pressedKeys.contains(KeyCode.SPACE)) {
            System.exit(0);
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
        for (KeyCode kc : pressedKeys) {
            if (kc.isDigitKey()) {
                int level = Integer.parseInt(kc.getName());
                skipToLevel(level);
                break;
            }
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

    void advanceLevels() {
        sceneRoot.getChildren().clear();

        int levelNum = 0;
        if (currentLevel != null) {
            levelNum = currentLevel.getLevelNumber();
        }
        if (levelNum == NUM_LEVELS) {
            currentLevel = null;
            finishedGame = true;
            EndScreen endScreen = new EndScreen(sceneWidth, sceneHeight, bgImage);
            sceneRoot.getChildren().add(endScreen);
            return;
        }

        harambe = new Harambe(0, 0);
        currentLevel = new Level(levelNum + 1, sceneHeight);
        background = new Background(bgImage, currentLevel.getWidth(), currentLevel.getHeight());

        sceneRoot.getChildren().add(background);
        sceneRoot.getChildren().add(currentLevel);
        sceneRoot.getChildren().add(harambe);
        sceneRoot.setLayoutX(0);
    }

    void skipToLevel(int levelNum) {
        sceneRoot.getChildren().clear();

        if (levelNum <= 0) {
            StartScreen startScreen = new StartScreen(sceneWidth, sceneHeight, bgImage);
            sceneRoot.getChildren().add(startScreen);
            finishedGame = false;
            currentLevel = null;
            sceneRoot.setLayoutX(0);
        } else if (levelNum > NUM_LEVELS) {
            EndScreen endScreen = new EndScreen(sceneWidth, sceneHeight, bgImage);
            sceneRoot.getChildren().add(endScreen);
            finishedGame = true;
            currentLevel = null;
            sceneRoot.setLayoutX(0);
        } else {
            harambe = new Harambe(0, 0);
            currentLevel = new Level(levelNum, sceneHeight);
            background = new Background(bgImage, currentLevel.getWidth(), currentLevel.getHeight());

            sceneRoot.getChildren().add(background);
            sceneRoot.getChildren().add(currentLevel);
            sceneRoot.getChildren().add(harambe);
            sceneRoot.setLayoutX(0);
        }
    }
}

