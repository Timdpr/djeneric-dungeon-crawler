package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.Test;

import main.java.locations.Floor;

/**
 * Contains tests covering the Floor class
 * 
 * @version 1.0
 * @author tp275
 */
class FloorTest {

    private Floor floor;

    /**
     * Tests Floor's checkValidPlayerLocation method with some valid and invalid locations
     */
    @Test
    void checkValidPlayerLocationShouldReturnCorrectBoolean() {
        this.floor = new Floor(0, 0);
        assertTrue(floor.checkValidPlayerLocation(new Point(3, 2)));
        assertFalse(floor.checkValidPlayerLocation(new Point(0, 0)));
        assertFalse(floor.checkValidPlayerLocation(new Point(-1, -1)));
    }

    /**
     * Tests that the start point of a new 0-difficulty floor is one of the possible 3
     */
    @Test
    void startPosAtDifficultyZeroShouldBeOneOfThree() {
        this.floor = new Floor(0, 0);
        Point p1 = new Point(3, 1);
        Point p2 = new Point(6, 2);
        Point p3 = new Point(5, 4);
        Point sp = floor.getStartPos();
        assert(p1.equals(sp) || p2.equals(sp) || p3.equals(sp));
    }
}
