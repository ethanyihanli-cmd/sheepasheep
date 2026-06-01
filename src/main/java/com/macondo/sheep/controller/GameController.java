package com.macondo.sheep.controller;

import com.macondo.sheep.logic.InventoryManager;
import com.macondo.sheep.logic.LevelGenerator;
import com.macondo.sheep.model.Card;
import com.macondo.sheep.model.CardType;
import com.macondo.sheep.model.GameState;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.List;

public class GameController {
    private final GameState gameState;
    private final InventoryManager inventoryManager;

    public GameController() {
        this.gameState = new GameState();
        this.inventoryManager = new InventoryManager(gameState);
    }

    public void onCardClicked(Card card) {
        if (gameState.isGameWon() || gameState.isGameLost()) {
            return;
        }

        if (!card.isSelectable()) {
            return;
        }

        boolean removed = gameState.getBoardCards().remove(card);
        if (!removed) {
            return;
        }

        inventoryManager.addToInventory(card);
        updateSelectableStatus();

        if (gameState.getBoardCards().isEmpty()) {
            gameState.setGameWon(true);
            onLevelComplete();
        }

        if (inventoryManager.checkGameLost()) {
            gameState.setGameLost(true);
        }
    }

    private void updateSelectableStatus() {
        for (Card card : gameState.getBoardCards()) {
            card.setSelectable(false);
        }

        for (Card card : gameState.getBoardCards()) {
            if (!card.isCovered()) {
                card.setSelectable(true);
            }
        }
    }

    private void onLevelComplete() {
        if (gameState.getCurrentLevel() == 1) {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> loadLevel(2));
            pause.play();
        }
    }

    public void loadLevel(int level) {
        gameState.reset();
        gameState.setCurrentLevel(level);

        List<CardType> cardTypes = (level == 1) ?
                LevelGenerator.getLevel1CardTypes() :
                LevelGenerator.getLevel2CardTypes();

        List<Card> cards = LevelGenerator.generateLevel(level, cardTypes);
        gameState.getBoardCards().addAll(cards);

        updateSelectableStatus();
    }

    public GameState getGameState() {
        return gameState;
    }
}

