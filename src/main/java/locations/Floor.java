package main.java.locations;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import main.java.tile.Empty;
import main.java.tile.Gold;
import main.java.tile.Stairs;
import main.java.tile.Start;
import main.java.tile.Tile;
import main.java.tile.Wall;
import main.java.tile.character.Enemy;

/**
 * Creates from a file and stores a HashMap of the floor, as Points and Tiles.
 * 
 * @version 2.0
 * @author tp275
 */
public class Floor {

    // the Point the player starts at on the floor
    private Point startPos;
    // sets difficulty of the floor: affects the layout loaded and enemies
    private final int difficulty;
    // contains all x,y Points of the floor with their corresponding tiles
    private final HashMap<Point, Tile> floorPlan = new HashMap<>();
    // a String representation of the current floorPlan
    private String[] floorPlanStringList;
    // ID of the floor, helpful as higher floors have lower IDs
    private final int id;
    // a handy Random for use within the class
    private final Random random = new Random();

    /**
     * Stores parameters and creates the floor plan from file
     * 
     * @param difficulty - The floor's difficulty level
     * @param id - The floor's unique ID
     */
    public Floor(int difficulty, int id) {
        this.difficulty = difficulty;
        this.id = id;
        this.createFloorPlan();
    }

    /**
     * Populates floorPlan with all x,y Points of the floor with their corresponding Tiles
     */
    private void createFloorPlan() {
        try { // Instantiate a scanner object and read the given row and column number from the file
            Scanner reader = new Scanner(findFloorplanFile());
            int rows = reader.nextInt();
            int cols = reader.nextInt();

            // make the reader interpret the rest of the file as one token
            reader.useDelimiter("\\Z");
            // split the floor map by newlines and store as a String[]
            this.floorPlanStringList = reader.next().trim().split("\\n");

            // loop through every tile of the floor
            for (int y = 0; y < cols; y++) {
                for (int x = 0; x < rows; x++) {
                    char tileChar = this.floorPlanStringList[x].charAt(y); // find char at 'x,y'
                    if (tileChar == 's') { // note startPos if start tile
                        this.startPos = new Point(x,y);
                    }
                    Point point = new Point(x,y);
                    // put the Point for this location and the corresponding Tile into the floorPlan hashmap
                    floorPlan.put(point, convertCharToTile(tileChar, point));
                }
            }
            reader.close();

        // Handles exception if the file cannot be found
        } catch (FileNotFoundException e) {
            System.out.println("Error: could not find floor plan file");
            e.printStackTrace();
        }
    }

    /**
     * Returns a .txt File containing a representation of the floor, 
     * chosen randomly from a certain amount of files at the specified difficulty level
     * 
     * @return - A .txt File containing a representation of the floor
     */
    private File findFloorplanFile() {
        // Get absolute path of current folder
        String path = this.getClass().getResource("").getPath();
        // Modify that path to point to resource folder
        path = path.replaceFirst("java/", "res/");
        path = path.replaceFirst("/locations", "");
        // Get random choice of floorplan from fixed difficulty level
        // eg. floorplan2-3.txt = difficulty 2, version 3
        String filename = path + "floorplan" + this.difficulty + "-" + (random.nextInt(3)+1) + ".txt";
        return new File(filename);
    }

    /**
     * Returns a fully initialised tile that corresponds with the tileString parameter
     * 
     * @param tileString - A char from the text representation of the floor
     * @param point - The Point on the floor that the tileString char is at
     * @return A fully initialised tile that corresponds with the tileString parameter
     */
    private Tile convertCharToTile(char tileString, Point point) {
        switch (tileString) {
        case '-':
            return new Wall();
        case 's':
            this.startPos = point;
            return new Start();
        case 'x':
            return new Stairs();
        case 'o':
            return new Empty();
        case 'e':
            return new Enemy(this.difficulty+1);
        case 'g':
            return new Gold(this.difficulty+1);
        }
        return null;
    }

    /**
     * Returns the floor's ID number
     * 
     * @return The floor's ID number
     */
    public int getID() {
        return this.id;
    }

    /**
     * Returns the floor's difficulty level
     * 
     * @return The floor's difficulty level
     */
    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     * Checks that the tile at the given point is not a wall or outside the bounds of the floor
     * 
     * @param point - The location to check
     * @return True if floor location contains a usable tile for the player, false otherwise
     */
    public boolean checkValidPlayerLocation(Point point) {
        return this.floorPlan.containsKey(point) && !(this.floorPlan.get(point).getName().equals("Wall"));
    }

    /**
     * Returns a string of the character representation of the floor plan, with the player's location shown on it
     * 
     * @param playerLocation - The Point of the player's current location on the floor. A P will be printed on the map at this location.
     * @return A string of the character representation of the floor plan, with the player's location shown on it
     */
    public String getFloorMap(Point playerLocation) {
        StringBuilder fpString = new StringBuilder();
        for (int i = 0; i < this.floorPlanStringList.length; i++) {
            StringBuilder floorRow = new StringBuilder(this.floorPlanStringList[i]);
            if (i == playerLocation.x) {
                floorRow.setCharAt(playerLocation.y, 'P');
            }
            fpString.append(floorRow);
        }
        return fpString.toString();
    }

    /**
     * Returns the Tile in floorPlan corresponding to the given point.
     * If no match, returns null
     * 
     * @param point - The Point to match
     * @return The corresponding Tile, or null if no match
     */
    public Tile getTileByPoint(Point point) {
        if (this.floorPlan.get(point) == null) {
            return null;
        }
        return this.floorPlan.get(point);
    }

    /**
     * Returns the Point corresponding to the location of the start tile on this floor
     * 
     * @return The Point corresponding to the location of the start tile on this floor
     */
    public Point getStartPos() {
        return this.startPos;
    }
}
