import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Platform extends Rectangle {
    Platform(double x, double y, double size) {
        super(x, y, size, size);
        setFill(Color.GREEN);
    }
}
