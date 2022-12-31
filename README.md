## Tiles Project Overview
This game Utilizes JavaFX 17 to create a version of the NY Times tiles 
puzzle game.

Link to reference: https://www.nytimes.com/puzzles/tiles

### *How to use Program*
Acceptable Game Sizes: 4, 16, 32, 64, etc.
(Square w/ even # of tiles, for aesthetic purposes. Game Default is 64 tiles)

Upon start of program, user must select two tiles that have at least 1 
matching characteristic. Tiles do not have to be adjacent. All matching 
characteristics within both tiles will be removed. The second tile the user 
selects will become their new first tile to match with. User should aim to
get the highest streak of continuous tiles. The game ends when the entire board 
is cleared.


### *Java Files*
1. TilesPackage.Main.java - Entry point. Handles GUI.
2. TilesPackage.CardStack.java - Contains a stack of tiles to put on grid
3. TilesPackage.Tile.java - Creates Circle arrays of a specific size w/ rand 
color.
4. TilesPackage.Score.java - Handles high score and current score.


### *Any known bugs*
* Didn't have time to place GUI text in border planes. Text will move around 
and possibly overlap.
* You can click on the same tile twice, and it'll count as a match.
* "Game Ended" Message not yet implemented, whoops.