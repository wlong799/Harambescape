import javafx.scene.image.Image;

/**
 * Bullet class for the bullets shot by policemen. Not affected by gravity and
 * move faster than other characters. Speed can be affected by cheats.
 */
public class Bullet extends GameCharacter {
    private static final String IMAGE_FILENAME = "images/bullet.png";
    private static final double IMAGE_SIZE = 15;

    private static double speedMultiplier = 3;

    /**
     * Creates new bullet and increases its speed beyond normal GameCharacter.
     * @param x is x-location of Bullet.
     * @param y is y-location of Bullet.
     */
    Bullet(double x, double y) {
        super(x, y, IMAGE_SIZE);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
        xSpeed *= speedMultiplier;
    }

    static void slowBulletSpeed() {
        speedMultiplier = 0.5;
    }

    static void normalBulletSpeed() {
        speedMultiplier = 3;
    }

    void updateMovementY(Level level, double elapsedTime) {
        return;
    }
}
