package main.java.gui;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import main.java.tile.Tile;
import main.java.tile.character.Player;

/**
 * The controller class, as per the MVC pattern.
 * Receives events from, manipulates and updates the GUI/'view'.
 * Contains a Player instance (the 'model') and calls its methods to progress the game.
 * Logs all button presses and text responses from the game to a log file.
 * 
 * @version 1.0
 * @author tp275
 */
public class Controller implements Initializable {

    // the 'log' TextArea for all text to be viewed
    @FXML
    private TextArea log;

    // contains the relevant image to be displayed to the user
    @FXML
    private ImageView image;

    // contains the map text for display
    @FXML
    private Label map;

    // displays the number of the current dungeon (1-5)
    @FXML
    private Label dungeonStat;
    // displays the number of the current floor
    @FXML
    private Label floorStat;
    // displays the player's level
    @FXML
    private Label levelStat;
    // displays the player's HP
    @FXML
    private Label hpStat;
    // displays the player's XP
    @FXML
    private Label xpStat;
    // displays the player's current gold
    @FXML
    private Label goldStat;

    // moves player up on click
    @FXML
    private Button up;
    // moves player down on click
    @FXML
    private Button down;
    // moves player left on click
    @FXML
    private Button left;
    // moves player right on click
    @FXML
    private Button right;
    // displays help message on click
    @FXML
    private Button help;
    // exits application on click
    @FXML
    private Button quit;

    // the player - essentially the top-level 'model' class
    private Player player;

    // java's logger, for easy logging to a text file using logger.info()
    private Logger logger;

    /**
     * Called after window has finished loading.
     * 
     * Initial setup: starts logger, displays intro text,
     * creates a new Player and updates the map and image.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.initializeLogger();
        this.log("Welcome to the most fantastic *Djeneric Dungeon Crawler!*\n");
        this.log("You stride into the fiendishly not very difficult Caverns of Adoddle\n");
        this.player = new Player(1); // create the player
        this.map.setText(this.player.getPrintableMap()); // display initial map
        this.updatePicture(); // display initial picture
    }

    /**
     * Sets up the (java.util) Logger for use by the controller's log method.
     * Creates the log file, which is named using the current system time.
     */
    private void initializeLogger() {
        try {
            // create the Logger
            this.logger = Logger.getLogger("gamelog");
            // create date format for log naming
            SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");
            // create file, with name depending on current time, formatted above
            FileHandler handler = new FileHandler("gamelog_" 
                    + format.format(Calendar.getInstance().getTime()) + ".log");
            // add the file handler to the logger
            this.logger.addHandler(handler);
            // create a new formatter for the logger
            SimpleFormatter formatter = new SimpleFormatter();
            handler.setFormatter(formatter);

            // game should not crash if logging to file is not working:
        } catch (IOException e) {
            System.out.println("IOException while initializing logger");
            e.printStackTrace();
        }
    }

    /**
     * Moves player up on the floor and executes one turn of game
     * 
     * @param event Mouse click/Button fire
     */
    @FXML
    private void up(ActionEvent event) {
        logFileOnly("pressed up");
        play(new Point(-1,0));
    }

    /**
     * Moves player down on the floor and executes one turn of game
     * 
     * @param event Mouse click/Button fire
     */
    @FXML
    private void down(ActionEvent event) {
        logFileOnly("pressed down");
        play(new Point(1,0));
    }

    /**
     * Moves player left on the floor and executes one turn of game
     * 
     * @param event Mouse click/Button fire
     */
    @FXML
    private void left(ActionEvent event) {
        logFileOnly("pressed left");
        play(new Point(0,-1));
    }

    /**
     * Moves player right on the floor and executes one turn of game
     * 
     * @param event Mouse click/Button fire
     */
    @FXML
    private void right(ActionEvent event) {
        logFileOnly("pressed right");
        play(new Point(0,1));
    }

