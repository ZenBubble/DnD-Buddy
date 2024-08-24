package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCharacterList {
    Character test1;
    Character test2;
    Character test3;
    CharacterList charList;
    ArrayList<Character> expectedList;

    @BeforeEach
    void runBefore() {
        test1 = new Character("snatch", "kenku", "rogue");
        charList = new CharacterList();
        expectedList = new ArrayList<Character>();
    }

    @Test
    void testConstructor() {
        assertEquals(0, charList.getLength());
    }

    @Test
    void testAddCharacters() {
        assertTrue(charList.isEmpty());
        charList.addCharacter(test1);
        assertEquals(test1, charList.getIndex(0));
    }

    @Test
    void testRemoveCharacters() {
        charList.addCharacter(test1);
        charList.removeCharacter(test1);
        assertTrue(charList.isEmpty());
    }

    @Test
    void testReturnCharacter() {
        charList.addCharacter(test1);
        expectedList.add(test1);
        assertEquals(charList.getCharacters(), expectedList);
    }

}
