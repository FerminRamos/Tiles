package TilesPackage;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This is the "entry" of the program.
 *
 * Acceptable game sizes include: 4,16,36...etc.
 * Game grid must be a square w/ an even amount of tiles bc I think it
 * looks aesthetically pleasing :)
 */
public class Main extends Application {
    private final int GAME_SIZE = 64;
    private static Text GAME_STATUS;

    private static StackPane[] CARDSTACK;
    private static int prevBorderLocation = 0;

    /**
     * Main Function. "Entrance"
     */
    public static void main(String[] args){
        launch(args);
    }

    public static EventHandler<MouseEvent> getMouseEvent(){
        // Register Mouse Input
        EventHandler<MouseEvent> mouseEventEventHandler = event -> {
            // Get Tile that was clicked on
            Circle circlePressed = (Circle) event.getTarget();

            // Get Stack Location of said tile
            int stackLocation = Integer.parseInt(circlePressed.getId());

            // Update Score accordingly
            Score.updateScore(CARDSTACK[stackLocation]);

            // Update Border
            if(CardStack.isEmpty(CARDSTACK[stackLocation])){
                removeBorder();
            }else{
                updateBorder(stackLocation);
            }

        };
        return mouseEventEventHandler;
    }



    /**
     * Changes the location of the border being displayed on GUI
     * @param newBorderLocation Stack index within grid
     */
    private static void updateBorder(int newBorderLocation){
        CARDSTACK[prevBorderLocation].setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.NONE,
                        CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        CARDSTACK[newBorderLocation].setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        prevBorderLocation = newBorderLocation;
    }

    /**
     * Removes border from previous location
     */
    public static void removeBorder(){
        CARDSTACK[prevBorderLocation].setBorder(new
                Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    /**
     * Updates the game status found on bottom right in GUI
     * @param text
     */
    public static void updateGameStatus(String text){
        GAME_STATUS.setText(text);
    }


    /**
     * Sets up the stage + Handles Animation Timer for updating GUI
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage primaryStage) {
        // Creates a new set of tiles
        Circle[] smallTiles = Tile.Tile(GAME_SIZE,'s');
        Circle[] medTiles = Tile.Tile(GAME_SIZE, 'm');
        Circle[] largeTiles = Tile.Tile(GAME_SIZE, 'l');

        // Add IO To each tile
        Tile.setTileIO(medTiles);
        Tile.setTileIO(largeTiles);
        Tile.setTileIO(smallTiles);

        // 1 Stack = 3 Shapes = 1 Grid Square
        CARDSTACK = CardStack.createStacks(smallTiles, medTiles,
                largeTiles, GAME_SIZE);

        // GUI Labels: High Score, Current Score, Game Status.
        Text highScoreText = new Text();
        highScoreText.setX(20);
        highScoreText.setY(630);
        highScoreText.setFont(Font.font(18));

        Text currentScoreText = new Text();
        currentScoreText.setX(20);
        currentScoreText.setY(650);
        currentScoreText.setFont(Font.font(18));

        GAME_STATUS = new Text();
        updateGameStatus("Welcome! Select a Tile to Continue.");
        GAME_STATUS.setX(245);
        GAME_STATUS.setY(610);
        GAME_STATUS.setFont(Font.font(20));

        // Creates a new grid for tiles, where they'll live + IO Handler
        GridPane gridPane = CardStack.addStackToGrid(CARDSTACK);

        // Compile our nodes to a group to add to scene
        Group group = new Group(highScoreText, currentScoreText,
                GAME_STATUS, gridPane);

        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        primaryStage.setTitle("An Absolute Rip-Off of the NY Times Game...");
        primaryStage.show();

        // Updates GUI labels
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                highScoreText.setText(
                        "High Score - " + Score.getLongestCombo());
                currentScoreText.setText(
                        "Current Score - " + Score.getCurrentCombo());
            }
        };
        animationTimer.start();
    }
}
