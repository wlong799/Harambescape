import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;

class LevelManager {
    private Scene scene;
    private Group sceneRoot;
    private int sceneWidth, sceneHeight;

    private int currentLevel;
    private int levelWidth, levelHeight;

    private Harambe harambe;

    private HashSet<KeyCode> pressedKeys;

    LevelManager(int width, int height) {
        sceneWidth = width;
        sceneHeight = height;

        sceneRoot = new Group();
        scene = new Scene(sceneRoot, sceneWidth, sceneHeight);

        currentLevel = 1;
        loadLevel();

        Image bgImage = new Image(getClass().getClassLoader().getResourceAsStream("images/city.png"));
        sceneRoot.getChildren().add(0, new Background(bgImage, sceneWidth, sceneHeight));

        harambe = new Harambe(0,0);
        sceneRoot.getChildren().add(harambe);

        pressedKeys = new HashSet<KeyCode>();
        scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
    }

    private void loadLevel() {
        String filename = "levels/level" + currentLevel + ".txt";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
        Scanner input = new Scanner(stream);

        levelWidth = input.nextInt();
        levelHeight = input.nextInt();
        int blockSize = sceneHeight /levelHeight;
        for (int y = 0; y < levelHeight; y++) {
            for (int x = 0; x < levelWidth; x++) {
                if (input.nextInt() == 1) {
                    sceneRoot.getChildren().add(new Platform(x*blockSize, y*blockSize, blockSize));
                }
            }
        }
    }

    Scene getScene() {
        return scene;
    }

    public void update (double elapsedTime) {
        if (pressedKeys.contains(KeyCode.CONTROL)) {
            if (pressedKeys.contains(KeyCode.D)) {
                sceneRoot.setLayoutX(sceneRoot.getLayoutX() - 10);
            }
            if (pressedKeys.contains(KeyCode.A)) {
                sceneRoot.setLayoutX(sceneRoot.getLayoutX() + 10);
            }
        } else {
            if (pressedKeys.contains(KeyCode.D) && !pressedKeys.contains(KeyCode.A)) {
                harambe.moveRight();
            }
            if (pressedKeys.contains(KeyCode.A) && !pressedKeys.contains(KeyCode.D)) {
                harambe.moveLeft();
            }
            if (pressedKeys.contains(KeyCode.W)) {
                harambe.jump();
            }
            if (pressedKeys.contains(KeyCode.SPACE)) {
                harambe.throwBanana();
            }
        }

    }
}

