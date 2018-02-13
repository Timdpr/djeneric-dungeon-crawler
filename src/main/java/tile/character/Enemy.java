package main.java.tile.character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * This class defines an enemy that can be stored within the floor plan
 * and encountered and fought by the player. Its name and description are randomly
 * chosen from lists defined within the class. It is a subclass of Character and
 * therefore has hp, a level and a living status to aid this as well as damage calculated here. 
 * 
 * @version 1.0
 * @author tp275
 */
public class Enemy extends Character {

    // a Random object for use by this class
    private final Random random = new Random();
    // the 'goodbye' message for this Enemy
    private String goodbye;

    /**
     * The constructor automatically assigns the random name/description/goodbye
     *
     * @param level The Enemy's level
     */
    public Enemy(int level) {
        super(level);
        setName();
        setDescription();
        setGoodbye();
    }

    /**
     * Sets a random enemy name from the list defined in the method
     */
    private void setName() {
        List<String> names = new ArrayList<>(Arrays.asList(
                "Glarg", "Wharg", "Klang", "Blerp", "Herg", "Flumpk", "Drerf", "Karump", "Blarg", "Klerp"));

        setName(getRandomListElement(names));
    }

    /**
     * Sets a random enemy description from the list defined in the method
     */
    private void setDescription() {
        // Format: You encountered -name-, -description-.
        List<String> descriptions = new ArrayList<>(Arrays.asList(
                ", who is a very hairy monster.",
                ", a terrifying beasty I must say.",
                " - ew, slimy.",
                ". He roars and stomps the ground. Uh-oh.",
                ". \"Grr!\" they shout, very convincingly.",
                ". It doesn't seem to like you.",
                ". Watch out!",
                ". Woah. Good luck!",
                ", wow they look disgusting.",
                ", who does not look happy to see you."
                ));

        setDescription(getRandomListElement(descriptions));
    }

    /**
     * Returns the set goodbye message for the enemy
     *
     * @return The set goodbye message for the enemy
     */
    public String getGoodbye() {
        return this.goodbye;
    }

    /**
     * Sets a random enemy defeat message from the list defined in the method
     */
    private void setGoodbye() {
        List<String> goodbyes = new ArrayList<>(Arrays.asList(
                "The monster comes crashing to the floor...",
                "Ka-chunk! Blergh!",
                "Pow! Biff! Wallop! Knock out!"
                ));

        this.goodbye = getRandomListElement(goodbyes);
    }

    /**
     * Returns a random element from a String list collection. Can handle any size that a Collection can.
     *
     * @return A random element from a String list collection
     */
    private String getRandomListElement(List<String> list) {
        return list.get(this.random.nextInt(list.size()));
    }
    
    /**
     * Returns the damage this enemy should do
     *
     * @return The damage this enemy should do per turn - currently set at its level * 5
     */
    public int getDamage() {
        return this.getLevel();
    }

    /**
     * Returns the XP the player should gain on defeating this enemy - currently equal to the enemy's damage
     *
     * @return The XP the player should gain on defeating this enemy
     */
    public int getXPOnDefeat() {
        return getDamage() * 5;
    }
}
