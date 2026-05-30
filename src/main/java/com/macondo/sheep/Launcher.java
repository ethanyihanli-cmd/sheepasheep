package com.macondo.sheep;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label title = new Label("Sheep A Sheep");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

        StackPane root = new StackPane(title);
        Scene scene = new Scene(root, 900, 700);

        primaryStage.setTitle("🐏 Sheep A Sheep");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
