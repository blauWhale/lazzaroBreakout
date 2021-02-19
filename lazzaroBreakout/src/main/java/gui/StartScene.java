package gui;

import game.Images;
import game.Status;
import gui.common.BaseScene;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;

public class StartScene extends BaseScene {
    private static int gameDifficulty = 0;
    public StartScene(Navigator navigator) {
        super(navigator, Images.WELCOME);

        setOnMouseClicked(e -> navigator.goTo(SceneType.GAME_SCREEN));

        Button easyButton = new Button("Easy");
        easyButton.setOnMouseClicked(event -> {
            setGameDifficulty(1);
            setOnMouseClicked(e -> navigator.goTo(SceneType.GAME_SCREEN));
        });
        easyButton.setPadding(new Insets(10));
        easyButton.setPrefWidth(100);
        easyButton.setLayoutX(250);
        easyButton.setLayoutY(350);

        Button normalButton = new Button("Normal");
        normalButton.setOnMouseClicked(event -> {
            setGameDifficulty(2);
            setOnMouseClicked(e -> navigator.goTo(SceneType.GAME_SCREEN));
        });
        normalButton.setPadding(new Insets(10));
        normalButton.setPrefWidth(100);
        normalButton.setLayoutX(250);
        normalButton.setLayoutY(400);

        Button hardButton = new Button("Hard");
        hardButton.setOnMouseClicked(event -> {
            setGameDifficulty(3);
            setOnMouseClicked(e -> navigator.goTo(SceneType.GAME_SCREEN));
        });
        hardButton.setPadding(new Insets(10));
        hardButton.setPrefWidth(100);
        hardButton.setLayoutX(250);
        hardButton.setLayoutY(450);





        Group root = (Group) getRoot();
        root.getChildren().add(hardButton);
        root.getChildren().add(normalButton);
        root.getChildren().add(easyButton);


    }

    @Override
    public void start() {
    }

    public static int getGameDifficulty() {
        return gameDifficulty;
    }

    public static void setGameDifficulty(int gameDifficulty) {
        StartScene.gameDifficulty = gameDifficulty;
    }
}
