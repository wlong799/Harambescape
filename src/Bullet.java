import javafx.scene.image.Image;

public class Bullet extends GameCharacter {
    private static final String IMAGE_FILENAME = "images/bullet.png";
    private static final double IMAGE_SIZE = 15;
    private static final double SPEED_MULTIPLIER = 3;

    Bullet(double x, double y) {
        super(x, y, IMAGE_SIZE);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
        xSpeed *= SPEED_MULTIPLIER;
    }

    void updateMovementY(Level level, double elapsedTime) {
        return;
    }
}
