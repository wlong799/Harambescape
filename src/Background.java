import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Background extends Group {
    Background(Image image, double bgWidth, double bgHeight) {
        super();
        int adjustedImageWidth = (int)(image.getWidth() * (bgHeight / image.getHeight()));
        for (int currentX = 0; currentX < bgWidth; currentX += adjustedImageWidth) {
            ImageView imgView = new ImageView(image);
            imgView.setFitHeight(bgHeight);
            imgView.setPreserveRatio(true);
            imgView.setX(currentX);
            imgView.setY(0);
            getChildren().add(imgView);
        }

    }




}
