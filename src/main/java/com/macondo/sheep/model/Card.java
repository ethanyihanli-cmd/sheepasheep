package com.macondo.sheep.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Card {
    private final CardType type;
    private final int layer;
    private final int row;
    private final int col;
    private final BooleanProperty isCovered = new SimpleBooleanProperty(true);
    private final BooleanProperty isSelectable = new SimpleBooleanProperty(false);

    public Card(CardType type, int layer, int row, int col) {
        this.type = type;
        this.layer = layer;
        this.row = row;
        this.col = col;
    }

    public CardType getType() {
        return type;
    }

    public int getLayer() {
        return layer;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isCovered() {
        return isCovered.get();
    }

    public BooleanProperty coveredProperty() {
        return isCovered;
    }

    public void setCovered(boolean covered) {
        this.isCovered.set(covered);
    }

    public boolean isSelectable() {
        return isSelectable.get();
    }

    public BooleanProperty selectableProperty() {
        return isSelectable;
    }

    public void setSelectable(boolean selectable) {
        this.isSelectable.set(selectable);
    }
}



