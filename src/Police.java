import javafx.scene.image.Image;

import java.util.Date;

/**
 * "Enemy" character within the game. Police stand still, and shoot at Harambe
 * when it comes to close. They die if they are hit by one of Harambe's bananas.
 *
 * @author Will Long
 */
class Police extends GameCharacter {
    private static final String IMAGE_FILENAME = "images/police.png";
    private static final double IMAGE_SIZE = 75;
    private static final int DIR_RIGHT = 1;
    private static final int DIR_LEFT = -1;
    private static final double SHOOTING_COOLDOWN_SECONDS = 1.5;

    private long previousShotTimeMilli;

    /**
     * Creates Police at specified location and initializes shot cool-down.
     * @param x is x-location.
     * @param y is y-location.
     */
    Police(double x, double y) {
        super(x, y, IMAGE_SIZE);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
        previousShotTimeMilli = 0;
    }

    /**
     * Police dies if it is touching a Banana.
     * @param level is Level to update game information in.
     */
    @Override
    void updateAliveStatus(Level level) {
        for (Banana banana : level.getBananaList()) {
            if (intersects(banana.getLayoutBounds())) {
                isAlive = false;
            }
        }
    }

    /**
     * Calls shoot, with the left direction specified.
     * @param level is the Level to update game information in.
     */
    void shootLeft(Level level) {
        shoot(level, DIR_LEFT);
    }

    /**
     * Calls shoot, with right direction specified.
     * @param level is the Level to update game information in.
     */
    void shootRight(Level level) {
        shoot(level, DIR_RIGHT);
    }

    /**
     * Shoot in the specified direction, if time since last shot is greater
     * than the cool-down time.
     * @param level is the Level to update game information within.
     * @param direction is the direction to shoot in.
     */
    private void shoot(Level level, int direction) {
        long time = (new Date()).getTime();
        if (time - previousShotTimeMilli < SHOOTING_COOLDOWN_SECONDS * 1000) {
            return;
        }
        previousShotTimeMilli = time;
        Bullet bullet = new Bullet(getX(), getY());
        level.getBulletList().add(bullet);
        level.getChildren().add(bullet);
        if (direction == DIR_LEFT) {
            bullet.moveLeft();
        } else if (direction == DIR_RIGHT){
            bullet.moveRight();
        }
    }
}
