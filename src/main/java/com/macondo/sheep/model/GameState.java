package com.macondo.sheep.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameState {
    public static final int INVENTORY_CAPACITY = 7;

    private final ObservableList<Card> boardCards = FXCollections.observableArrayList();
    private final ObservableList<Card> inventory = FXCollections.observableArrayList();
    private final IntegerProperty currentLevel = new SimpleIntegerProperty(1);
    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final BooleanProperty gameWon = new SimpleBooleanProperty(false);
    private final BooleanProperty gameLost = new SimpleBooleanProperty(false);

    public ObservableList<Card> getBoardCards() {
        return boardCards;
    }

    public ObservableList<Card> getInventory() {
        return inventory;
    }

    public int getCurrentLevel() {
        return currentLevel.get();
    }

    public IntegerProperty currentLevelProperty() {
        return currentLevel;
    }

    public void setCurrentLevel(int level) {
        this.currentLevel.set(level);
    }

    public int getScore() {
        return score.get();
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public void addScore(int points) {
        this.score.set(this.score.get() + points);
    }

    public boolean isGameWon() {
        return gameWon.get();
    }

    public BooleanProperty gameWonProperty() {
        return gameWon;
    }

    public void setGameWon(boolean won) {
        this.gameWon.set(won);
    }

    public boolean isGameLost() {
        return gameLost.get();
    }

    public BooleanProperty gameLostProperty() {
        return gameLost;
    }

    public void setGameLost(boolean lost) {
        this.gameLost.set(lost);
    }

    public boolean isInventoryFull() {
        return inventory.size() >= INVENTORY_CAPACITY;
    }

    public void reset() {
        boardCards.clear();
        inventory.clear();
        score.set(0);
        gameWon.set(false);
        gameLost.set(false);
    }
}




