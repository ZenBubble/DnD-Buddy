package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestSpell {
    Spell validSpell;
    Spell invalidSpell;

    @BeforeEach
    void runBefore() {
        validSpell = new Spell("fireball", 10);
        invalidSpell = new Spell("wish", 0);
    }

    @Test
    void testConstructor() {
        assertEquals(10, validSpell.getUses(), validSpell.getMaxUses());
        assertEquals("wish", invalidSpell.getName());
    }

    @Test
    void testCanUse() {
        assertFalse(invalidSpell.canUse());
        assertTrue(validSpell.canUse());
    }

    @Test
    void testUseSpell() {
        validSpell.useSpell(1);
        assertEquals(validSpell.getUses(), 9);
    }

    @Test
    void testRefillUses() {
        validSpell.useSpell(1);
        validSpell.useSpell(1);
        validSpell.useSpell(1);
        validSpell.refillUses();
        assertEquals(validSpell.getUses(), 10);
    }
}
