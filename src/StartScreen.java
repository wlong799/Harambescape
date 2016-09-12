import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Date;

class StartScreen extends Group {
    private static final int BORDER_WIDTH = 325;
    private static final int TOP_PADDING = 175;
    private static final int BOTTOM_PADDING = 75;
    private static final double TOGGLE_DELAY_SECONDS = 0.25;

    private static final String OPTIONS = "Press SPACE to play.\n" +
                                          "Press H to toggle help.";

    private static final String DESCRIPTION = "It was just another normal day in the gorilla enclosure at the "+
                                              "Cincinatti Zoo, when suddenly, a human child appeared...\n\n"+
                                              "Playing as Harambe the gorilla, you must survive the perils of the zoo "+
                                              "and escape the police who are trying to kill you.\n\n"+
                                              "Survive, and enjoy all the bananas you could ever want.";

    private static final String HELP = "CONTROLS:\n"+
                                       "W,A,D to move\n"+
                                       "SPACE to throw bananas at police\n\n"+
                                       "Avoid the bullets, and advance through the levels to reach the final stage.\n\n"+
                                       "CHEATS: (CONTROL + ...)\n"+
                                       "D, A: Skip forwards or backwards\n" +
                                       "1,2,...: Jump to level\n"+
                                       "K: Kill all police in level\n"+
                                       "S: Slow bullets\n"+
                                       "I: Invincibility this level\n"+
                                       "B: Increased banana reloading this level\n"+
                                       "F, J: Super speed or jumping this level\n"+
                                       "C: Clear currently active cheats";

    ImageView background;
    Text optionText;
    Text helpText;
    boolean showingHelp;
    long lastTimeToggledMilli;

    StartScreen(int width, int height, Image image) {
        background = new ImageView(image);
        background.setFitHeight(height);
        background.setPreserveRatio(true);
        background.setViewport(new Rectangle2D(0, 0, width, height));

        optionText = new Text(0, height - BOTTOM_PADDING, OPTIONS);
        optionText.setWrappingWidth(BORDER_WIDTH);
        optionText.setTextAlignment(TextAlignment.CENTER);
        optionText.setFont(Font.font("Monospace", 24));

        helpText = new Text(0, TOP_PADDING, DESCRIPTION);
        helpText.setWrappingWidth(BORDER_WIDTH);
        helpText.setTextAlignment(TextAlignment.CENTER);
        helpText.setFont(Font.font("Monospace", 16));

        getChildren().add(background);
        getChildren().add(optionText);
        getChildren().add(helpText);
    }

    void toggleHelp() {
        long time = (new Date()).getTime();
        if (time - lastTimeToggledMilli < TOGGLE_DELAY_SECONDS*1000) {
            return;
        }
        lastTimeToggledMilli = time;
        if (showingHelp) {
            helpText.setText(DESCRIPTION);
            showingHelp = false;
        } else {
            helpText.setText(HELP);
            showingHelp = true;
        }
    }
}