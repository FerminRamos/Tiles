package TilesPackage;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import java.util.Objects;

/**
 * Handles the logic of removing // adding pieces (tiles) within stacks.
 */
public class CardStack {
    private static StackPane previouslyChosenStack = null;


    /**
     * Creates a stack array from pre-made tiles. (See examples below)
     * Stack[0] = smallTiles[0] + medTiles[0] + largeTiles[0]
     * Stack[1] = smallTiles[1] + medTiles[1] + largeTiles[1]
     * @return StackPane w/ tiles
     */
    public static StackPane[] createStacks(Circle[] smallTiles,
                                           Circle[] medTiles,
                                           Circle[] largeTiles,
                                           int gameSize){
        StackPane[] stackPane = new StackPane[gameSize];

        // Add a single small, medium, and large tile to a single StackPane
        String stringID;
        int ID = 0;
        for(int i = 0; i < gameSize; i++){
            // Add ID to each tile to identify which stack it's assigned to
            stringID = String.valueOf(ID);
            smallTiles[i].setId(stringID);
            medTiles[i].setId(stringID);
            largeTiles[i].setId(stringID);

            stackPane[i] = new StackPane();
            stackPane[i].getChildren().addAll(largeTiles[i],
                    medTiles[i], smallTiles[i]);

            ID++;
        }

        return stackPane;
    }


    /**
     * Sorts Tiles, so they're organized in an "n x n" grid (square).
     * Stack[0] in gridPane[0][0]
     * Stack[1] in gridPane[1][0]
     * Fills grid from left -> right, downward.
     * @return organized and filled in grid
     */
    public static GridPane addStackToGrid(StackPane[] cardStacks){
        GridPane gridPane = new GridPane();

        // Assumes "n x n" grid
        double TOTAL_COLS = Math.sqrt(cardStacks.length);

        // Allocate each stack to a single grid space
        int row = 0;
        int col = 0;
        for(int i = 0; i < cardStacks.length; i++){
            gridPane.add(cardStacks[i], col, row);
            col++;

            if(col == TOTAL_COLS){
                col = 0;
                row++;
            }
        }

        // Sets lines visible, turn off after testing.
        gridPane.setGridLinesVisible(false);

        return gridPane;
    }


    /**
     * Checks if a stack is empty (has no more circles)
     * @return True if stack is empty
     */
    public static boolean isEmpty(StackPane stack){
        return stack.getChildren().size() == 0;
    }

    /**
     * Checks if two stacks have matching elements (color and size). Assumes
     *  previouslyChosenStack != null. After elements were removed. Function
     *  checks if stacks became empty & signals previouslyChosenStack to equal
     *  null (user can choose new start location)
     *  Possible Results:
     *      1. Yes, matching elements -> Removes elements -> Returns True
     *      2. No matching elements -> Returns False
     * @return True if at least 1 same shape exists between two stacks
     */
    public static boolean makeMatches(StackPane newlyChosenStack){
        // Get 'radius & color' tile attributes for each stack
        String[] newlyChosenStackAttr = getRadiusColorAttr(
                newlyChosenStack.getChildren().toString());
        String[] previouslyChosenStackAttr = getRadiusColorAttr(
                previouslyChosenStack.getChildren().toString());


        // Default settings = [0,0]. Change this to indicate null elements
        int row = 0;
        int[][] matchingIndexes = new int[3][2]; //[newStack][prevStack] etc.
        for(row = 0; row < 3; row++){
            matchingIndexes[row][0] = -1;
            matchingIndexes[row][1] = -1;
        }

        // Iterate attributes and see if any attributes match
        row = 0;
        for(int i = 0; i < newlyChosenStackAttr.length; i++){
            for(int j = 0; j < previouslyChosenStackAttr.length; j++){
                if(Objects.equals(newlyChosenStackAttr[i],
                        previouslyChosenStackAttr[j])){
                    matchingIndexes[row][0] = i;
                    matchingIndexes[row][1] = j;
                    row++;
                }
            }
        }


        // If first element in matchingIndexes != [-1][-1], then
        // matches = successful!
        if(matchingIndexes[0][0] != -1){
            // Iterate matchingIndexes and remove elements
            int filledRows = row;
            int offset = 0;
            for(row = 0; row < filledRows; row++){
                // -1 = null. Check if not null to remove element
                if(matchingIndexes[row][0] != -1){
                    newlyChosenStack.getChildren().remove(
                            matchingIndexes[row][0]-offset);
                    previouslyChosenStack.getChildren().remove(
                            matchingIndexes[row][1]-offset);
                    offset++;
                }
            }
        }else{
            return false;
        }

        // Check if empty -> set to null to indicate that user can pick new tile
        // Else, set newly picked tile as previously picked.
        if (isEmpty(newlyChosenStack)){
            setPreviouslyChosenStack(null);
        }else{
            setPreviouslyChosenStack(newlyChosenStack);
        }

        return true;
    }


    /**
     * Checks if user has chosen a base card to match w/ new selection. May
     *  return false if new game or combo lost.
     * @return True if user can match
     */
    public static boolean userCanMatch(){
        if(previouslyChosenStack == null || isEmpty(previouslyChosenStack)){
            return false;
        }
        return true;
    }

    /**
     * Sets the previously chosen stack to new stack
     */
    public static void setPreviouslyChosenStack(StackPane stack){
        previouslyChosenStack = stack;
    }

    /**
     * Formats the string data from a stack to only keep radius & fill. Only
     *  formats string for a single stack at a time. Does not match attributes.
     * Pass raw data from new & prev stacks in the format:
     *   [Circle[id=5, centerX=0.0, centerY=0.0, radius=35.0, fill=0x800080ff],
     *   Circle[id=5, centerX=0.0, centerY=0.0, radius=25.0, fill=0x800080ff],
     *   Circle[id=5, centerX=0.0, centerY=0.0, radius=15.0, fill=0x800080ff]]
     * (Example above is data from a single stack.)
     * @param data Raw data (see above) obtained from
     *             previouslyChosenStack.getChildren.toString()
     * @return Array radius & color data for each tile in stack
     */
    public static String[] getRadiusColorAttr(String data){
        // Only keep 'radius=#, fill=#' part of data
        String[] stackModData = data.split("centerY=0.0, ");

        // Add radius and color data to return array
        String[] stackAttr = new String[stackModData.length-1];
        String[] tmpStringArray;
        for(int i = 1; i < stackModData.length; i++){
            tmpStringArray = stackModData[i].split(", stroke=");
            stackAttr[i-1] = tmpStringArray[0];
        }

        return stackAttr;
    }
}
