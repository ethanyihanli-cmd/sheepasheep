package com.macondo.sheep.model;

public enum CardType {
    CABBAGE("🥬", "Cabbage"),
    CORN("🌽", "Corn"),
    MILK("🥛", "Milk"),
    CARROT("🥕", "Carrot"),
    BRUSH("🖌️", "Brush"),
    BELL("🔔", "Bell"),
    GLOVE("🧤", "Glove"),
    BUCKET("🪣", "Bucket"),
    YARN("🧶", "Yarn"),
    SCISSORS("✂️", "Scissors"),
    SPADE("🔧", "Spade"),
    GRASS("🌿", "Grass"),
    FIRE("🔥", "Fire"),
    HAY("🌾", "Hay"),
    STUMP("🪵", "Stump"),
    WOOL("🐑", "Wool");

    private final String symbol;
    private final String englishName;

    CardType(String symbol, String englishName) {
        this.symbol = symbol;
        this.englishName = englishName;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getEnglishName() {
        return englishName;
    }
}
