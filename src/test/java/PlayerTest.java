package test.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.tile.Gold;
import main.java.tile.Stairs;
import main.java.tile.character.Enemy;
import main.java.tile.character.Player;

/**
 * Contains tests covering the Player class
 * 
 * @version 1.0
 * @author tp275
 */
class PlayerTest {

    private Player player;

    /**
     * Tests that a newly created Player
     * has the level number given in the constructor
     */
    @Test
    void playerShouldInitializeWithGivenLevel() {
        this.player = new Player(1);
        assertEquals(1, this.player.getLevel());
        this.player = new Player(2);
        assertEquals(2, this.player.getLevel());
        this.player = new Player(10);
        assertEquals(10, this.player.getLevel());
    }

    /**
     * Tests that a newly created Player has all stats at their correct levels
     */
    @Test
    void playerShouldInitializeWithBaseStats() {
        this.player = new Player(1);
        assertEquals(0, this.player.getCurrentDungeonID());
        assertEquals(0, this.player.getCurrentFloorID());
        assertEquals(1, this.player.getLevel());
        assertEquals(50, this.player.getHp());
        assertEquals(0, this.player.getXp());
        assertEquals(0, this.player.getGold());
    }

    /**
     * Tests that a battle between a new Player and a level 1 enemy
     * results in the player having 5 xp
     */
    @Test
    void battleShouldAddXP() {
        this.player = new Player(1);
        Enemy enemy = new Enemy(1);
        this.player.playTile(enemy);
        assertEquals(5, this.player.getXp());
    }

    /**
     * Tests that the Player's playTile method correctly adds the given Gold
     * object's value to PlayerStats and returns the correct String
     */
    @Test
    void goldPickupShouldAddToStatsAndReturnString() {
        this.player = new Player(1);
        Gold gold = new Gold(5);
        String testString = this.player.playTile(gold);
        assertEquals("There was 5 gold scattered here.", testString);
        assertEquals(5, this.player.getGold());
    }

    /**
     * Descends stairs 3 times and tests that floor and dungeon IDs
     * are correct at all times
     */
    @Test
    void descendingFloorsShouldLeadToCorrectFloorsAndDungeons() {
        this.player = new Player(1);
        Stairs stairs = new Stairs();
        assertEquals(0, this.player.getCurrentDungeonID());
        assertEquals(0, this.player.getCurrentFloorID());
        this.player.playTile(stairs);
        assertEquals(1, this.player.getCurrentDungeonID());
        assertEquals(0, this.player.getCurrentFloorID());
        this.player.playTile(stairs);
        assertEquals(1, this.player.getCurrentDungeonID());
        assertEquals(1, this.player.getCurrentFloorID());
        this.player.playTile(stairs);
        assertEquals(2, this.player.getCurrentDungeonID());
        assertEquals(0, this.player.getCurrentFloorID());
    }
}
