package main.java.locations;

import java.util.ArrayList;

/**
 * Populates and holds in an ArrayList all the Dungeon objects in the game world
 * 
 * @version 1.0
 * @author tp275
 */
public class Map {

    // a list holding each dungeon object on the map, in visiting order, with ascending difficulty
    private final ArrayList<Dungeon> dungeonList = new ArrayList<>();
    // the limit on the amount of dungeons in the map
    private final int howManyDungeons = 4;

    /**
     * Populates the list of dungeons
     */
    public Map() {
        populateDungeonList();
    }

    /**
     * Adds howManyDungeons number of new dungeons to dungeonList
     */
    private void populateDungeonList() {
        for (int i = 0; i < this.howManyDungeons; i++) {
            this.dungeonList.add(new Dungeon(i, i));
        }
        // Set final dungeon in list to be the last dungeon
        this.dungeonList.get(howManyDungeons-1).setLastDungeon();
    }

    /**
     * Returns the Dungeon from the dungeonList that matches the given ID
     * 
     * @param id - The unique ID number of the dungeon that is wanted
     * @return Dungeon from dungeonList with given ID, else null
     */
    public Dungeon getDungeonByID(int id) {
        for (Dungeon d : dungeonList) {
            if (d.getID() == id) {
                return d;
            }
        }
        return null;
    }

    /**
     * Returns the limit on the number of dungeons there are in the world map
     * 
     * @return Limit on number of dungeons there are in the world map
     */
    public int getHowManyDungeons() {
        return this.howManyDungeons;
    }
}
