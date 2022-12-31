package TilesPackage;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static TilesPackage.Main.getMouseEvent;

/**
 * Sets up the color and aesthetic of each tile.
 */
public class Tile {
    private static float RADIUS;
    private static final int STROKE_WIDTH = 2;


    /**
     * Initiates a Tiles Set of Size "n"
     * @param gameSize The size of the tiles array
     */
    public static Circle[] Tile(int gameSize, char tileSize){
        //New Array of Tiles of size "n"
        Circle[] tiles = new Circle[gameSize];

        if(tileSize == 'l'){
            RADIUS = 35.0f;
        }else if(tileSize == 'm'){
            RADIUS = 25.0f;
        }else{
            RADIUS = 15.0f;
        }

        // Sets up basic params of each tile (creates pairs to assure match)
        for(int i = 0; i < gameSize; i += 2){
            Color randColor = newRandColor();

            tiles[i] = new Circle();
            tiles[i].setRadius(RADIUS);
            tiles[i].setFill(randColor);
            tiles[i].setStroke(Color.BLACK);
            tiles[i].setStrokeWidth(STROKE_WIDTH);


            tiles[i+1] = new Circle();
            tiles[i+1].setRadius(RADIUS);
            tiles[i+1].setFill(randColor);
            tiles[i+1].setStroke(Color.BLACK);
            tiles[i+1].setStrokeWidth(STROKE_WIDTH);
        }

        // Randomize order of tiles & Return
        return randomizeOrder(tiles);
    }


    /**
     * Randomizes the order of the tiles (start of game)
     * @return Random order of tiles
     */
    private static Circle[] randomizeOrder(Circle[] tiles){
        List<Circle> listArray = Arrays.asList(tiles);
        Collections.shuffle(listArray);
        return listArray.toArray(tiles);
    }

    /**
     * Adds mouse input to each tile array
     * Returns Nothing.
     */
    public static void setTileIO(Circle[] tiles){
        for(int i = 0; i < tiles.length; i++){
            tiles[i].addEventHandler(MouseEvent.MOUSE_CLICKED, getMouseEvent());
        }
    }


    /**
     * Picks a random color to use for our tile
     * @return Random Color
     */
    private static Color newRandColor(){
        Color[] colorWheel = {Color.YELLOW, Color.BLUE, Color.DEEPPINK,
                Color.ORANGE, Color.CYAN, Color.GREEN};

        Random random = new Random();
        return colorWheel[random.nextInt(colorWheel.length)];
    }
}
