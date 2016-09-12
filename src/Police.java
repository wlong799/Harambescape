import javafx.scene.image.Image;

import java.util.Date;

class Police extends GameCharacter {
    private static final String IMAGE_FILENAME = "images/police.png";
    private static final double IMAGE_SIZE = 75;
    private static final int DIR_RIGHT = 1;
    private static final int DIR_LEFT = -1;
    private static final double SHOOTING_COOLDOWN_SECONDS = 1.5;

    private long previousShotTimeMilli;

    Police(double x, double y) {
        super(x, y, IMAGE_SIZE);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
        previousShotTimeMilli = 0;
    }

    @Override
    void updateAliveStatus(Level level) {
        for (Banana banana : level.getBananaList()) {
            if (intersects(banana.getLayoutBounds())) {
                isAlive = false;
            }
        }
    }

    void shootLeft(Level level) {
        shoot(level, DIR_LEFT);
    }

    void shootRight(Level level) {
        shoot(level, DIR_RIGHT);
    }

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
