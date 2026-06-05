package com.macondo.sheep.view;

import com.macondo.sheep.model.Card;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CardNode extends StackPane {
    public static final double CARD_WIDTH = 70;
    public static final double CARD_HEIGHT = 90;

    private final Card card;
    private final Rectangle background;

    public CardNode(Card card) {
        this.card = card;
        this.background = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        background.setArcWidth(15);
        background.setArcHeight(15);
        background.setFill(Color.web("#F5E6D3"));
        background.setStroke(Color.web("#D4A574"));
        background.setStrokeWidth(2);

        Label symbolLabel = new Label(card.getType().getSymbol());
        symbolLabel.setFont(Font.font("System", FontWeight.BOLD, 32));
        symbolLabel.setTextFill(Color.web("#8B4513"));

        getChildren().addAll(background, symbolLabel);
        setAlignment(Pos.CENTER);
        updateStyle();

        card.selectableProperty().addListener((obs, old, newVal) -> updateStyle());
    }

    private void updateStyle() {
        if (card.isSelectable()) {
            background.setFill(Color.web("#FFF8DC"));
            background.setStroke(Color.web("#FFD700"));
            setOpacity(1.0);
        } else if (card.isCovered()) {
            background.setFill(Color.web("#D9D0C1"));
            background.setStroke(Color.web("#9E9587"));
            setOpacity(1.0);
        } else {
            background.setFill(Color.web("#E8D8B0"));
            background.setStroke(Color.web("#B8860B"));
            setOpacity(1.0);
        }
    }

}
