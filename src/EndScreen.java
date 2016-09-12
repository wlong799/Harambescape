import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

class EndScreen extends Group {
    private static final int BOTTOM_PADDING = 50;

    private static final String OPTIONS = "Catch the bananas!\n"+
                                          "Press SPACE when ready to exit.";

    ImageView background;
    Text optionText;

    EndScreen(int width, int height, Image image) {
        background = new ImageView(image);
        background.setFitHeight(height);
        background.setPreserveRatio(true);
        background.setViewport(new Rectangle2D(0, 0, width, height));

        optionText = new Text(0, height-BOTTOM_PADDING, OPTIONS);
        optionText.setWrappingWidth(width);
        optionText.setTextAlignment(TextAlignment.CENTER);
        optionText.setFont(Font.font("Monospace", 24));

        getChildren().add(background);
        getChildren().add(optionText);
    }
}

