import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;


class Level {
    private Scene myScene;
    private int[][] levelData = {{0,0,0,0,0,0,0,0},
                                 {0,0,0,0,0,0,0,0},
                                 {0,0,0,0,0,0,0,0},
                                 {0,0,0,0,0,0,0,0},
                                 {0,0,0,0,0,0,0,0},
                                 {0,0,0,0,1,1,0,0},
                                 {0,0,0,1,1,1,1,1},
                                 {1,1,1,1,1,1,1,1}};

    Level(int width, int height) {
        Group root = new Group();
        myScene = new Scene(root, width, height, Color.DEEPSKYBLUE);
        for (int y = 0; y < levelData.length; y++) {
            for (int x = 0; x < levelData[0].length; x++) {
                if (levelData[y][x] == 1) {
                    root.getChildren().add(new Platform(x*50, y*50));
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
