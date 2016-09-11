import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Harambe extends Rectangle {
    private static final double SIZE = 40;
    private static final double SPEED = 5;
    private static final double JUMP_HEIGHT = 20;
    private static final double FALL_ACCEL = 0.2;
    private static final double FALL_MAX = 2;

    private boolean isMovingRight, isMovingLeft;
    private boolean canJump;
    private double yVelocity;
    private double yAccel;

    Harambe(double x, double y) {
        super(x, y, SIZE, SIZE);
        setFill(Color.BLACK);
        yVelocity = 0;
        yAccel = 0;
        canJump = true;
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

    void throwBanana() {
        if (getFill() == Color.BLACK) {
            setFill(Color.YELLOW);
        } else {
            setFill(Color.BLACK);
        }
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
                    setX(bounds.getMinX()-SIZE-0.01);
                }
                if (isMovingLeft) {
                    setX(bounds.getMaxX()+0.01);
                }
            }
        }

        if (yAccel < FALL_MAX) {
            yAccel += FALL_ACCEL;
        }
        yVelocity += yAccel;
        setY(getY() + yVelocity);
        for (Node platform : level.getChildren()) {
            Bounds bounds = platform.getLayoutBounds();
            if (intersects(bounds)) {
                setY(bounds.getMinY() - SIZE - 0.01);
                canJump = true;
                yVelocity = 0;
                yAccel = 0;
            }
        }
    }
}
