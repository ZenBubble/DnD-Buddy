package persistence;

import model.Character;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Adapted with permission from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git.

public class JsonTest {
    protected void checkCharacter(String name, String job, Character character) {
        assertEquals(name, character.getName());
        assertEquals(job, character.getJob());
    }
}