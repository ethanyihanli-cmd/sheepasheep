package com.macondo.sheep;

import com.macondo.sheep.controller.GameController;
import com.macondo.sheep.view.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameController controller = new GameController();
        controller.loadLevel(1);

        GameView gameView = new GameView(controller);
        Scene scene = new Scene(gameView.getRoot(), 900, 700);

        primaryStage.setTitle("🐏 Sheep A Sheep");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

