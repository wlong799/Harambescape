import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;

class LevelManager {
    private Group sceneRoot;
    private Scene myScene;
    private int myWidth, myHeight;
    private int currentLevel;
    private int levelWidth, levelHeight;
    private HashSet<KeyCode> pressedKeys;

    LevelManager(int width, int height) {
        myWidth = width;
        myHeight = height;

        sceneRoot = new Group();
        myScene = new Scene(sceneRoot, myWidth, myHeight);

        currentLevel = 1;
        loadLevel();

        Image bgImage = new Image(getClass().getClassLoader().getResourceAsStream("images/city.png"));
        sceneRoot.getChildren().add(0, new Background(bgImage, myWidth, myHeight));


        pressedKeys = new HashSet<KeyCode>();
        myScene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        myScene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
    }

    private void loadLevel() {
        String filename = "levels/level" + currentLevel + ".txt";
        InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
        Scanner input = new Scanner(stream);

        levelWidth = input.nextInt();
        levelHeight = input.nextInt();
        int blockSize = myHeight/levelHeight;
        for (int y = 0; y < levelHeight; y++) {
            for (int x = 0; x < levelWidth; x++) {
                if (input.nextInt() == 1) {
                    sceneRoot.getChildren().add(new Platform(x*blockSize, y*blockSize, blockSize));
                }
            }
        }
    }

    Scene getScene() {
        return myScene;
    }

    public void update (double elapsedTime) {
        if (pressedKeys.contains(KeyCode.D)) {
            sceneRoot.setLayoutX(sceneRoot.getLayoutX() - 10);
        }
        if (pressedKeys.contains(KeyCode.A)) {
            sceneRoot.setLayoutX(sceneRoot.getLayoutX() + 10);
        }
    }
}

