package com.macondo.sheep.controller;

import com.macondo.sheep.logic.InventoryManager;
import com.macondo.sheep.logic.LevelGenerator;
import com.macondo.sheep.model.Card;
import com.macondo.sheep.model.CardType;
import com.macondo.sheep.model.GameState;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.util.Duration;

import java.util.*;

public class GameController {
    private static final double CARD_WIDTH = 70;
    private static final double CARD_HEIGHT = 90;
    private final GameState gameState;
    private final InventoryManager inventoryManager;
    private final Stack<Card> moveHistory = new Stack<>();

    public GameController() {
        this.gameState = new GameState();
        this.inventoryManager = new InventoryManager(gameState);
    }

    public void onCardClicked(Card card) {
        if (gameState.isGameWon() || gameState.isGameLost()) return;
        if (!card.isSelectable()) return;

        boolean removed = gameState.getBoardCards().remove(card);
        if (!removed) return;

        moveHistory.push(card);
        boolean added = inventoryManager.addToInventory(card);
        if (!added) {
            moveHistory.pop();
            gameState.getBoardCards().add(card);
            updateSelectableStatus();
            gameState.setGameLost(true);
            return;
        }
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
        updateCoveredStatus();
        for (Card card : gameState.getBoardCards()) {
            card.setSelectable(false);
        }
        for (Card card : gameState.getBoardCards()) {
            if (!card.isCovered()) {
                card.setSelectable(true);
            }
        }
    }

    private void updateCoveredStatus() {
        for (Card card : gameState.getBoardCards()) {
            card.setCovered(false);
        }
        for (Card current : gameState.getBoardCards()) {
            for (Card other : gameState.getBoardCards()) {
                if (other.getLayer() > current.getLayer() && isOverlapping(current, other)) {
                    current.setCovered(true);
                    break;
                }
            }
        }
    }

    private boolean isOverlapping(Card a, Card b) {
        return a.getX() < b.getX() + CARD_WIDTH
                && a.getX() + CARD_WIDTH > b.getX()
                && a.getY() < b.getY() + CARD_HEIGHT
                && a.getY() + CARD_HEIGHT > b.getY();
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
        moveHistory.clear();

        List<CardType> cardTypes = (level == 1) ?
                LevelGenerator.getLevel1CardTypes() :
                LevelGenerator.getLevel2CardTypes();

        List<Card> cards = LevelGenerator.generateLevel(level, cardTypes);
        gameState.getBoardCards().addAll(cards);
        updateSelectableStatus();
    }

    public void undoLastMove() {
        if (moveHistory.isEmpty()) return;
        if (gameState.isGameWon() || gameState.isGameLost()) return;

        Card lastCard = moveHistory.pop();
        boolean removedFromInventory = gameState.getInventory().remove(lastCard);
        if (removedFromInventory) {
            gameState.getBoardCards().add(lastCard);
            updateSelectableStatus();
            gameState.addScore(-10);
        }
    }

    public void shuffleBoard() {
        if (gameState.isGameWon() || gameState.isGameLost()) return;
        List<Card> currentCards = new ArrayList<>(gameState.getBoardCards());
        Collections.shuffle(currentCards);
        gameState.getBoardCards().setAll(currentCards);
        updateSelectableStatus();
    }

    public void useRemoveTool() {
        if (gameState.isGameWon() || gameState.isGameLost()) return;
        ObservableList<Card> inventory = gameState.getInventory();
        if (inventory.size() >= 3) {
            List<Card> toRemove = new ArrayList<>(inventory.subList(0, 3));
            inventory.removeAll(toRemove);
        }
    }

    public GameState getGameState() {
        return gameState;
    }
}
