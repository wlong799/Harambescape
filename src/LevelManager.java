import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.util.Scanner;

class LevelManager {
    private Scene myScene;
    private int myWidth, myHeight;
    private int currentLevel;
    private int levelWidth, levelHeight;

    LevelManager(int width, int height) {
        myWidth = width;
        myHeight = height;

        Group root = new Group();
        myScene = new Scene(root, myWidth, myHeight, Color.DEEPSKYBLUE);

        currentLevel = 1;
        try {
            loadLevel();
        } catch (Exception e) {
            return;
        }

    }

    private void loadLevel() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("level1.txt");
        Scanner input = new Scanner(stream);

        levelWidth = input.nextInt();
        levelHeight = input.nextInt();
        int blockSize = myHeight/levelHeight;
        Group root = (Group)(myScene.getRoot());
        for (int y = 0; y < levelHeight; y++) {
            for (int x = 0; x < levelWidth; x++) {
                if (input.nextInt() == 1) {
                    root.getChildren().add(new Platform(x*blockSize, y*blockSize, blockSize));
                }
            }
        }
    }

    Scene getScene() {
        return myScene;
    }

    public void step (double elapsedTime) {
        return;
    }
}