    /**
     * Prints help text to log
     * 
     * @param event Mouse click/Button fire
     */
    @FXML
    private void help(ActionEvent event) {
        logFileOnly("pressed help");
        this.log("Press the movement buttons to move around the current dungeon. "
                + "There are 5 dungeons to fight through.\n");
    }

    /**
     * Exits the application
     * 
     * @param event Mouse click/Button fire
     */
    @FXML
    private void quit(ActionEvent event) {
        logFileOnly("pressed quit");
        Platform.exit();
    }

    /**
     * Carries out one turn of the game. Moves the player,
     * interacts with the Tile at the Player's position,
     * updates the map and stats visuals and checks for finishing conditions.
     * 
     * @param movement A Point representing the direction and velocity to update the player's position by
     */
    private void play(Point movement) {
        // if movement is to a valid location
        if (this.player.updateLocation(movement)) {
            // play tile and append resulting text to log
            this.log(playTile());
            // update map
            updateMap();
            // update displayed stat info
            updateStats();
            // update displayed picture
            updatePicture();
            // check for victory or defeat & display messages
            checkFinished();

        } else {
            this.log("You can't move here! Try again.\n");
        }
    }

    /**
     * Sets the GUI map to the current map (all text-based)
     */
    private void updateMap() {
        this.map.setText(this.player.getPrintableMap());
    }

    /**
     * Plays the tile that the Player is currently located at,
     * by calling the Player's playTile method on the current tile,
     * then returns the String output of the Player's playTile method
     * 
     * @return the String output of the Player's playTile method using the current tile
     */
    private String playTile() {
        Tile currentTile = this.player.getFloorTile();
        return this.player.playTile(currentTile) + "\n";
    }

    /**
     * Sets all displayed stats on the GUI to their current values within Player/PlayerStats
     */
    private void updateStats() {        
        this.dungeonStat.setText(Integer.toString(this.player.getCurrentDungeonID()+1));
        this.floorStat.setText(Integer.toString(this.player.getCurrentFloorID()+1));
        this.levelStat.setText(Integer.toString(this.player.getLevel()));
        this.hpStat.setText(Integer.toString(this.player.getHp()));
        this.xpStat.setText(Integer.toString(this.player.getXp()));
        this.goldStat.setText(Integer.toString(this.player.getGold()));
    }

    /**
     * Sets the displayed picture depending on the current dungeon
     */
    private void updatePicture() {
        // get path of this class
        String path = this.getClass().getResource("").getPath();
        // modify path to point to resource folder
        path = path.replaceFirst("java/", "res/");
        path = path.replaceFirst("/gui", "");
        // make URI, point to relevant dungeon picture using id
        path = "file:" + path + "dungeon" + Integer.toString(this.player.getCurrentDungeonID())+ ".jpg";
        // get image from path and set ImageView to that image
        Image imageFile = new Image(path);
        this.image.setImage(imageFile);
    }

    /**
     * Checks if the player is alive, and if not whether they are victorious or defeated.
     * If either of those, prints relevant message to the log.
     */
    private void checkFinished() {
        if (!this.player.isAlive()) {
            if (this.player.isVictorious()) {
                this.log("\n**************\n\nCongratulations! "
                        + "You battled through every dungeon and took home "
                        + this.player.getGold()
                        + " gold, too. Don't spend it all at once now.\n");
            } else {
                this.log("You died! Tough luck. Restarting....\n\n");
                // this reruns the post-window-loading startup procedure, seemingly with no side effects
                this.initialize(null, null); 
            }
        }
    }

    /**
     * Prints the given text to the GUI log and sends it to be logged to the log file
     * 
     * @param text The text to be logged to the GUI and log file
     */
    protected void log(String text) {
        this.log.appendText(text);
        this.logFileOnly(text);
    }

    /**
     * Writes the given text (and timestamp) to the log file only, using the Logger
     * 
     * @param text The text to be logged to the log file
     */
    private void logFileOnly(String text) {
        this.logger.info(text + "\n ");
    }
}
