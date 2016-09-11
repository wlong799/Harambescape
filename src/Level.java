import javafx.scene.Group;

import java.io.InputStream;
import java.util.Scanner;

public class Level extends Group {
    private static final String LEVEL_FILE_PREFIX = "levels/level";
    private static final String LEVEL_FILE_POSTFIX = ".txt";
    private static final double PLATFORM_WIDTH_RATIO = 4;

    private static final int FREE_ID = 0;
    private static final int PLATFORM_ID = 1;

    private double levelWidth, levelHeight;

    Level(int levelNumber, double height) {
        String filename = LEVEL_FILE_PREFIX + levelNumber + LEVEL_FILE_POSTFIX;
        InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
        Scanner input = new Scanner(stream);

        int levelBlockWidth = input.nextInt();
        int levelBlockHeight = input.nextInt();
        levelHeight = height;
        levelWidth = (double)(levelBlockWidth)/levelBlockHeight *
                     levelHeight * PLATFORM_WIDTH_RATIO;
        double blockHeight = levelHeight / levelBlockHeight;
        double blockWidth = blockHeight * PLATFORM_WIDTH_RATIO;

        for (int y = 0; y < levelBlockHeight; y++) {
            for (int x = 0; x < levelBlockWidth; x++) {
                if (input.nextInt() == PLATFORM_ID) {
                    Platform platform = new Platform(x * blockWidth, y * blockHeight,
                                                     blockWidth, blockHeight);
                    getChildren().add(platform);
                }
            }
        }
    }

    double getLevelHeight() {
        return levelHeight;
    }

    double getLevelWidth() {
        return levelWidth;
    }
}