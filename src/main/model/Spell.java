package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents a spell with a name and max uses
public class Spell implements Writable {
    private String name;
    private int maxUses;
    private int uses;

    // EFFECTS: organizes class fields into a json file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("spellname", name);
        json.put("uses", uses);
        json.put("maxuses", maxUses);
        return json;
    }

    //EFFECTS: constructs a spell and sets the max uses to the given int
    public Spell(String name, int maxUses) {
        this.name = name;
        this.maxUses = maxUses;
        uses = maxUses;
    }

    //EFFECTS: returns true if uses > 0
    public boolean canUse() {
        return (uses > 0);
    }

    //REQUIRES: uses >= 1, i >= 1
    //MODIFIES: this
    //EFFECTS: decreases uses by amount
    public void useSpell(int i) {
        uses -= i;
    }

    //MODIFIES: this
    //EFFECTS: resets uses to maxUses
    public void refillUses() {
        uses = maxUses;
    }

    //EFFECTS: returns uses
    public int getUses() {
        return uses;
    }

    //EFFECTS: returns max uses
    public int getMaxUses() {
        return maxUses;
    }

    //EFFECTS: returns name
    public String getName() {
        return name;
    }
}
