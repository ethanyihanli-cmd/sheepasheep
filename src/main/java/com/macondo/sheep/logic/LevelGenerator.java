package com.macondo.sheep.logic;

import com.macondo.sheep.model.Card;
import com.macondo.sheep.model.CardType;
import java.util.*;

public class LevelGenerator {
    private static final Random random = new Random();

    public static List<Card> generateLevel(int level, List<CardType> selectedTypes) {
        int layers = (level == 1) ? 3 : 6 + random.nextInt(3);
        int cardsPerLayer = (level == 1) ? 12 : 20 + random.nextInt(10);

        List<CardType> cardPool = new ArrayList<>();
        int groupCount = (level == 1) ? 1 : 2 + random.nextInt(3);

        for (CardType type : selectedTypes) {
            for (int g = 0; g < groupCount; g++) {
                for (int i = 0; i < 3; i++) {
                    cardPool.add(type);
                }
            }
        }

        int totalNeeded = layers * cardsPerLayer;
        while (cardPool.size() < totalNeeded) {
            cardPool.add(selectedTypes.get(random.nextInt(selectedTypes.size())));
        }
        while (cardPool.size() > totalNeeded) {
            cardPool.remove(random.nextInt(cardPool.size()));
        }

        Collections.shuffle(cardPool);

        List<Card> cards = new ArrayList<>();
        int cardIndex = 0;

        for (int layer = layers; layer >= 1; layer--) {
            int rowCount = getRowCountForLayer(level, layer, layers);
            int colCount = getColCountForLayer(level, layer, layers);

            for (int row = 0; row < rowCount && cardIndex < cardPool.size(); row++) {
                for (int col = 0; col < colCount && cardIndex < cardPool.size(); col++) {
                    CardType type = cardPool.get(cardIndex++);
                    Card card = new Card(type, layer, row, col);
                    cards.add(card);
                }
            }
        }

        updateCoveredStatus(cards);
        return cards;
    }

    private static int getRowCountForLayer(int level, int layer, int totalLayers) {
        if (level == 1) return 3;
        boolean isBottleneck = (layer > totalLayers / 3 && layer < totalLayers * 2 / 3);
        return isBottleneck ? 4 + random.nextInt(2) : 6 + random.nextInt(3);
    }

    private static int getColCountForLayer(int level, int layer, int totalLayers) {
        if (level == 1) return 4;
        boolean isBottleneck = (layer > totalLayers / 3 && layer < totalLayers * 2 / 3);
        return isBottleneck ? 4 + random.nextInt(2) : 6 + random.nextInt(3);
    }

    private static void updateCoveredStatus(List<Card> cards) {
        for (Card card : cards) {
            card.setCovered(false);
        }

        for (int i = 0; i < cards.size(); i++) {
            Card current = cards.get(i);
            for (int j = 0; j < cards.size(); j++) {
                Card other = cards.get(j);
                if (other.getLayer() > current.getLayer() && isOverlapping(current, other)) {
                    current.setCovered(true);
                    break;
                }
            }
        }
    }

    private static boolean isOverlapping(Card a, Card b) {
        return Math.abs(a.getRow() - b.getRow()) <= 1 && Math.abs(a.getCol() - b.getCol()) <= 1;
    }

    public static List<CardType> getLevel1CardTypes() {
        return Arrays.asList(CardType.CABBAGE, CardType.CORN, CardType.MILK);
    }

    public static List<CardType> getLevel2CardTypes() {
        List<CardType> types = new ArrayList<>(Arrays.asList(CardType.values()));
        Collections.shuffle(types);
        return types.subList(0, Math.min(types.size(), 10 + random.nextInt(5)));
    }
}


