package persistence;

import model.CharacterList;
import model.Character;
import model.Spell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Adapted with permission from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git.
class JsonWriterTest extends JsonTest {
    private CharacterList cl;
    private Character test1;
    private Character test2;
    private Spell testSpell;
    private Spell testSpell2;

    @BeforeEach
    void runBefore() {
        cl = new CharacterList();
        test1 = new Character("Snatch", "Kenku", "Rogue");
        test2 = new Character("Cassian", "Snake", "Wizard");
        testSpell = new Spell("wish", 2);
        testSpell2 = new Spell("fireball", 1);
        test1.learnSpell(testSpell);
        test1.learnSpell(testSpell2);
        test1.castSpell("wish", 1);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCharacterList() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCharacterList.json");
            writer.open();
            writer.write(cl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCharacterList.json");
            cl = reader.read();
            assertTrue(cl.isEmpty());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCharacterList() {
        try {
            cl.addCharacter(test1);
            cl.addCharacter(test2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCharacterList.json");
            writer.open();
            writer.write(cl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCharacterList.json");
            cl = reader.read();
            List<Character> characters = cl.getCharacters();
            assertEquals(2, characters.size());
            checkCharacter("Snatch", "Rogue", characters.get(0));
            checkCharacter("Cassian", "Wizard", characters.get(1));
            Spell characterSpell = characters.get(0).getSpells().getSpellOfName("wish");
            assertEquals(characterSpell.getMaxUses(), 2);
            assertEquals(characterSpell.getUses(), 1);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}