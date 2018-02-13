package main.java.locations;

import java.util.ArrayList;

/**
 * Creates and stores Floors. Difficulty level sets floor difficulty and number of floors.
 * 
 * @version 1.0
 * @author tp275
 */
public class Dungeon {

    // increase in difficulty from 0-4, 4 is boss dungeon
    private final int difficulty;
    // a unique ID number for this dungeon
    private final int id;
    // a list containing every Floor object in this dungeon - size varies with difficulty
    private final ArrayList<Floor> floorList = new ArrayList<>();
    // stores whether or not this dungeon is the final one in the game
    private boolean isLastDungeon = false;
    // the intro text to be played on entering the dungeon
    private String intro;

    /**
     * Stores parameters, populates floors and sets the dungeon intro
     * 
     * @param difficulty - The dungeon's difficulty level
     * @param id - The dungeon's unique ID
     */
    public Dungeon(int difficulty, int id) {
        this.difficulty = difficulty;
        this.id = id;
        populateFloors(difficulty+1); // Difficulty serves as amount of floors also
        setIntro();
    }

    /**
     * Adds Floors to the floorList. They all have the dungeon's difficulty level.
     */
    private void populateFloors(int amount) {
        for (int i = 0; i < amount; i++) {
            floorList.add(new Floor(this.difficulty, i));
        }
    }

    /**
     * Returns the dungeon's unique ID number
     * 
     * @return The dungeon's unique ID number
     */
    public int getID() {
        return this.id;
    }

    /**
     * Returns a Floor from the floorList that matches the given ID number; null if no match
     * 
     * @return A Floor from the floorList that matches the given ID number; null if no match
     */
    public Floor getFloorByID(int id) {
        for (Floor f : this.floorList) {
            if (f.getID() == id) {
                return f;
            }
        }
        System.out.println("No floor with this id was found!");
        return null;
    }

    /**
     * Returns true if this dungeon is the final one in the game, else false
     * 
     * @return True if this dungeon is the final one in the game, else false
     */
    public boolean isLastDungeon() {
        return this.isLastDungeon;
    }

    /**
     * Sets this dungeon as the final one in the game
     */
    public void setLastDungeon() {
        this.isLastDungeon = true;
    }

    /**
     * Returns the intro text to be played on entering the dungeon
     * 
     * @return The dungeon's intro text
     */
    public String getIntro() {
        return this.intro;
    }

    /**
     * Sets the intro text. The text chosen, hardcoded in this method, is dependent on the dungeon's difficulty
     */
    private void setIntro() {
        switch (this.difficulty) {
        case 0:
            this.intro = "You stride into the fiendishly not very difficult Caverns of Adoddle";
            break;
        case 1:
            this.intro = "You hold your breath as you creep through the back entrance of The Putrescent Castle of Rafflesia";
            break;
        case 2:
            this.intro = "You take some aspirin as you prepare to navigate the Torturous Labyrinth of Moderate-To-Really-Quite-Painful Toothache";
            break;
        case 3:
            this.intro = "The Damnable Mines of Drastic Dereliction dare your doughty derriere through its dreadful doors";
            break;
        case 4:
            this.intro = "Uh-oh, you've really done it now. Good luck in the Final Caverns Of Try-Not-To-Die";
            break;
        }
    }
}
