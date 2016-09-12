import javafx.scene.image.Image;

import java.util.Date;

class Harambe extends GameCharacter {
    private static final String IMAGE_FILENAME = "images/gorilla.png";
    private static final double IMAGE_SIZE = 75;
    private static final int BANANA_COOLDOWN_SECONDS = 2;

    private long previousBananaThrowTime;


    Harambe(double x, double y) {
        super(x, y, IMAGE_SIZE);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
        previousBananaThrowTime = 0;
    }



    void throwBanana(Level level) {
        long time = (new Date()).getTime();
        if (time - previousBananaThrowTime < BANANA_COOLDOWN_SECONDS * 1000) {
            return;
        }
        previousBananaThrowTime = time;
        Banana banana = new Banana(getX(), getY());
        level.getBananaList().add(banana);
        level.getChildren().add(banana);
        if (isMovingLeft) {
            banana.moveLeft();
        } else {
            banana.moveRight();
        }
        banana.jump();
    }
}
