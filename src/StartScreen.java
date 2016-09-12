import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class StartScreen extends Group {
    StartScreen(int width, int height, Image image) {
        ImageView imgView = new ImageView(image);
        imgView.setFitHeight(height);
        imgView.setPreserveRatio(true);
        imgView.setViewport(new Rectangle2D(0, 0, width, height));
        getChildren().add(imgView);
    }
}