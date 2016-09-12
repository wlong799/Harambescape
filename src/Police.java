import javafx.scene.image.Image;

import java.util.Date;

class Police extends GameCharacter {
    private static final String IMAGE_FILENAME = "images/police.png";
    private static final double IMAGE_SIZE = 75;

    Police(double x, double y) {
        super(x, y, IMAGE_SIZE);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
    }

    @Override
    void updateAliveStatus(Level level) {
        for (Banana banana : level.getBananaList()) {
            if (intersects(banana.getLayoutBounds())) {
                isAlive = false;
            }
        }
    }
}
