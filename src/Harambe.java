import javafx.scene.image.Image;

import java.util.Date;

class Harambe extends GameCharacter {
    private static final String IMAGE_FILENAME = "images/gorilla.png";
    private static final double IMAGE_SIZE = 75;

    private double bananaCooldownSeconds;
    private long previousBananaThrowTime;
    private boolean isInvincible;

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
