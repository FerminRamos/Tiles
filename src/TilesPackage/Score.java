package TilesPackage;

import javafx.scene.layout.StackPane;
import java.util.Random;

/**
 * Keeps track of 2 things: Longest Combo and Player's Current Combo.
 */
public class Score {
    private static int longestCombo = 0;
    private static int currentCombo = 0;


    /**
     * Updates Score based on stack selection
     * @param newlyChosenStack Stack that user clicked on
     */
    public static void updateScore(StackPane newlyChosenStack){
        Random random = new Random();
        String[] motivation = {" Nice Job!", "Great!", "Keep Going!",
                "Rock On!", "On a Roll!"};
        String[] myDadsQuotes = {"Lol, Wrong Answer!", "Nope.",
                "Not Even Close.", "Are You Even Trying?"};

        // userCanMatch() only returns false if no previous stack selected
        if(CardStack.userCanMatch()){
            // If make matches returns true -> match successful, add combo.
            if(CardStack.makeMatches(newlyChosenStack)){
                addCombo();

                // If Both Stacks = Empty, Tell User to select new starting pos.
                // Else, print normal motivational quote.
                if(CardStack.isEmpty(newlyChosenStack)){
                    Main.updateGameStatus("Select new starting position.");
                }else{
                    Main.updateGameStatus(motivation[
                        random.nextInt(motivation.length)] + " +1");
                }
            }else{
                resetCombo();
                Main.updateGameStatus(myDadsQuotes[
                        random.nextInt(myDadsQuotes.length)]
                        + "\n Select New Starting Tile.");
            }
        }else{
            Main.updateGameStatus("Select Matching Tile.");
            CardStack.setPreviouslyChosenStack(newlyChosenStack);
        }
    }


    /**
     * Checks if new combo is greater than previous combo
     *  Returns: True if new combo is greater.
     */
    public static boolean hasNewLongCombo(){
        return longestCombo < currentCombo;
    }

    /**
     * Used when user Correctly Matches a tile.
     */
    public static void addCombo(){
        currentCombo += 1;
    }

    /**
     * Returns user's current combo
     * @return current combo
     */
    public static int getCurrentCombo(){
        return currentCombo;
    }

    /**
     * Returns user's longest combo
     * @return longest combo
     */
    public static int getLongestCombo(){return longestCombo;}

    /**
     * Used when user Mis-Matches a tile.
     *   First, checks if user's current combo = new record
     *   Second, resets currentCombo
     */
    public static void resetCombo(){
        if(hasNewLongCombo()){longestCombo = currentCombo;}
        currentCombo = 0;
        CardStack.setPreviouslyChosenStack(null);
    }
}
