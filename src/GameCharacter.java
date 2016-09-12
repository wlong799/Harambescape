import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class GameCharacter extends ImageView {
    protected static final double IMAGE_SIZE = 50;
    protected static final double SPEED = 5;
    protected static final double JUMP_HEIGHT = 20;
    protected static final double FALL_ACCEL = 0.2;
    protected static final double FALL_MAX = 2;

    protected boolean isMovingRight, isMovingLeft;
    protected boolean canJump;
    protected double yVelocity;
    protected double yAccel;

    GameCharacter(double x, double y, Image image) {
        super(image);
        setX(x);
        setY(y);
        setFitWidth(IMAGE_SIZE);
        setFitHeight(IMAGE_SIZE);
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

    void updateMovement(Level level) {
        if (isMovingRight) {
            setX(getX() + SPEED);
        }
        if (isMovingLeft) {
            setX(getX() - SPEED);
        }
        for (Node platform : level.getChildren()) {
            Bounds bounds = platform.getLayoutBounds();
            if (intersects(bounds)) {
                if (isMovingRight) {
                    setX(bounds.getMinX()-IMAGE_SIZE-0.01);
                }
                if (isMovingLeft) {
                    setX(bounds.getMaxX()+0.01);
                }
            }
        }
        if (getX() < 0) {
            setX(0);
        }

        if (yAccel < FALL_MAX) {
            yAccel += FALL_ACCEL;
        }
        yVelocity += yAccel;
        setY(getY() + yVelocity);
        for (Node platform : level.getChildren()) {
            Bounds bounds = platform.getLayoutBounds();
            if (intersects(bounds)) {
                setY(bounds.getMinY() - IMAGE_SIZE - 0.01);
                canJump = true;
                yVelocity = 0;
                yAccel = 0;
            }
        }
    }
}
