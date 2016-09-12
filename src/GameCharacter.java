import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

class GameCharacter extends ImageView {
    protected static final double DEFAULT_SPEED = 300;
    protected static final double JUMP_HEIGHT = 700;
    protected static final double FALL_ACCEL = 2000;

    protected double imageSize;
    protected boolean isMovingRight, isMovingLeft;
    protected boolean canJump;
    protected double xSpeed;
    protected double yVelocity;
    protected double yAccel;
    protected boolean isAlive;

    GameCharacter(double x, double y, double size) {
        setX(x);
        setY(y);
        imageSize = size;
        setFitWidth(imageSize);
        setFitHeight(imageSize);
        isAlive = true;
        xSpeed = DEFAULT_SPEED;
    }

    boolean isAlive() {
        return isAlive;
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

    void update(Level level, double elapsedTime) {
        updateMovementX(level, elapsedTime);
        updateMovementY(level, elapsedTime);
        updateAliveStatus(level);
    }

    void updateMovementX(Level level, double elapsedTime) {
        if (isMovingRight) {
            setX(getX() + xSpeed * elapsedTime);
        }
        if (isMovingLeft) {
            setX(getX() - xSpeed * elapsedTime);
        }
        for (Platform platform : level.getPlatformList()) {
            Bounds bounds = platform.getLayoutBounds();
            if (intersects(bounds)) {
                if (isMovingRight) {
                    setX(bounds.getMinX() - imageSize - 0.01);
                }
                if (isMovingLeft) {
                    setX(bounds.getMaxX() + 0.01);
                }
            }
        }
        if (getX() < 0) {
            setX(0);
        }
    }

    void updateMovementY(Level level, double elapsedTime) {
        yVelocity += FALL_ACCEL * elapsedTime;
        setY(getY() + yVelocity * elapsedTime);
        for (Platform platform : level.getPlatformList()) {
            Bounds bounds = platform.getLayoutBounds();
            if (intersects(bounds)) {
                setY(bounds.getMinY() - imageSize - 0.01);
                canJump = true;
                yVelocity = 0;
                yAccel = 0;
            }
        }
    }

    void updateAliveStatus(Level level) {
        return;
    }
}
