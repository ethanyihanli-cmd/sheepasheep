package com.macondo.sheep.logic;

import com.macondo.sheep.model.Card;
import com.macondo.sheep.model.CardType;

import java.util.*;

public class LevelGenerator {
    private static final Random random = new Random();
    private static final int MATCH_GROUP_SIZE = 3;
    private static final double CARD_WIDTH = 70;
    private static final double CARD_HEIGHT = 90;
    private static final double LEVEL_FIELD_WIDTH = 430;
    private static final double LEVEL_FIELD_HEIGHT = 350;
    private static final int LEVEL_1_COLUMNS = 3;
    private static final double LEVEL_1_X_STEP = 92;
    private static final double LEVEL_1_Y_STEP = 74;

    public static List<Card> generateLevel(int level, List<CardType> selectedTypes) {
        if (selectedTypes == null || selectedTypes.isEmpty()) {
            throw new IllegalArgumentException("At least one card type is required");
        }

        List<Card> cards = (level == 1)
                ? generateOrganizedIntroLevel(selectedTypes)
                : generateMessyCompactLevel(selectedTypes);
        updateCoveredStatus(cards);
        return cards;
    }

    private static List<Card> generateOrganizedIntroLevel(List<CardType> selectedTypes) {
        List<CardType> cardPool = createCardPool(selectedTypes, 18);
        List<Card> cards = new ArrayList<>();
        int cardIndex = 0;

        cardIndex = addLevel1Layer(cards, cardPool, cardIndex, 1, 3, 34, 8);
        cardIndex = addLevel1Layer(cards, cardPool, cardIndex, 2, 2, 74, 52);
        addLevel1Layer(cards, cardPool, cardIndex, 3, 1, 114, 96);
        return cards;
    }

    private static int addLevel1Layer(
            List<Card> cards,
            List<CardType> cardPool,
            int cardIndex,
            int layer,
            int rows,
            double startX,
            double startY
    ) {
        for (int row = 0; row < rows && cardIndex < cardPool.size(); row++) {
            for (int col = 0; col < LEVEL_1_COLUMNS && cardIndex < cardPool.size(); col++) {
                cards.add(new Card(
                        cardPool.get(cardIndex++),
                        layer,
                        row,
                        col,
                        startX + col * LEVEL_1_X_STEP,
                        startY + row * LEVEL_1_Y_STEP
                ));
            }
        }
        return cardIndex;
    }

    private static List<Card> generateMessyCompactLevel(List<CardType> selectedTypes) {
        int totalCards = 54;
        int layers = 6;
        List<CardType> cardPool = createCardPool(selectedTypes, totalCards);
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < cardPool.size(); i++) {
            int layer = 1 + (i % layers);
            double layerDrift = (layer - 1) * 8;
            double x = 18 + layerDrift + random.nextDouble(LEVEL_FIELD_WIDTH - CARD_WIDTH - 36);
            double y = 10 + layerDrift + random.nextDouble(LEVEL_FIELD_HEIGHT - CARD_HEIGHT - 28);
            cards.add(new Card(cardPool.get(i), layer, i, layer, x, y));
        }
        return cards;
    }

    private static List<CardType> createCardPool(List<CardType> selectedTypes, int totalCards) {
        List<CardType> cardPool = new ArrayList<>(totalCards);
        while (cardPool.size() < totalCards) {
            CardType type = selectedTypes.get(random.nextInt(selectedTypes.size()));
            for (int i = 0; i < MATCH_GROUP_SIZE; i++) {
                cardPool.add(type);
            }
        }
        Collections.shuffle(cardPool);
        return cardPool;
    }

    private static void updateCoveredStatus(List<Card> cards) {
        for (Card card : cards) {
            card.setCovered(false);
        }
        for (Card current : cards) {
            for (Card other : cards) {
                if (other.getLayer() > current.getLayer() && isOverlapping(current, other)) {
                    current.setCovered(true);
                    break;
                }
            }
        }
    }

    private static boolean isOverlapping(Card a, Card b) {
        return a.getX() < b.getX() + CARD_WIDTH
                && a.getX() + CARD_WIDTH > b.getX()
                && a.getY() < b.getY() + CARD_HEIGHT
                && a.getY() + CARD_HEIGHT > b.getY();
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
