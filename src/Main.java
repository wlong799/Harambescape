import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private LevelManager levelManager;

    @Override
    public void start (Stage stage) {
        levelManager = new LevelManager(WIDTH, HEIGHT);
        stage.setTitle("Harambescape");
        stage.setScene(levelManager.getScene());
        stage.show();

        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> levelManager.update(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
