import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class EndHarambe extends ImageView {
    private static final String IMAGE_FILENAME = "images/gorilla.png";
    private static final double SPEED = 300;
    private static final double JUMP_HEIGHT = 700;
    private static final double FALL_ACCEL = 2000;
    private static final double GROWTH_RATE = 1.1;
    private static final double START_SIZE = 75;

    private double imageSize;

    private boolean isMovingRight, isMovingLeft;
    private boolean canJump;
    private double yVelocity;

    EndHarambe(double x, double y) {
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
        setX(x);
        setY(y);
        imageSize = START_SIZE;
        setFitWidth(imageSize);
        setFitHeight(imageSize);
    }

    void moveRight() {
        isMovingRight = true;
        isMovingLeft = false;
    }

    void moveLeft() {
        isMovingLeft = true;
        isMovingRight = false;
    }

    void stall() {
        isMovingLeft = false;
        isMovingRight = false;
    }

    void jump() {
        if (canJump) {
            yVelocity -= JUMP_HEIGHT;
        }
        canJump = false;
    }

    void update(EndScreen endScreen, double elapsedTime) {
        updateMovementX(endScreen, elapsedTime);
        updateMovementY(endScreen, elapsedTime);
    }

    void updateMovementX(EndScreen endScreen, double elapsedTime) {
        if (isMovingRight) {
            setX(getX() + SPEED * elapsedTime);
        }
        if (isMovingLeft) {
            setX(getX() - SPEED * elapsedTime);
        }
        if (getX() < 0) {
            setX(0);
        }
        if (getX() + imageSize > endScreen.getWidth()) {
            setX(endScreen.getWidth()-imageSize);
        }
    }

    void updateMovementY(EndScreen endScreen, double elapsedTime) {
        yVelocity += FALL_ACCEL * elapsedTime;
        setY(getY() + yVelocity * elapsedTime);

        if (getY() + imageSize >= endScreen.getHeight()) {
            setY(endScreen.getHeight() - imageSize);
            canJump = true;
        }
    }

    void grow() {
        imageSize *= GROWTH_RATE;
        setFitWidth(imageSize);
        setFitHeight(imageSize);
    }
}
