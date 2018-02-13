package main.java.tile.character;

/**
 * Holds relevant player stats - xp, level and amount of gold - and provides useful methods
 * 
 * @version 1.0
 * @author tp275
 */
public class PlayerStats {

    // the player's experience points
    private int xp;
    // the player's level
    private int level;
    // the amount of gold the player has
    private int gold;

    /**
     * Initially sets xp and gold to 0, and the level to the given parameter
     * 
     * @param level The initial level of the player
     */
    public PlayerStats(int level) {
        this.xp = 0;
        this.level = level;
        this.gold = 0;
    }

    /**
     * Returns the player's current level
     *
     * @return The player's current level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Increments player level by 1
     */
    private void incrementLevel() {
        this.level += 1;
    }
    
    public int getXp() {
        return this.xp;
    }

    /**
     * Adds given xp to total xp. Increments player level and displays message when xp is over certain amounts
     * 
     * @param xp The amount of xp to increase current total by
     * @return True if player has levelled up, else false
     */
    public boolean addXp(int xp) {
        this.xp += xp;
        if ( xp > ((this.level+5) + (Math.pow(this.level, 2))) ) {
            incrementLevel();
            this.xp = 0;
            return true;
        }
        return false;
    }

    /**
     * Returns the amount of gold carried by the player
     *
     * @return Amount of gold carried by player
     */
    public int getGold() {
        return this.gold;
    }

    /**
     * Adds given amount of gold to total gold
     * 
     * @param gold Amount of gold to add to total gold
     */
    public void addGold(int gold) {
        this.gold += gold;
    }

    /**
     * Returns the damage this player should do. Differs from Enemy's method to 
     * provide a way of varying player and enemy damage
     * 
     * @return The damage this player should do
     */
    public int getDamage() {
        return this.getLevel() * 2;
    }

    /**
     * Returns a neat String representation of the stats handled by this class,
     * overriding the default Object toString method
     *
     * @return A neat String representation of the stats handled by this class
     */
    @Override
    public String toString() {
        return "Your current level: " + this.level +
                "\nYour current xp: " + this.xp +
                "\nYour current gold: " + this.gold;
    }
}
