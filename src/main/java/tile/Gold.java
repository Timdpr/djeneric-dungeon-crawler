package main.java.tile;

/**
 * A Gold tile for the player to discover and be happy about.
 * Has a certain value that can be varied with eg. difficulty
 * 
 * @version 1.0
 * @author tp275
 */
public class Gold extends Tile {

    // the value/amount of gold in this Gold object
    private final int value;

    /**
     * Sets name, description and specified value of the Gold tile
     *
     * @param value The value/amount of gold in this Gold object
     */
    public Gold(int value) {
        setName("Gold");
        setDescription("You found some gold!");
        this.value = value;
    }

    /**
     * Returns the value of this Gold object
     *
     * @return The value of this Gold object
     */
    public int getValue() {
        return this.value;
    }
}
