package persistence;

import model.CharacterList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Adapted with permission from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git.
class JsonReaderTest extends JsonTest {
    private CharacterList cl;

    @BeforeEach
    void runBefore() {
        cl = new CharacterList();
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            cl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCharacterList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCharacterList.json");
        try {
            cl = reader.read();
            assertEquals(cl.getLength(), 0);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCharacterList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCharacterList.json");
        try {
            cl = reader.read();
            assertEquals(cl.getLength(), 1);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}