import javafx.scene.image.Image;

/**
 * Banana class extends GameCharacter. Similar to main
 * game character, but includes an X-resistance to
 * ensure flung bananas don't fly on forever.
 * @author Will Long
 */
public class Banana extends GameCharacter {
    private static final String IMAGE_FILENAME = "images/banana.png";
    private static final double IMAGE_SIZE = 25;
    private static final double SPEED_MULTIPLIER = 2;
    private static final double X_RESISTANCE = 300;

    /**
     * Creates banana at specified position, and sets its ability to jump to
     * true to ensure that Harambe can immediately throw new bananas upwards
     * into the air.
     * @param x is x-location of banana.
     * @param y is y-location of banana.
     */
    Banana(double x, double y) {
        super(x, y, IMAGE_SIZE);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
        xSpeed *= SPEED_MULTIPLIER;
        canJump = true;
    }

    /**
     * Updates x velocity by subtracting away the resistance.
     * @param level is Level to update information in.
     * @param elapsedTime is time spent updating.
     */
    @Override
    void updateMovementX(Level level, double elapsedTime) {
        super.updateMovementX(level, elapsedTime);
        xSpeed -= X_RESISTANCE * elapsedTime;
        if (xSpeed < 0) {
            xSpeed = 0;
        }
    }
}
