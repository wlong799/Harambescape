import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Harambe extends Rectangle {
    private static final double SIZE = 40;
    private static final double SPEED = 5;
    private static final double JUMP_HEIGHT = 10;

    private int myVelocity;
    private int myAccel;

    Harambe(double x, double y) {
        super(x, y, SIZE, SIZE);
        setFill(Color.BLACK);
        myVelocity = 0;
        myAccel = 0;
    }

    void moveRight() {
        setX(getX() + SPEED);
    }

    void moveLeft() {
        setX(getX() - SPEED);
    }

    void jump() {
        setY(getY() - JUMP_HEIGHT);
    }

    void throwBanana() {
        setY(getY() + JUMP_HEIGHT);
    }
}
