import javafx.scene.image.Image;

import java.util.Date;

class Police extends GameCharacter {
    private static final String IMAGE_FILENAME = "images/police.png";
    private static final double IMAGE_SIZE = 75;

    Police(double x, double y) {
        super(x, y, IMAGE_SIZE, null);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
        setFitHeight(IMAGE_SIZE);
        setFitWidth(IMAGE_SIZE);
    }
}
