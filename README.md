# Sheep A Sheep

Sheep A Sheep is a JavaFX tile-matching puzzle game inspired by a viral TikTok ad for a sheep-themed mobile game. I decided to recreate it because the original game kept showing up everywhere, and level 2 was so difficult that it basically gave me nightmares because I could not pass it.

So now I challenge other players too: try to beat level 2.

## Gameplay

The game is a tile-matching puzzle. The player is shown a stack of different cards with sheep-themed icons. The goal is to clear the board by selecting matching cards.

When you click a card, it moves into the collection bar at the bottom. If you collect three cards of the same type, those cards disappear. The objective is to remove all cards from the board before the collection bar becomes full.

The game becomes harder as the level increases because more cards appear and the board becomes more crowded. Some cards may be blocked by cards above them, so the player needs to think carefully before clicking.

## How To Play

1. Launch the game.
2. Click on available cards to move them into the collection bar.
3. Match three cards of the same type to remove them.
4. Keep matching cards until the whole board is cleared.
5. Avoid filling the collection bar, or the game is over.
6. Try to pass level 2 if you can.

## How To Recreate This Game

This project was built using Java and JavaFX.

To recreate or modify the game:

1. Install Java JDK 17 or newer.
2. Install an IDE such as IntelliJ IDEA.
3. Clone or download the project files.
4. Open the project as a Maven project.
5. Make sure Maven loads the JavaFX dependencies from `pom.xml`.
6. Run the main launcher class:
```text
com.macondo.sheep.Launcher

You can also build the project into a JAR file using Maven:
./mvnw package

On Windows:
mvnw.cmd package

The main parts of the project are:
model: contains the game data, such as cards and card types.
logic: contains the level generation and inventory/matching logic.
controller: controls the game flow.
view: contains the JavaFX visual components.
Launcher: starts the game.

Inspiration
The idea came from a TikTok ad that went viral before. The ad showed a sheep puzzle game that looked simple at first, but became extremely difficult very quickly. I especially remembered level 2 because I could not pass it, and it was frustrating enough that I wanted to recreate the game myself.
This version is my own recreation of that idea as a JavaFX project.

Challenge
I challenge anyone who plays this game to try level 2.
If you can pass it, you have officially done better than me.
