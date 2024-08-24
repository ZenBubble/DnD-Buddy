package persistence;

import model.Character;
import model.CharacterList;
import model.Spell;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads charList from JSON data stored in file
// Adapted with permission from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git.
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads characterList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CharacterList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCharacterList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses character list from JSON object and returns it
    private CharacterList parseCharacterList(JSONObject jsonObject) {
        CharacterList cl = new CharacterList();
        addCharacters(cl, jsonObject);
        return cl;
    }

    // MODIFIES: cl
    // EFFECTS: parses character list from JSON object and adds them to characters
    private void addCharacters(CharacterList cl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("characters");
        for (Object json : jsonArray) {
            JSONObject nextCharacter = (JSONObject) json;
            addCharacter(cl, nextCharacter);
        }
    }

    // MODIFIES: cl
    // EFFECTS: parses character from JSON object and adds it to character list
    private void addCharacter(CharacterList cl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String race = jsonObject.getString("race");
        String job = jsonObject.getString("job");

        int lvl = jsonObject.getInt("lvl");
        int str = jsonObject.getInt("str");
        int dex = jsonObject.getInt("dex");
        int con = jsonObject.getInt("con");
        int inte = jsonObject.getInt("int");
        int wis = jsonObject.getInt("wis");
        int cha = jsonObject.getInt("cha");

        Character character = new Character(name, race, job);
        character.setStats(str, dex, con, inte, wis, cha);
        character.lvlUp(lvl - 1);
        character.calcStartStats();

        JSONArray jsonArray = jsonObject.getJSONArray("spelllist");
        for (Object json : jsonArray) {
            learnSpell(character, json);
        }

        cl.addCharacter(character);
    }

    // MODIFIES: cl
    // EFFECTS: learn spells for character using spells in jsonObject
    private void learnSpell(Character character, Object json) {
        JSONObject spell = (JSONObject) json;
        String spellName = spell.getString("spellname");
        int maxUses = spell.getInt("maxuses");
        int uses = spell.getInt("uses");
        Spell newSpell = new Spell(spellName, maxUses);
        character.learnSpell(newSpell);
        character.castSpell(spellName, (maxUses - uses));
    }
}
