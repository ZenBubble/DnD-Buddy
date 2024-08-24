package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import model.Character;
import model.CharacterList;
import model.Spell;
import model.SpellList;
import model.Dice;
import model.Event;
import model.EventLog;
import persistence.*;

// Represents the main DnDBuddy menu with a navigation function between menus
public class DndBuddyApp implements Runnable {
    private static final String JSON_STORAGE = "./data/characters.json";
    private CharacterList charList;
    private Scanner scanner;
    private Dice dice;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private EventLog logger;

    // EFFECTS: constructs the dnd app main menu
    public DndBuddyApp() throws FileNotFoundException {
        System.out.println("\nWelcome to DnD Buddy!");
        jsonWriter = new JsonWriter(JSON_STORAGE);
        jsonReader = new JsonReader(JSON_STORAGE);
        charList = new CharacterList();
        dice = new Dice();
        scanner = new Scanner(System.in);
        logger = EventLog.getInstance();
    }

    @Override
    // EFFECTS: runs the app which allows parallelism with GUI
    public void run() {
        runApp();
    }

    // EFFECTS: handles user input
    private void runApp() {
        boolean running = true;
        String input = "";
        while (running) {
            displayMainMenu();
            input = scanner.nextLine();
            input = input.toLowerCase();

            if (input.equals("q")) {
                running = false;
            } else {
                processMainInput(input);
            }
        }
        System.out.println("\nSee you next time, traveller!");
        printEvents();
        System.exit(0);
    }

