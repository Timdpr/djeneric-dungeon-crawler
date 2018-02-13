package main.java.tile;

/**
 * A Floor is made up of these. Designed to be inherited by eg. Start, Stairs, Enemy, Player -
 * anything that should be encountered or move around the Floor.
 * 
 * @version 1.0
 * @author tp275
 */
public class Tile {

    // the Tile name
    private String name;
    // the Tile greeting
    private String description;

    /**
     * Sets the Tile's name
     *
     * @param name The name to set the Tile's name as
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the Tile's name
     *
     * @return The Tile's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the Tile's description
     *
     * @param description The description to set the Tile's description as
     */
    protected void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the description for the Tile
     *
     * @return The description for the Tile
     */
    public String getDescription() {
        return this.description;
    }
}
