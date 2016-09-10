import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Platform extends Rectangle {
    private static final double SIZE = 50;

    Platform(double x, double y) {
        super(x, y, SIZE, SIZE);
        setFill(Color.GREEN);
    }
}
