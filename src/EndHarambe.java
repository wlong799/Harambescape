import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Harambe character for the end game mode. Very similar to main game Harambe,
 * except it doesn't throw bananas, and it grows any time it touches a banana.
 * @author Will Long
 */
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

    /**
     * Creates new Harambe at specified position.
     * @param x is x-location.
     * @param y is y-location.
     */
    EndHarambe(double x, double y) {
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
        setX(x);
        setY(y);
        imageSize = START_SIZE;
        setFitWidth(imageSize);
        setFitHeight(imageSize);
    }

    /**
     * Set Harambe to move right.
     */
    void moveRight() {
        isMovingRight = true;
        isMovingLeft = false;
    }

    /**
     * Set Harambe to move left.
     */
    void moveLeft() {
        isMovingLeft = true;
        isMovingRight = false;
    }

    /**
     * Set Harambe to stop moving.
     */
    void stall() {
        isMovingLeft = false;
        isMovingRight = false;
    }

    /**
     * Make Harambe jump if possible.
     */
    void jump() {
        if (canJump) {
            yVelocity -= JUMP_HEIGHT;
        }
        canJump = false;
    }

    /**
     * Update Harambe's position on screen.
     * @param endScreen is screen to update position on.
     * @param elapsedTime is time spent moving.
     */
    void update(EndScreen endScreen, double elapsedTime) {
        updateMovementX(endScreen, elapsedTime);
        updateMovementY(endScreen, elapsedTime);
    }

    /**
     * Updates Harambe's x position. Move right or left depending on current
     * state. Ensure Harambe doesn't pass screen boundaries.
     * @param endScreen is screen to update position on.
     * @param elapsedTime is time spent moving.
     */
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

    /**
     * Update Harambe's y position. Update y velocity by accounting for gravity.
     * Ensure Harambe doesn't fall off bottom.
     * @param endScreen is screen to update position on.
     * @param elapsedTime
     */
    void updateMovementY(EndScreen endScreen, double elapsedTime) {
        yVelocity += FALL_ACCEL * elapsedTime;
        setY(getY() + yVelocity * elapsedTime);

        if (getY() + imageSize >= endScreen.getHeight()) {
            setY(endScreen.getHeight() - imageSize);
            canJump = true;
        }
    }

    /**
     * Scale Harambe's size by the growth rate.
     */
    void grow() {
        imageSize *= GROWTH_RATE;
        setFitWidth(imageSize);
        setFitHeight(imageSize);
    }
}
