import javafx.scene.image.Image;

class Harambe extends GameCharacter {

    Harambe(double x, double y, Image image) {
        super(x, y, image);
    }

    void throwBanana() {
        System.out.println("THROW");
        return;
    }
}
