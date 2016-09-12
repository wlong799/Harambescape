import javafx.scene.Group;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level extends Group {
    private static final String LEVEL_FILE_PREFIX = "levels/level";
    private static final String LEVEL_FILE_POSTFIX = ".txt";
    private static final double PLATFORM_WIDTH_RATIO = 4;

    private static final int FREE_ID = 0;
    private static final int PLATFORM_ID = 1;
    private static final int POLICE_ID = 2;

    private int levelNumber;
    private double levelWidth, levelHeight;
    private List<Platform> platformList;
    private List<Police> policeList;
    private List<Banana> bananaList;
    private List<Bullet> bulletList;

    Level(int levelNum, double height) {
        levelNumber = levelNum;
        String filename = LEVEL_FILE_PREFIX + levelNum + LEVEL_FILE_POSTFIX;
        InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
        Scanner input = new Scanner(stream);

        platformList = new ArrayList<Platform>();
        policeList = new ArrayList<Police>();
        bananaList = new ArrayList<Banana>();
        bulletList = new ArrayList<Bullet>();

        int levelBlockWidth = input.nextInt();
        int levelBlockHeight = input.nextInt();
        levelHeight = height;
        levelWidth = (double) (levelBlockWidth) / levelBlockHeight *
                levelHeight * PLATFORM_WIDTH_RATIO;
        double blockHeight = levelHeight / levelBlockHeight;
        double blockWidth = blockHeight * PLATFORM_WIDTH_RATIO;

        for (int y = 0; y < levelBlockHeight; y++) {
            for (int x = 0; x < levelBlockWidth; x++) {
                int id = input.nextInt();
                if (id == FREE_ID) {
                    continue;
                } else if (id == PLATFORM_ID) {
                    Platform platform = new Platform(x * blockWidth, y * blockHeight,
                            blockWidth, blockHeight);
                    getChildren().add(platform);
                    platformList.add(platform);
                } else if (id == POLICE_ID) {
                    double xpos = (x * blockWidth) + blockWidth / 2.0;
                    double ypos = y * blockHeight;
                    Police police = new Police(xpos, ypos);
                    getChildren().add(police);
                    policeList.add(police);
                }
            }
        }
    }

    double getHeight() {
        return levelHeight;
    }

    double getWidth() {
        return levelWidth;
    }

    int getLevelNumber() {
        return levelNumber;
    }

    List<Platform> getPlatformList() {
        return platformList;
    }

    List<Police> getPoliceList() { return policeList; }

    List<Banana> getBananaList() { return bananaList; }

    List<Bullet> getBulletList() { return bulletList; }
}