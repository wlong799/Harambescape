import javafx.scene.image.Image;

import java.util.Date;

/**
 * Main character of game, extension of GameCharacter. Harambe is a gorilla
 * who can jump, can throw bananas, and dies upon contact with a policeman or
 * his bullet.
 * @author Will Long
 */
class Harambe extends GameCharacter {
    private static final String IMAGE_FILENAME = "images/gorilla.png";
    private static final double IMAGE_SIZE = 75;

    private double bananaCooldownSeconds;
    private long previousBananaThrowTime;
    private boolean isInvincible;

    /**
     * Creates Harambe at specified location.
     * @param x is x-location.
     * @param y is y-location.
     */
    Harambe(double x, double y) {
        super(x, y, IMAGE_SIZE);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
        bananaCooldownSeconds = 2;
        previousBananaThrowTime = 0;
    }

    void fastBananaReload() {
        bananaCooldownSeconds = 0.25;
    }

    void setInvincible() {
        isInvincible = true;
    }

    void superSpeed() {
        xSpeed = DEFAULT_SPEED * 3;
    }

    void superJump() {
        jumpHeight = DEFAULT_JUMP * 2;
    }

    /**
     * Harambe dies if in contact with a bullet or a policeman.
     * @param level is Level to update information in.
     */
    @Override
    void updateAliveStatus(Level level) {
        if (isInvincible) {
            return;
        }
        for (Police police : level.getPoliceList()) {
            if (intersects(police.getLayoutBounds())) {
                isAlive = false;
            }
        }
        for (Bullet bullet : level.getBulletList()) {
            if (intersects(bullet.getLayoutBounds())) {
                isAlive = false;
            }
        }
    }

    /**
     * Lobs a banana in the same direction that Harambe is moving. Does not
     * throw the banana if banana was thrown more recently than the cooldown
     * time.
     * @param level is the Level to update information on.
     */
    void throwBanana(Level level) {
        long time = (new Date()).getTime();
        if (time - previousBananaThrowTime < bananaCooldownSeconds * 1000) {
            return;
        }
        previousBananaThrowTime = time;
        Banana banana = new Banana(getX(), getY());
        level.getBananaList().add(banana);
        level.getChildren().add(banana);
        if (isMovingLeft) {
            banana.moveLeft();
        } else {
            banana.moveRight();
        }
        banana.jump();
    }
}
