package model;

import java.util.ArrayList;

import org.json.JSONArray;

// Represents a list of spells
public class SpellList {
    private ArrayList<Spell> spells;

    // EFFECTS: returns spells in list as a JSON array
    public JSONArray spellsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Spell s : spells) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }

    //EFFECTS: Constructs a spell list and assigns it to an array list
    public SpellList() {
        spells = new ArrayList<Spell>();
    }

    //EFFECTS: Returns spell list
    public ArrayList<Spell> getSpells() {
        return spells;
    }

    //REQUIRES: given string much match spell name
    //EFFECTS: Returns spell with given name, null if not found
    public Spell getSpellOfName(String name) {
        String spellName;
        for (Spell spell: spells) {
            spellName = spell.getName();
            spellName = spellName.toLowerCase();
            if (name.equals(spellName)) {
                return spell;
            }
        }
        return null;
    }


    //MODIFIES: this
    //EFFECTS: adds given spell to spell list
    public void addSpell(Spell s) {
        spells.add(s);
    }

    //EFFECTS: returns lists length
    public int getLength() {
        return spells.size();
    }

    //EFFECTS: Returns true if spell list is empty
    public Boolean isEmpty() {
        return spells.isEmpty();
    }


}
