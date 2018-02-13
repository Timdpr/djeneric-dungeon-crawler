package main.java.tile.character;

import java.awt.Point;

import main.java.locations.Dungeon;
import main.java.locations.Floor;
import main.java.locations.Map;
import main.java.tile.Gold;
import main.java.tile.Tile;

/**
 * The Player object, containing the main playTile method, which carries out the relevant effects
 * on the player and gives the log a string to print
 * The map of the world, containing all Dungeon and Floor objects, is created, held and accessed here.
 * Also keeps track of the location of the player on the current floor.
 * 
 * @version 2.0
 * @author tp275
 */
public class Player extends Character {

    // holds and provides methods for the player's xp, level and gold
    private final PlayerStats stats;
    // the main Map object, holding all the dungeons and providing some utility methods
    private final Map worldMap = new Map();
    // records whether or not the player has won the game
    private boolean victory = false;
    // the id of the Dungeon object the player is currently in
    private int currentDungeonID;
    // the id of the Floor object the player is currently on
    private int currentFloorID;
    // the current location of the player on the current floor
    private Point floorLocation;

    /**
     * Creates a new PlayerStats object with the given player level, and sets
     * the player's initial dungeon, floor, floor location and hp.
     *
     * @param level The Player's level
     */
    public Player(int level) {
        super(level);
        this.stats = new PlayerStats(level);
        this.currentDungeonID = 0;
        this.currentFloorID = 0;
        resetHP();
        resetFloorLocation();
    }

    /**
     * May perform actions depending on the tile given, and returns a string for the ui's playTile
     * method to print (or check for in the case of an enemy)
     * 
     * @param tile The Tile to play
     * @return A descriptive String for the UI to print, or check for in the case of Enemy
     */
    public String playTile(Tile tile) {
        switch (tile.getClass().getSimpleName()) {

        case "Enemy":
            // cast tile to Enemy
            Enemy enemy = (Enemy) tile;
            // create battle
            Battle battle = new Battle(this, enemy);
            // start and print results
            return battle.startBattle();

        case "Gold":
            Gold gold = (Gold)tile;
            stats.addGold(gold.getValue());
            return "There was " + gold.getValue() + " gold scattered here.";

        case "Stairs":
            // try to go down a floor. if there are no more floors to go to...
            if (!descendFloor()) {
                // ...check if this is the last dungeon...
                if (getCurrentDungeon().isLastDungeon()) {
                    // ...if it is, then assume VICTORY!
                    setVictory();
                    setAlive(false); // do this to allow victory check in controller
                    return "";
                } else {
                    // otherwise, go to the next dungeon
                    setDungeon(this.currentDungeonID+1);
                    resetHP(); // and reset the player's hp
                    return "\n**************\nYou take the stairs. They lead to another dungeon. "
                         + "Your HP has been reset. Weird.\n\n" + getCurrentDungeon().getIntro() + "\n";
                }
            } else { // if going down a floor was successful:
                return "\nYou take the stairs, going down another floor...\n";
            }

        case "Start":
            return getCurrentDungeon().getIntro();

        case "Empty":
            return tile.getDescription();
        }
        return "Error: Player's playTile: end reached. Tile name = " + tile.getClass().getSimpleName();
    }

    /**
     * Updates the player's location on the floor by given direction, if the resulting location is valid
     * 
     * @param direction The direction to move, in the form of a vector Point
     * @return true if new location is valid, else false
     */
    public boolean updateLocation(Point direction) {
        // create a copy of current location point 
        Point clonedPoint = new Point(this.floorLocation.x, this.floorLocation.y);
        // move in specified direction
        clonedPoint.translate(direction.x, direction.y);
        // check new location is valid, update current location if it is
        return setFloorLocation(clonedPoint);
    }

    /**
     * Returns the dungeon object that the player is currently in
     * 
     * @return The dungeon object that the player is currently in
     */
    public Dungeon getCurrentDungeon() {
        return this.worldMap.getDungeonByID(currentDungeonID);
    }
    
