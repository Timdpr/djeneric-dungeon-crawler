package main.java.tile;

/**
 * A wall. Borders the playable tiles on the floor, but the
 * player should not be able to move here or interact with the tile
 * 
 * @version 1.0
 * @author tp275
 */
public class Wall extends Tile {

    /**
     * Sets the Wall's name and description
     */
    public Wall() {
        setName("Wall");
        setDescription("You cannot walk here");
    }
}
