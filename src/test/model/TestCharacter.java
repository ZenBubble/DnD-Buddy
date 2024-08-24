package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCharacter {
    Character testCharacter;
    Spell testSpell;

    @BeforeEach
    void runBefore() {
        testCharacter = new Character("Snatch", "Kenku", "Rogue");
        testSpell = new Spell("wish", 1);
        testCharacter.learnSpell(testSpell);
    }

    @Test
    void testConstructor() {
        assertEquals(testCharacter.getName(), "Snatch");
        assertEquals(testCharacter.getRace(), "Kenku");
        assertEquals(testCharacter.getJob(), "Rogue");
        assertEquals(testCharacter.getAttr("hd"), 8);
    }

    @Test 
    void testSetStat() {
        testCharacter.setStats(1, 2, 3, 4, 5, 6);
        assertEquals(testCharacter.getStat("str"), 1);
        assertEquals(testCharacter.getStat("dex"), 2);
        assertEquals(testCharacter.getStat("con"), 3);
        assertEquals(testCharacter.getStat("int"), 4);
        assertEquals(testCharacter.getStat("wis"), 5);
        assertEquals(testCharacter.getStat("cha"), 6);
        assertEquals(testCharacter.getStat("invalid"), 0);
    }

    @Test 
    void testCalcStartStats() {
        testCharacter.setStats(8, 15, 10, 11, 7, 20);
        testCharacter.calcStartStats();
        assertEquals(testCharacter.getAttr("maxHp"), 18);
        assertEquals(testCharacter.getAttr("hp"), 18);
        assertEquals(testCharacter.getAttr("armr"), 25);
        assertEquals(testCharacter.getStat("strBon"), -1);
        assertEquals(testCharacter.getStat("dexBon"), 2);
        assertEquals(testCharacter.getStat("conBon"), 0);
        assertEquals(testCharacter.getStat("intBon"), 0);
        assertEquals(testCharacter.getStat("wisBon"), -1);
        assertEquals(testCharacter.getStat("chaBon"), 5);
    }

    @Test
    void testTakeDamageAndRest() { 
        testCharacter.setStats(0, 0, 10, 0, 0, 0);
        testCharacter.calcStartStats();
        testCharacter.takeDamage(10);
        assertEquals(testCharacter.getAttr("hp"), 8);
        testCharacter.longRest();
        assertEquals(testCharacter.getAttr("hp"), 18);
        testCharacter.takeDamage(100);
        assertEquals(testCharacter.getAttr("hp"), 0);
    }

    @Test 
    void testLvlUp() {
        assertEquals(testCharacter.getAttr("lvl"), 1);
        testCharacter.lvlUp(1);
        assertEquals(testCharacter.getAttr("lvl"), 2);
        assertEquals(testCharacter.getAttr("prof"), 2);
        testCharacter.lvlUp(3);
        assertEquals(testCharacter.getAttr("lvl"), 5);
        assertEquals(testCharacter.getAttr("prof"), 3);
        testCharacter.lvlUp(4);
        assertEquals(testCharacter.getAttr("prof"), 4);
        testCharacter.lvlUp(4);
        assertEquals(testCharacter.getAttr("prof"), 5);
        testCharacter.lvlUp(4);
        assertEquals(testCharacter.getAttr("prof"), 6);
        assertEquals(testCharacter.getAttr("invalid"), 0);
    }

    @Test 
    void testCastSpell() {  
        assertTrue(testCharacter.castSpell("wish", 1));
        Spell characterSpell = testCharacter.getSpells().getSpellOfName("wish");
        assertEquals(characterSpell.getUses(), 0);
        assertFalse(testCharacter.castSpell("wish", 1));
        assertFalse(testCharacter.castSpell("bananarama", 1));
    }

    @Test 
    void testReplenishSpells() {
        testCharacter.castSpell("wish", 1);
        Spell characterSpell = testCharacter.getSpells().getSpellOfName("wish");
        testCharacter.replenishSpells();
        assertEquals(characterSpell.getUses(), 1);
    }
}

