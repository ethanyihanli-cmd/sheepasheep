package com.macondo.sheep.view;

import com.macondo.sheep.controller.GameController;
import com.macondo.sheep.model.Card;
import com.macondo.sheep.model.GameState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameView {
    private final GameController controller;
    private final BorderPane root;
    private final GridPane boardPane;
    private final HBox inventoryPane;
    private final Label scoreLabel;
    private final Label levelLabel;

    public GameView(GameController controller) {
        this.controller = controller;
        this.root = new BorderPane();
        this.boardPane = new GridPane();
        this.inventoryPane = new HBox(10);
        this.scoreLabel = new Label("Score: 0");
        this.levelLabel = new Label("Level 1");

        setupUI();
        bindListeners();
    }

    private void setupUI() {
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #87CEEB, #E0F6FF);");

        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(10));
        topBar.getChildren().addAll(levelLabel, scoreLabel);

        boardPane.setAlignment(Pos.CENTER);
        boardPane.setHgap(8);
        boardPane.setVgap(8);
        boardPane.setPadding(new Insets(20));

        inventoryPane.setAlignment(Pos.CENTER);
        inventoryPane.setPadding(new Insets(10));
        inventoryPane.setStyle("-fx-background-color: rgba(255,255,255,0.7); " +
                "-fx-background-radius: 15;");

        HBox buttonBar = new HBox(15);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPadding(new Insets(10));

        Button restartBtn = new Button("Restart");
        restartBtn.setOnAction(e -> restartGame());

        buttonBar.getChildren().addAll(restartBtn);

        VBox centerArea = new VBox(20);
        centerArea.setAlignment(Pos.CENTER);
        centerArea.getChildren().addAll(boardPane, inventoryPane, buttonBar);

        root.setTop(topBar);
        root.setCenter(centerArea);
    }

    private void bindListeners() {
        controller.getGameState().getBoardCards().addListener(
                (javafx.collections.ListChangeListener.Change<? extends Card> c) -> updateBoard()
        );

        controller.getGameState().getInventory().addListener(
                (javafx.collections.ListChangeListener.Change<? extends Card> c) -> updateInventory()
        );

        controller.getGameState().scoreProperty().addListener(
                (obs, old, newVal) -> scoreLabel.setText("Score: " + newVal)
        );

        controller.getGameState().currentLevelProperty().addListener(
                (obs, old, newVal) -> levelLabel.setText("Level " + newVal)
        );

        controller.getGameState().gameWonProperty().addListener(
                (obs, old, newVal) -> { if (newVal) showMessage("You won!"); }
        );

        controller.getGameState().gameLostProperty().addListener(
                (obs, old, newVal) -> { if (newVal) showMessage("Game over!"); }
        );
    }

    private void updateBoard() {
        boardPane.getChildren().clear();

        int maxLayer = controller.getGameState().getBoardCards().stream()
                .mapToInt(Card::getLayer)
                .max().orElse(1);

        for (Card card : controller.getGameState().getBoardCards()) {
            CardNode cardNode = new CardNode(card);

            GridPane.setRowIndex(cardNode, card.getRow());
            GridPane.setColumnIndex(cardNode, card.getCol());

            cardNode.setOnMouseClicked(e -> {
                if (card.isSelectable()) {
                    controller.onCardClicked(card);
                }
            });

            boardPane.add(cardNode, card.getCol(), card.getRow());
        }
    }

    private void updateInventory() {
        inventoryPane.getChildren().clear();

        for (int i = 0; i < GameState.INVENTORY_CAPACITY; i++) {
            StackPane slot = new StackPane();
            slot.setPrefSize(70, 90);
            slot.setStyle("-fx-background-color: rgba(255,255,255,0.5); " +
                    "-fx-border-color: #D4A574; -fx-border-width: 2; " +
                    "-fx-border-radius: 10; -fx-background-radius: 10;");

            if (i < controller.getGameState().getInventory().size()) {
                Card card = controller.getGameState().getInventory().get(i);
                Label cardLabel = new Label(card.getType().getSymbol());
                cardLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
                slot.getChildren().add(cardLabel);
            }

            inventoryPane.getChildren().add(slot);
        }
    }

    private void showMessage(String message) {
        Label msgLabel = new Label(message);
        msgLabel.setFont(Font.font(24));
        msgLabel.setTextFill(Color.RED);
        msgLabel.setStyle("-fx-background-color: white; -fx-padding: 10;");
        msgLabel.setAlignment(Pos.CENTER);

        VBox overlay = new VBox(msgLabel);
        overlay.setAlignment(Pos.CENTER);
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.5);");

        root.getChildren().add(overlay);

        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(2));
        pause.setOnFinished(e -> root.getChildren().remove(overlay));
        pause.play();
    }

    private void restartGame() {
        controller.loadLevel(controller.getGameState().getCurrentLevel());
    }

    public BorderPane getRoot() {
        return root;
    }
}


