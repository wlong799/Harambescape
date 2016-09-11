import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.HashSet;

class Game {
    private Scene scene;
    private Group sceneRoot;
    private int sceneWidth, sceneHeight;

    private Level currentLevel;
    private Harambe harambe;

    private HashSet<KeyCode> pressedKeys;

    Game(int width, int height) {
        sceneWidth = width;
        sceneHeight = height;
        sceneRoot = new Group();
        scene = new Scene(sceneRoot, sceneWidth, sceneHeight);

        currentLevel = new Level(1, sceneHeight);
        Image bgImage = new Image(getClass().getClassLoader().getResourceAsStream("images/city.png"));
        sceneRoot.getChildren().add(new Background(bgImage, currentLevel.getLevelWidth(),
                                                            currentLevel.getLevelHeight()));
        sceneRoot.getChildren().add(currentLevel);

        harambe = new Harambe(0,0);
        sceneRoot.getChildren().add(harambe);

        pressedKeys = new HashSet<KeyCode>();
        scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
    }



    Scene getScene() {
        return scene;
    }

    public void update(double elapsedTime) {
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

        harambe.updateMovement(currentLevel);

    }
}

