import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Banana class created for end game. Only movement is to fall downwards.
 * @author Will Long
 */
class EndBanana extends ImageView {
    private static final String IMAGE_FILENAME = "images/banana.png";
    private static final double FALL_ACCEL = 200;
    private static final double IMAGE_SIZE = 25;

    private double yVelocity;

    /**
     * Creates banana at specified location.
     * @param x is x-location.
     * @param y is y-location.
     */
    EndBanana(double x, double y) {
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
        setX(x);
        setY(y);
        setFitWidth(IMAGE_SIZE);
        setFitHeight(IMAGE_SIZE);
    }

    /**
     * Updates bananas fall-speed based on gravity and moves banana down based
     * on its velocity.
     * @param endScreen is screen to update banana on.
     * @param elapsedTime is time spent updating.
     */
    void update(EndScreen endScreen, double elapsedTime) {
        yVelocity += FALL_ACCEL * elapsedTime;
        setY(getY() + yVelocity * elapsedTime);
    }
}