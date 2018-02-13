package main.java.tile;

/**
 * Simply an empty tile for the player to stand on. Does nothing, but enables there
 * to be empty tiles on the floor.
 * 
 * @version 1.0
 * @author tp275
 */
public class Empty extends Tile {

    /**
     * Sets the name and description of the Empty tile
     */
    public Empty() {
        setName("Empty tile");
        setDescription("There is nothing here");
    }
}
