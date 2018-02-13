package main.java.tile.character;

import java.security.InvalidParameterException;

import main.java.tile.Tile;

/**
 * A Character class designed to be inherited by an Enemy and a Player. Extends Tile functionality
 * to include certain stats - HP, level and whether or not they are alive, as well as relevant
 * methods to set or increase/decrease them. 'goodbye' messages were removed with the Shopkeeper class -
 * there may need to be changes if this is added back in.
 * 
 * @version 1.0
 * @author tp275
 */
public class Character extends Tile {

    // the character's hitpoints - when these are depleted they should no longer be alive!
    private int hp;
    // a character's level - can be used to determine damage, hp, xp logic etc
    private int level;
    // records whether or not the character is living
    private boolean alive = true;

    /**
     * Usual Tile setup but with a level parameter. Level determines things like HP and damage
     *
     * @param level The character's level
     */
    public Character(int level) {
        this.level = level;
        this.hp = level * 3;
    }

    /**
     * Returns the character's current level
     *
     * @return The character's current level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Returns the player's current hitpoints
     *
     * @return The player's current hitpoints
     */
    public int getHp() {
        return this.hp;
    }

    /**
     * Sets the character's hitpoints with a formula dependent on level
     */
    public void resetHP() {
        this.hp = level * 70;
    }

    /**
     * Decreases HP by given amount. 
     * 
     * @param amount The amount of HP to decrease by
     * @return False resulting HP is <= 0, and the Character is therefore dead, else true
     * @throws InvalidParameterException is amount to decrease is <= 0 
     */
    public boolean decreaseHp(int amount) {
        if (amount <= 0) {
            throw new InvalidParameterException("HP to decrease was <= 0");   
        } else if (this.hp - amount > 0) {
            this.hp -= amount;
            return true;
        }
        return false;
    }

    /**
     * Returns whether or not the character is alive
     *
     * @return True if character is alive, false if not
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * Sets the living status of the character to the given parameter
     * 
     * @param alive True = character alive, False = character dead
     */
    public void setAlive(Boolean alive) {
        this.alive = alive;
    }
}
