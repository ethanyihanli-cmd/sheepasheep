package com.macondo.sheep.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import java.util.Objects;

public class Card {
    private final CardType type;
    private final int layer;
    private final int row;
    private final int col;
    private final double x;
    private final double y;
    private final BooleanProperty isCovered = new SimpleBooleanProperty(true);
    private final BooleanProperty isSelectable = new SimpleBooleanProperty(false);

    public Card(CardType type, int layer, int row, int col, double x, double y) {
        this.type = type;
        this.layer = layer;
        this.row = row;
        this.col = col;
        this.x = x;
        this.y = y;
    }

    public CardType getType() { return type; }
    public int getLayer() { return layer; }
    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isCovered() { return isCovered.get(); }
    public void setCovered(boolean covered) { this.isCovered.set(covered); }
    public boolean isSelectable() { return isSelectable.get(); }
    public BooleanProperty selectableProperty() { return isSelectable; }
    public void setSelectable(boolean selectable) { this.isSelectable.set(selectable); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return layer == card.layer && row == card.row && col == card.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(layer, row, col);
    }
}
