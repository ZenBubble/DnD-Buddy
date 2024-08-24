package model;

import persistence.Writable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a list of characters
public class CharacterList implements Writable {
    private ArrayList<Character> characters;
    EventLog logger;

    @Override
    // EFFECTS: convert characters list to json readable
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("characters", charactersToJson());
        return json;
    }

    // EFFECTS: returns characters in list as a JSON array
    private JSONArray charactersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Character c : characters) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }

    //EFFECTS: Constructs a character list and assigns it to an array list
    public CharacterList() {
        logger = EventLog.getInstance();
        characters = new ArrayList<Character>();
    }

    //EFFECTS: Returns character list
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    //MODIFIES: this
    //EFFECTS: adds given character to character list
    public void addCharacter(Character c) {
        characters.add(c);
        logger.logEvent(new Event("Added a new character"));
    }

    //MODIFIES: this
    //EFFECTS: removes given character from character list
    public void removeCharacter(Character c) {
        characters.remove(c);
        logger.logEvent(new Event("Removed a character"));
    }

    //EFFECTS: returns lists length
    public int getLength() {
        return characters.size();
    }

    //EFFECTS: returns lists length
    public Character getIndex(int i) {
        return characters.get(i);
    }

    //EFFECTS: Returns true if character list is empty
    public Boolean isEmpty() {
        return characters.isEmpty();
    }


}
