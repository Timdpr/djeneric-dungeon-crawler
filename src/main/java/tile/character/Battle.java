package main.java.tile.character;

/**
 * Handles the battling system, making the player and enemy take turns decreasing each other's hp
 * by certain amounts of damage, and ultimately returning a large string describing the battle
 * 
 * @version 1.0
 * @author tp275
 */
public class Battle {

    // the Player object that will be battling the enemy
    private final Player player;
    // the Enemy object that will be battling the player
    private final Enemy enemy;

    /**
     * A class for handling a battle between the player and an enemy. 
     * 
     * @param player The Player object that will be battling the enemy
     * @param enemy The Enemy object that will be battling the player
     */
    public Battle (Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    /**
     * Runs the battle logic, making the player and enemy take turns decreasing each other's hp
     * by certain amounts of damage, and ultimately returning a large string describing the battle
     * 
     * @return All of the text generated during the (automatic) battle, ready for printing by the UI
     */
    public String startBattle() {
        // the battle intro
        StringBuilder output = new StringBuilder("You encountered " + this.enemy.getName() + this.enemy.getDescription() + " Time to battle!");

        // check if enemy is dead - return info and don't battle if so!
        if (!this.enemy.isAlive()) {
            return "Here lies the corpse of " + this.enemy.getName();
        }

        while (isOngoing()) { // while both characters are alive

            if (!playerTurn()) { // carry out player's turn and check if enemy was defeated
                // if enemy was defeated, add relevant messages to string, add xp to player and set enemy as defeated
                output.append(playerHitEnemyMessage());
                output.append("\n").append(this.enemy.getGoodbye());
                output.append("\nYou were victorious! You gained ").append(this.enemy.getXPOnDefeat()).append(" xp.");
                if (addPlayerXP()) { // will return true on level up
                    output.append("\nYou levelled up! Your new level is ").append(getLevel(this.player));
                }
                this.enemy.setAlive(false);
            }

            else if (!enemyTurn()) { // carry out enemy's turn and check if player was defeated
                // if player was defeated, print relevant messages and set player as defeated
                output.append(enemyHitPlayerMessage());
                output.append("\n").append(this.enemy.getName()).append(" killed you!");
                this.player.setAlive(false);
            }

            else { // if nobody was defeated, just add relevant info to string and loop
                output.append(playerHitEnemyMessage());
                output.append(enemyHitPlayerMessage());
            }
        }
        return output.toString();
    }

    /**
     * Adds XP to the player (using playerStats)
     * 
     * @return True if the player levelled up, else false
     */
    private boolean addPlayerXP() {
        return this.player.getStats().addXp(enemy.getXPOnDefeat());
    }

    /**
     * Handles the player's turn in the battle. Decreases HP from enemy and checks if it has died.
     * 
     * @return False if enemy is dead, else true
     */
    private boolean playerTurn() {
        return this.enemy.decreaseHp(this.player.getStats().getDamage());
    }

    /**
     * Handles the enemy's turn in the battle. Decreases HP from player and checks if it has died.
     * 
     * @return False if player is dead, else true
     */
    private boolean enemyTurn() {
        return this.player.decreaseHp(this.enemy.getDamage());
    }

    /**
     * Returns a suitable String telling the player how much damage the enemy did
     *
     * @return A String telling the player how much damage the enemy did
     */
    private String playerHitEnemyMessage() {
        return "\nYou hit " + this.enemy.getName() + " for " + this.player.getStats().getDamage() + "HP.";
    }

    /**
     * Return a suitable String describing how much damage the enemy did to the player
     *
     * @return A String describing how much damage the enemy did to the player
     */
    private String enemyHitPlayerMessage() {
        return "\n" + this.enemy.getName() + " hits you for " + this.enemy.getDamage() + "HP.";
    }

    /**
     * Returns whether both player and enemy are still alive
     *
     * @return True if both player and enemy are still alive, else false
     */
    private boolean isOngoing() {
        return player.isAlive() && enemy.isAlive();
    }

    /**
     * A little helper method returning the level of the given player
     *
     * @param player The Player to return the level of
     * @return The level of the given Player
     */
    private int getLevel(Player player) {
        return player.getStats().getLevel();
    }
}
