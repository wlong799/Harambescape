import javafx.scene.image.Image;

public class Banana extends GameCharacter {
    private static final String IMAGE_FILENAME = "images/banana.png";
    private static final double IMAGE_SIZE = 25;
    private static final double SPEED_MULTIPLIER = 2;
    private static final double X_RESISTANCE = 300;

    Banana(double x, double y) {
        super(x, y, IMAGE_SIZE);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
        xSpeed *= SPEED_MULTIPLIER;
        canJump = true;
    }

    @Override
    void updateMovementX(Level level, double elapsedTime) {
        super.updateMovementX(level, elapsedTime);
        xSpeed -= X_RESISTANCE * elapsedTime;
        if (xSpeed < 0) {
            xSpeed = 0;
        }
    }
}
