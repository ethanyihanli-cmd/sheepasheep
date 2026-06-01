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
    private final Card card;
    private final Rectangle background;
    private final Label symbolLabel;

    public CardNode(Card card) {
        this.card = card;
        this.background = new Rectangle(70, 90);
        background.setArcWidth(15);
        background.setArcHeight(15);
        background.setFill(Color.web("#F5E6D3"));
        background.setStroke(Color.web("#D4A574"));
        background.setStrokeWidth(2);

        this.symbolLabel = new Label(card.getType().getSymbol());
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
            background.setFill(Color.web("#C0C0C0"));
            background.setStroke(Color.web("#A0A0A0"));
            setOpacity(0.6);
        } else {
            background.setFill(Color.web("#E8D8B0"));
            background.setStroke(Color.web("#B8860B"));
            setOpacity(0.9);
        }
    }

    public Card getCard() {
        return card;
    }
}

