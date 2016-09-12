import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class EndBanana extends ImageView {
    private static final String IMAGE_FILENAME = "images/banana.png";
    private static final double FALL_ACCEL = 200;
    private static final double IMAGE_SIZE = 25;

    private double yVelocity;

    EndBanana(double x, double y) {
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
        setX(x);
        setY(y);
        setFitWidth(IMAGE_SIZE);
        setFitHeight(IMAGE_SIZE);
    }

    void update(EndScreen endScreen, double elapsedTime) {
        yVelocity += FALL_ACCEL * elapsedTime;
        setY(getY() + yVelocity * elapsedTime);
    }
}