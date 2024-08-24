package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents a character with defined stats and attributes. Can know a spell list
public class Character implements Writable {
    private String name;
    private String race;
    private String job;

    private int lvl;
    private int maxHp;
    private int hp;
    private int hd;
    private int armr;
    private int prof;

    private int str;
    private int dex;
    private int con;
    private int inte;
    private int wis;
    private int cha;

    private int strBon;
    private int dexBon;
    private int conBon;
    private int inteBon;
    private int wisBon;
    private int chaBon;

    private SpellList spellList;

    // EFFECTS: organizes class fields into a json file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("race", race);
        json.put("job", job);
        json.put("lvl", lvl);
        json.put("str", str);
        json.put("dex", dex);
        json.put("con", con);
        json.put("int", inte);
        json.put("wis", wis);
        json.put("cha", cha);
        json.put("spelllist", spellList.spellsToJson());
        return json;
    }

    // EFFECTS: Contructs a character with given name, race and job and starting
    // level 1
    public Character(String name, String race, String job) {
        spellList = new SpellList();
        this.name = name;
        this.race = race;
        this.job = job;
        lvl = 1;
        prof = 2;
        hd = 8;
    }

    // REQUIRES: attribute stats must have values determined
    // MODIFIES: this
    // EFFECTS: calculates side stats
    public void calcStartStats() {
        maxHp = hd + con;
        hp = maxHp;
        armr = 10 + dex;
        strBon = (str - 10) / 2;
        dexBon = (dex - 10) / 2;
        conBon = (con - 10) / 2;
        inteBon = (inte - 10) / 2;
        wisBon = (wis - 10) / 2;
        chaBon = (cha - 10) / 2;
    }

    // MODIFIES: this
    // EFFECTS: replenishes hp back to max and refills spell uses
    public void longRest() {
        hp = maxHp;
        replenishSpells();
    }

    // MODIFIES: this
    // EFFECTS: reduces hp by the given amount, if hp were to fall under 0, hp
    // becomes 0
    public void takeDamage(int amount) {
        if ((hp - amount) < 0) {
            hp = 0;
        } else {
            hp -= amount;
        }
    }

    // MODIFIES: this
    // EFFECTS: increments lvl by i and increases proficiency bonus if applicable
    public void lvlUp(int i) {
        lvl += i;
        if (lvl < 5) {
            prof = 2;
        } else if (lvl < 9) {
            prof = 3;
        } else if (lvl < 13) {
            prof = 4;
        } else if (lvl < 17) {
            prof = 5;
        } else {
            prof = 6;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds given spell to spell list
    public void learnSpell(Spell spell) {
        spellList.addSpell(spell);
    }

    // MODIFIES: this
    // EFFECTS: runs useSpell on the given spell name i amount of times and returns true if
    // successful, false if can't find spell or out of uses
    public boolean castSpell(String spellName, int i) {
        String pendingSpell;
        for (Spell spell : spellList.getSpells()) {
            pendingSpell = spell.getName();
            pendingSpell = pendingSpell.toLowerCase();
            if (spellName.equals(pendingSpell) && spell.canUse()) {
                spell.useSpell(i);
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: runs refillSpell on every single spell in spellList
    public void replenishSpells() {
        for (Spell spell : spellList.getSpells()) {
            spell.refillUses();
        }
    }

    // EFFECT: Returns character name
    public String getName() {
        return name;
    }

    // EFFECT: Returns character race
    public String getRace() {
        return race;
    }

    // EFFECT: Returns character job
    public String getJob() {
        return job;
    }

    // REQUIRES: stat must be one of the given strings AND amt > 0
    // MODIFIES: this
    // EFFECTS: sets all of the stats
    public void setStats(int str, int dex, int con, int inte, int wis, int cha) {
        this.str = str;
        this.dex = dex;
        this.con = con;
        this.inte = inte;
        this.wis = wis;
        this.cha = cha;
    }

    // EFFECTS: returns given stat or zero if it is not defined
    @SuppressWarnings("methodlength")
    public int getStat(String stat) {
        switch (stat) {
            case "str":
                return str;
            case "dex":
                return dex;
            case "con":
                return con;
            case "int":
                return inte;
            case "wis":
                return wis;
            case "cha":
                return cha;
            case "strBon":
                return strBon;
            case "dexBon":
                return dexBon;
            case "conBon":
                return conBon;
            case "intBon":
                return inteBon;
            case "wisBon":
                return wisBon;
            case "chaBon":
                return chaBon;
            default:
                return 0;
        }
    }

    // EFFECTS: returns given attribute or zero if it is undefined
    public int getAttr(String attr) {
        switch (attr) {
            case "lvl":
                return lvl;
            case "hp":
                return hp;
            case "maxHp":
                return maxHp;
            case "hd":
                return hd;
            case "armr":
                return armr;
            case "prof":
                return prof;
            default:
                return 0;
        }
    }

    // EFFECTS: returns spell list
    public SpellList getSpells() {
        return spellList;
    }
}