    /**
     * Returns the ID of the dungeon that the player is currently in
     * 
     * @return The ID of the dungeon that the player is currently in
     */
    public int getCurrentDungeonID() {
        return this.currentDungeonID;
    }
    
    /**
     * Returns the ID of the floor that the player is currently in
     * 
     * @return The ID of the floor that the player is currently in
     */
    public int getCurrentFloorID() {
        return this.currentFloorID;
    }
    
    /**
     * Returns the player's current gold level (from PlayerStats)
     * 
     * @return The player's current gold level (from PlayerStats)
     */
    public int getGold() {
        return this.stats.getGold();
    }
    
    /**
     * Returns the player's current level (from PlayerStats)
     * 
     * @return The player's current level (from PlayerStats)
     */
    @Override
    public int getLevel() {
        return this.stats.getLevel();
    }
    
    /**
     * Returns the player's current amount of XP (from PlayerStats)
     * 
     * @return The player's current amount of XP (from PlayerStats)
     */
    public int getXp() {
        return this.stats.getXp();
    }

    /**
     * Sets the current dungeon that the player is in, by given ID,
     * checking that the ID is no less than the total number of dungeons
     * 
     * @param id The ID of the Dungeon to set
     * @throws IllegalArgumentException When dungeon ID is > total # of dungeons
     */
    private void setDungeon(int id) {
        if (id <= this.worldMap.getHowManyDungeons() && id >= 0) {
            this.currentDungeonID = id;
            this.currentFloorID = 0;
            resetFloorLocation();
        } else {
            throw new IllegalArgumentException("The dungeon ID was not valid");
        }
    }

    /**
     * Attempts to change current floor to next floor in dungeon (ID+1)
     * 
     * @return true if change is successful, false if floor ID is out of bounds (max. ID is dungeon's difficulty level)
     */
    private boolean descendFloor() {
        if (this.currentFloorID+1 <= getCurrentFloor().getDifficulty()) {
            currentFloorID++;
            resetFloorLocation(); // Reset start point
            return true;
        }
        return false;
    }

    /**
     * Returns the Floor object that the player is currently on
     * 
     * @return The Floor object that the player is currently on
     */
    private Floor getCurrentFloor() {
        return this.getCurrentDungeon().getFloorByID(this.currentFloorID);
    }

    /**
     * Sets the player's location on the current floor to be the given Point, if given Point is valid on the floor
     * 
     * @param location - The location on the floor to set as the player's location
     * @return True if location is valid, else false
     */
    private boolean setFloorLocation(Point location) {
        if (getCurrentFloor().checkValidPlayerLocation(location)) {
            this.floorLocation = location;
            return true;
        }
        return false;
    }

    /**
     * Resets the player's floorLocation to the start tile
     */
    private void resetFloorLocation() {
        this.floorLocation = getCurrentFloor().getStartPos();
    }

    /**
     * Returns the floor Tile that the player is currently standing on
     * 
     * @return The floor Tile that the player is currently standing on
     */
    public Tile getFloorTile() {
        return getCurrentFloor().getTileByPoint(this.floorLocation);
    }

    /**
     * Returns a ready-to-print string of the character representation 
     * of the floor plan, with the player's location shown on it
     * 
     * @return A string of the character representation of the floor plan
     */
    public String getPrintableMap() {
        return getCurrentFloor().getFloorMap(this.floorLocation);
    }

    /**
     * Returns whether or not the player has won the game (true = win!)
     *
     * @return Whether or not the player has won the game
     */
    public boolean isVictorious() {
        return this.victory;
    }

    /**
     * Sets the victory flag to true - denotes that the player has won the game
     */
    private void setVictory() {
        this.victory = true;
    }

    /**
     * Returns the player's PlayerStats object
     *
     * @return The player's PlayerStats object
     */
    public PlayerStats getStats() {
        return this.stats;
    }
}
