import javafx.scene.image.Image;

import java.util.Date;

class Harambe extends GameCharacter {
    private static final String IMAGE_FILENAME = "images/gorilla.png";
    private static final double IMAGE_SIZE = 75;
    private static final int BANANA_COOLDOWN_SECONDS = 2;

    private long previousBananaThrowTime;


    Harambe(double x, double y) {
        super(x, y, IMAGE_SIZE, null);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        setImage(image);
        setFitHeight(IMAGE_SIZE);
        setFitWidth(IMAGE_SIZE);
        previousBananaThrowTime = 0;
    }

    void throwBanana() {
        long time = (new Date()).getTime();
        if (time - previousBananaThrowTime < BANANA_COOLDOWN_SECONDS * 1000) {
            System.out.println("COOLDOWN");
        } else {
            previousBananaThrowTime = time;
            System.out.println("THROW");
        }
    }
}
