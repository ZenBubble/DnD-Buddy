package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestSpellList {
    Spell spell1;
    Spell spell2;
    Spell spell3;
    SpellList spellList;

    @BeforeEach
    void runBefore() {
        spell1 = new Spell("lightning", 1);
        spell2 = new Spell("firebolt", 19);
        spell3 = new Spell("wish", 2);
        spellList = new SpellList();
    }

    @Test
    void testConstructor() {
        assertEquals(0, spellList.getLength());
    }

    @Test
    void testGetSpellOfName() {
        assertTrue(spellList.isEmpty());
        spellList.addSpell(spell1);
        spellList.addSpell(spell2);
        spellList.addSpell(spell3);
        assertEquals(spellList.getSpellOfName("wish"), spell3);
        assertEquals(spellList.getSpellOfName("blagaza"), null);
    }

}
