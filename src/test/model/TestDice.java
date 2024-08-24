package model;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestDice {
    Dice testDice;

    @BeforeEach
    void runBefore() {
        testDice = new Dice();
    }

    @Test
    void testRoll() {
        int result = testDice.roll(10);
        assertTrue(result <= 10);
    }

}
