import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class Game {
    private static final int NUM_LEVELS = 3;
    private static final KeyCode CHEAT_KEY = KeyCode.CONTROL;

    private Scene scene;
    private Group sceneRoot;
    private int sceneWidth, sceneHeight;

    private Harambe harambe;
    private Image bgImage, startImage, endImage;
    private Background background;
    private StartScreen startScreen;
    private EndScreen endScreen;
    private Level currentLevel;
    boolean finishedGame;

    private HashSet<KeyCode> pressedKeys;

    Game(int width, int height) {
        sceneWidth = width;
        sceneHeight = height;
        sceneRoot = new Group();
        scene = new Scene(sceneRoot, sceneWidth, sceneHeight);

        bgImage = new Image(getClass().getClassLoader().getResourceAsStream("images/city.png"));
        startImage = new Image(getClass().getClassLoader().getResourceAsStream("images/harambe-start.png"));
        endImage = new Image(getClass().getClassLoader().getResourceAsStream("images/jungle.png"));

        startScreen = new StartScreen(sceneWidth, sceneHeight, startImage);
        endScreen = new EndScreen(sceneWidth, sceneHeight, endImage);
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
                endScreen.update(elapsedTime);
            }
            return;
        }
        resolveKeyPresses();
        updateGameCharacters(elapsedTime);
        scrollLevel();
        if (currentLevel != null && harambe.getX() + harambe.getFitWidth() >= currentLevel.getWidth()) {
            advanceLevels();
        }
        removeDeadCharacters();
    }

    void updateGameCharacters(double elapsedTime) {
        if (currentLevel == null) {
            return;
        }
        harambe.update(currentLevel, elapsedTime);
        for (Police police : currentLevel.getPoliceList()) {
            if (Math.abs(police.getX() - harambe.getX()) < sceneWidth / 2) {
                if (police.getX() < harambe.getX()) {
                    police.shootRight(currentLevel);
                } else if (police.getX() > harambe.getX()) {
                    police.shootLeft(currentLevel);
                }
            }
            police.update(currentLevel, elapsedTime);
        }
        for (Banana banana : currentLevel.getBananaList()) {
            banana.update(currentLevel, elapsedTime);
        }
        for (Bullet bullet : currentLevel.getBulletList()) {
            bullet.update(currentLevel, elapsedTime);
        }
    }

    void resolveStartOptions() {
        if (pressedKeys.contains(KeyCode.SPACE)) {
            advanceLevels();
        }
        if (pressedKeys.contains(KeyCode.H)) {
            startScreen.toggleHelp();
        }
    }

    void resolveEndOptions() {
        if (pressedKeys.contains(KeyCode.SPACE)) {
            System.exit(0);
        }

        if (pressedKeys.contains(KeyCode.D) && !pressedKeys.contains(KeyCode.A)) {
            endScreen.getHarambe().moveRight();
        } else if (pressedKeys.contains(KeyCode.A) && !pressedKeys.contains(KeyCode.D)) {
            endScreen.getHarambe().moveLeft();
        } else {
            endScreen.getHarambe().stall();
        }

        if (pressedKeys.contains(KeyCode.W)) {
            endScreen.getHarambe().jump();
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
            harambe.throwBanana(currentLevel);
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
        if (pressedKeys.contains(KeyCode.K)) {
            currentLevel.getChildren().removeAll(currentLevel.getPoliceList());
            currentLevel.getPoliceList().clear();
        }
        if (pressedKeys.contains(KeyCode.S)) {
            Bullet.slowBulletSpeed();
        }
        if (pressedKeys.contains(KeyCode.I)) {
            harambe.setInvincible();
        }
        if (pressedKeys.contains(KeyCode.B)) {
            harambe.fastBananaReload();
        }
        if (pressedKeys.contains(KeyCode.F)) {
            harambe.superSpeed();
        }
        if (pressedKeys.contains(KeyCode.J)) {
            harambe.superJump();
        }
        if (pressedKeys.contains(KeyCode.C)) {
            Harambe newHarambe = new Harambe(harambe.getX(), harambe.getY());
            sceneRoot.getChildren().remove(harambe);
            harambe = newHarambe;
            sceneRoot.getChildren().add(harambe);
            Bullet.normalBulletSpeed();
        }
        for (KeyCode kc : pressedKeys) {
            if (kc.isDigitKey()) {
                int level = Integer.parseInt(kc.getName());
                skipToLevel(level);
                break;
            }
        }
    }

    void removeDeadCharacters() {
        if (!harambe.isAlive()) {
            skipToLevel(0);
            return;
        }

        if (currentLevel == null) {
            return;
        }
        List<Police> deadPoliceList = new ArrayList<Police>();
        for (Police police : currentLevel.getPoliceList()) {
            if (!police.isAlive()) {
                deadPoliceList.add(police);
            }
        }
        currentLevel.getPoliceList().removeAll(deadPoliceList);
        currentLevel.getChildren().removeAll(deadPoliceList);
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
        sceneRoot.setLayoutX(0);

        int levelNum = 0;
        if (currentLevel != null) {
            levelNum = currentLevel.getLevelNumber();
        }
        if (levelNum == NUM_LEVELS) {
            currentLevel = null;
            finishedGame = true;
            sceneRoot.getChildren().add(endScreen);
            return;
        }

        harambe = new Harambe(0, 0);
        currentLevel = new Level(levelNum + 1, sceneHeight);
        background = new Background(bgImage, currentLevel.getWidth(), currentLevel.getHeight());

        sceneRoot.getChildren().add(background);
        sceneRoot.getChildren().add(currentLevel);
        sceneRoot.getChildren().add(harambe);
    }

    void skipToLevel(int levelNum) {
        sceneRoot.getChildren().clear();
        sceneRoot.setLayoutX(0);
        currentLevel = null;

        if (levelNum <= 0) {
            sceneRoot.getChildren().add(startScreen);
            finishedGame = false;
        } else if (levelNum > NUM_LEVELS) {
            sceneRoot.getChildren().add(endScreen);
            finishedGame = true;
        } else {
            harambe = new Harambe(0, 0);
            currentLevel = new Level(levelNum, sceneHeight);
            background = new Background(bgImage, currentLevel.getWidth(), currentLevel.getHeight());

            sceneRoot.getChildren().add(background);
            sceneRoot.getChildren().add(currentLevel);
            sceneRoot.getChildren().add(harambe);
        }
    }
}

