// This entire file is part of my masterpiece.
// Will Long
//
// I chose this file because I felt it was a good example of using inheritance.
// It sets some default methods that are necessary for all subclasses, and
// provides a template that subclasses can extend upon. Furthermore, it protects
// all instance variables so that only the classes that need them (i.e.
// subclasses) will be able to access them/modify them.

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

/**
 * Super-class for all characters existing in game. Can move right and left,
 * and can jump. Cannot move through platforms. Does not die to anything by
 * default, but this method will be overwritten by subclasses.
 * @author Will Long
 */
class GameCharacter extends ImageView {
    protected static final double DEFAULT_SPEED = 300;
    protected static final double DEFAULT_JUMP = 700;
    protected static final double FALL_ACCEL = 2000;

    protected double imageSize;
    protected boolean isMovingRight, isMovingLeft;
    protected boolean canJump;
    protected double jumpHeight;
    protected double xSpeed;
    protected double yVelocity;
    protected double yAccel;
    protected boolean isAlive;

    /**
     * Creates character of specified size and initializes default speed and
     * jump height.
     * @param x is x-position.
     * @param y is y-position.
     * @param size is size of character.
     */
    GameCharacter(double x, double y, double size) {
        setX(x);
        setY(y);

        imageSize = size;
        setFitWidth(imageSize);
        setFitHeight(imageSize);

        isAlive = true;
        xSpeed = DEFAULT_SPEED;
        jumpHeight = DEFAULT_JUMP;
    }

    boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets character to move right.
     */
    void moveRight() {
        isMovingRight = true;
        isMovingLeft = false;
    }

    /**
     * Sets character to move left.
     */
    void moveLeft() {
        isMovingLeft = true;
        isMovingRight = false;
    }

    /**
     * Sets character to stop moving.
     */
    void stall() {
        isMovingLeft = false;
        isMovingRight = false;
    }

    /**
     * Sets character to jump, if possible.
     */
    void jump() {
        if (canJump) {
            yVelocity -= jumpHeight;
        }
        canJump = false;
    }

    /**
     * Update character movement and check if it died.
     * @param level is Level to update information on.
     * @param elapsedTime is time spent updating.
     */
    void update(Level level, double elapsedTime) {
        updateMovementX(level, elapsedTime);
        updateMovementY(level, elapsedTime);
        updateAliveStatus(level);
    }

    /**
     * Updates character's X direction movement based on current movement
     * status. Prevents character from moving through platforms or offscreen.
     * @param level is Level to update information in.
     * @param elapsedTime is time spent updating.
     */
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

    /**
     * Updates character's Y direction movement. If mid-air, y velocity is
     * increased by the given fall acceleration variable. Prevents character
     * from falling through platform. Once a player touches a platform, its
     * y velocity is reset and its ability to jump is restored.
     * @param level is Level to update information in.
     * @param elapsedTime is time spent updating.
     */
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

    /**
     * Updates whether player is still alive. This is used by Game to determine
     * whether the character should be removed from screen. By default,
     * GameCharacter cannot die. This may have been better to implement as an
     * abstract method.
     * @param level is Level to update information in.
     */
    void updateAliveStatus(Level level) {
        return;
    }
}