    public void printEvents() {
        Iterator<Event> iterator = logger.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    // EFFECTS: displays main menu to user
    public void displayMainMenu() {
        System.out.println("\nSelect from the following (parantheses is what to input):");
        System.out.println("\t(c)haracters");
        System.out.println("\t(r)oll dice");
        System.out.println("\t(s)ave characters");
        System.out.println("\t(l)oad characters");
        System.out.println("\t(q)uit");
    }

    // EFFECTS: changes menu based on what input is given
    public void processMainInput(String input) {
        while (!(input.equals("c") || input.equals("r") || input.equals("s") || input.equals("l"))) {
            input = scanner.nextLine();
        }
        if (input.equals("c")) {
            displayCharacter();
        } else if (input.equals("r")) {
            while (displayDice()) {
            }
        } else if (input.equals("s")) {
            saveCharacters();
        } else if (input.equals("l")) {
            loadCharacters();
        }
    }

    // EFFFECTS: displays the dice menu and handles user input, returns false when
    // 'back is entered'
    public boolean displayDice() {
        while (true) {
            System.out.println("\nWhat dice would you like to roll? Type 'b' to go back");
            String input = scanner.nextLine();
            if (input.equals("b")) {
                break;
            } else {
                try {
                    int number = Integer.valueOf(input);
                    try {
                        System.out.println("You rolled a d" + number + " and got " + dice.roll(number));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Positive non-zero integers only, please");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid selection. Try again");
                }
            }
        }
        return false;
    }

    public int rollDice(int i) {
        return dice.roll(i);
    }

    public CharacterList getCharacters() {
        return charList;
    }

    // EFFECTS: save character list to file
    public void saveCharacters() {
        try {
            jsonWriter.open();
            jsonWriter.write(charList);
            jsonWriter.close();
            System.out.println("Saved current characters to " + JSON_STORAGE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads character list from file
    public void loadCharacters() {
        try {
            charList = jsonReader.read();
            System.out.println("Loaded characters from " + JSON_STORAGE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: display the character menu
    public void displayCharacter() {
        if (charList.isEmpty()) {
            System.out.println("\nNo character found! Entering character creator");
            charList.addCharacter(createCharacter());
        }
        String input = "";
        while (!(input.equals("s") || (input.equals("n")))) {
            System.out.println("\n(S)elect existing character or create a (n)ew one?");
            input = scanner.nextLine();
            input = input.toLowerCase();
        }
        if (input.equals("s")) {
            handleCharacter(selectCharacter());
        } else {
            charList.addCharacter(createCharacter());
        }
    }

    // MODIFIES: character
    // EFFECTS: handles user input and returns a character
    public Character createCharacter() {
        System.out.println("What is your character's name?");
        String name = scanner.nextLine();
        System.out.println("\nWhat is their race?");
        String race = scanner.nextLine();
        System.out.println("\nFinally, what is your characters class?");
        String job = scanner.nextLine();
        Character character = new Character(name, race, job);
        System.out.println("\nChoose your characters stats! Type any character for the default stat spread.");
        try {
            handleStats(character);
        } catch (InputMismatchException e) {
            System.out.println("\nDefaulting to standard stat spread of (15, 14, 13, 12, 10, and 8)");
            character.setStats(15, 14, 13, 12, 10, 8);
        } finally {
            character.calcStartStats();
        }
        return character;
    }

    // MODIFIES: character
    // EFFECTS: handles the user input menu for choosing stats and assigns them to
    // given character
    public void handleStats(Character character) {
        System.out.println("Strength: ");
        int str = scanner.nextInt();
        System.out.println("Dexterity: ");
        int dex = scanner.nextInt();
        System.out.println("Constitution: ");
        int con = scanner.nextInt();
        System.out.println("Intelligence: ");
        int inte = scanner.nextInt();
        System.out.println("Wisdom: ");
        int wis = scanner.nextInt();
        System.out.println("Charisma: ");
        int cha = scanner.nextInt();
        character.setStats(str, dex, con, inte, wis, cha);
    }

    // EFFECTS: handles the selection of a character from the character list and
    // returns the selected character
    public Character selectCharacter() {
        ArrayList<Character> characters = charList.getCharacters();
        while (true) {
            System.out.println("\nType the name of your character: ");
            for (Character character : characters) {
                System.out.println(character.getName());
            }
            String input = scanner.nextLine();
            input = input.toLowerCase();
            for (Character character : characters) {
                String name = character.getName();
                name = name.toLowerCase();
                if (input.equals((character.getName()).toLowerCase())) {
                    return character;
                }
            }
            System.out.println("Character not found. Try again?");
        }
    }

    // EFFECTS: handles the menu of a selected character
    public void handleCharacter(Character character) {
        printStats(character);
        characterChoice(character);
    }

    // EFFECTS: display the choices for the user in a character menu
    public void characterChoice(Character character) {
        String input = "";
        System.out.println("\nWhat would " + character.getName() + " like to do?");
        while (!(input.equals("r") || (input.equals("l")) || (input.equals("q")) || (input.equals("t"))
                || (input.equals("u")) || (input.equals("d")))) {
            System.out.println("\t(d)isplay stats");
            System.out.println("\t(r)est");
            System.out.println("\t(l)evel up");
            System.out.println("\t(u)se a spell");
            System.out.println("\t(t)ake damage");
            System.out.println("\t(q)uit to menu");
            input = scanner.nextLine();
            input = input.toLowerCase();
        }
        characterInput(input, character);
    }

    // MODIFIES: character
    // EFFECTS: handles the input for the character menu
    public void characterInput(String input, Character character) {
        if (input.equals("r")) {
            character.longRest();
            System.out.println("\n" + character.getName() + " had a nice rest and replenished their hp and spells!");
            characterChoice(character);
        } else if (input.equals("l")) {
            character.lvlUp(1);
            System.out.println("\n" + character.getName() + " leveled up!");
            characterChoice(character);
        } else if (input.equals("t")) {
            takeDamage(character);
        } else if (input.equals("u")) {
            handleSpells(character);
        } else if (input.equals("d")) {
            printStats(character);
            characterChoice(character);
        } else {
            runApp();
        }
    }

    // MODIFIES: character
    // EFFECTS: handles character damage and incapcitated logic
    public void takeDamage(Character character) {
        System.out.println("\nBy how much?");
        try {
            int amt = scanner.nextInt();
            character.takeDamage(amt);
            System.out.println("\n" + character.getName() + " took " + amt + " damage!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid amount!");
        } finally {
            if (character.getAttr("hp") == 0) {
                System.out.println(character.getName() + " falls unconcious!");
            }
            characterChoice(character);
        }
    }

    // EFFECTS: handles interaction of user in regards to spells
    public boolean handleSpells(Character character) {
        while (true) {
            SpellList spellList = character.getSpells();
            String input = "";
            System.out.println("\n" + character.getName() + " ponders his orb...");
            while (!(input.equals("u") || (input.equals("l")) || (input.equals("q")))) {
                input = spellInput();
            }
            if (input.equals("u")) {
                if (spellList.isEmpty()) {
                    System.out.println("\n" + "Spell list empty! Entering spell creator");
                    learnSpell(character);
                } else {
                    while (useSpell(character, spellList)) {
                    }
                }
            } else if (input.equals("l")) {
                learnSpell(character);
            } else {
                handleCharacter(character);
            }
        }
    }

    // EFFECTS: handles the user input of the spell menu
    public String spellInput() {
        System.out.println("\t(u)se an existing spell");
        System.out.println("\t(l)earn a new spell");
        System.out.println("\t(q)uit to character");
        String input = scanner.nextLine();
        input = input.toLowerCase();
        return input;
    }

    // MODIFIES: character
    // EFFECTS: handles the user input of using a spell
    public boolean useSpell(Character character, SpellList spellList) {
        System.out.println("\n" + "Which spell would you like to use?");
        String input = "";
        while (!character.castSpell(input, 1)) {
            System.out.println("Ensure that you have learned the spell and it has uses left! Type 'c' to cancel");
            for (Spell spell : spellList.getSpells()) {
                System.out.println(spell.getName() + ": " + spell.getUses() + "/" + spell.getMaxUses());
            }
            input = scanner.nextLine();
            input = input.toLowerCase();
            if (input.equals("c")) {
                return false;
            }
        }
        Spell spell = spellList.getSpellOfName(input);
        System.out.println(character.getName() + " used " + spell.getName() + "!");
        return false;
    }

    // MODIFIES: character
    // EFFECTS: handles the user input for the creation of a new spell
    public void learnSpell(Character character) {
        System.out.println("What is the spells name?");
        String name = scanner.nextLine();
        System.out.println("\n" + "How many uses does " + name + " have?");
        int uses = 5;
        try {
            uses = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Not an int! Defaulting to 5 uses");
        } finally {
            Spell spell = new Spell(name, uses);
            character.learnSpell(spell);
            System.out.println("\n" + character.getName() + " learned " + name + "!");
            handleSpells(character);
        }
    }

    // EFFECTS: prints the stats of a character
    public void printStats(Character character) {
        System.out.println("\n" + character.getName() + " the " + character.getJob() + " " + character.getRace());
        System.out.println("\tHP: " + character.getAttr("hp") + ", Max HP: " + character.getAttr("maxHp"));
        System.out.println("\tLevel: " + character.getAttr("lvl") + ", Proficiency: " + character.getAttr("prof"));
        System.out.println("\tArmor Class: " + character.getAttr("armr") + ", Hit Dice: " + character.getAttr("hd"));
        System.out.println(
                "\t Strength: " + character.getStat("str") + " + " + character.getStat("strBon"));
        System.out.println("\t Dex: " + character.getStat("dex") + " + " + character.getStat("dexBon"));
        System.out.println(
                "\t Constituion: " + character.getStat("con") + " + " + character.getStat("conBon"));
        System.out.println(
                "\t Intelligence: " + character.getStat("int") + " + " + character.getStat("intBon"));
        System.out.println(
                "\t Wisdom: " + character.getStat("wis") + " + " + character.getStat("wisBon"));
        System.out.println(
                "\t Charisma: " + character.getStat("cha") + " + " + character.getStat("chaBon"));
    }
}
