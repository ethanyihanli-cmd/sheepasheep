package com.macondo.sheep.logic;

import com.macondo.sheep.model.Card;
import com.macondo.sheep.model.CardType;
import com.macondo.sheep.model.GameState;
import javafx.collections.ObservableList;
import java.util.*;

public class InventoryManager {
    private final GameState gameState;

    public InventoryManager(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean addToInventory(Card card) {
        ObservableList<Card> inventory = gameState.getInventory();

        if (inventory.size() >= GameState.INVENTORY_CAPACITY) {
            return false;
        }

        inventory.add(card);

        Map<CardType, List<Card>> typeGroups = groupByType(inventory);

        for (Map.Entry<CardType, List<Card>> entry : typeGroups.entrySet()) {
            List<Card> cards = entry.getValue();
            if (cards.size() >= 3) {
                List<Card> toRemove = cards.subList(0, 3);
                inventory.removeAll(toRemove);
                gameState.addScore(10);
                return true;
            }
        }
        return true;
    }

    private Map<CardType, List<Card>> groupByType(List<Card> cards) {
        Map<CardType, List<Card>> groups = new HashMap<>();
        for (Card card : cards) {
            groups.computeIfAbsent(card.getType(), k -> new ArrayList<>()).add(card);
        }
        return groups;
    }

    public boolean checkGameLost() {
        ObservableList<Card> inventory = gameState.getInventory();
        if (inventory.size() < GameState.INVENTORY_CAPACITY) {
            return false;
        }
        Map<CardType, List<Card>> groups = groupByType(inventory);
        for (List<Card> cards : groups.values()) {
            if (cards.size() >= 3) {
                return false;
            }
        }
        return true;
    }
}
