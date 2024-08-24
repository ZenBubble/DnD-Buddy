package ui.windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import model.Character;
import ui.GuiController;
import ui.components.EntryField;
import ui.components.StatSpinner;

// Represents a window displaying new character options
public class NewCharacterWindow extends InteractableWindow {
    EntryField name;
    EntryField race;
    EntryField job;
    StatSpinner str;
    StatSpinner dex;
    StatSpinner con;
    StatSpinner inte;
    StatSpinner wis;
    StatSpinner cha;
    JButton confirm;

    // EFFECTS: constructs a new character window
    public NewCharacterWindow() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initFields();
        addToPanel();
        addConfirmButton();
        confirm.setAlignmentX(CENTER_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: adds a confirm button to window
    private void addConfirmButton() {
        confirm = new JButton("Create New Character"); 
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String n = name.getEntry();
                String c = job.getEntry();
                String r = race.getEntry();
                Character character = new Character(n, r, c);
                int st = str.getValue();
                int de = dex.getValue();
                int co = con.getValue();
                int in = inte.getValue();
                int wi = wis.getValue();
                int ch = cha.getValue();
                character.setStats(st,de,co,in,wi,ch);
                character.calcStartStats();
                GuiController.getApp().getCharacters().addCharacter(character);
                removeAll();
                add(new JLabel("Successfully created character!"));
                repaint();
                revalidate();
            }
        }); 
        add(confirm);
    }

    // MODIFIES: this
    // EFFECTS: initializes fields
    private void initFields() {
        name = new EntryField("Name");
        race = new EntryField("Race");
        job = new EntryField("Class");
        str = new StatSpinner("Strength");
        dex = new StatSpinner("Dexterity");
        con = new StatSpinner("Constituton");
        inte = new StatSpinner("Intelligence");
        wis = new StatSpinner("Wisdom");
        cha = new StatSpinner("Charisma");
    }

    // MODIFIES: this
    // EFFECTS: adds all fields to panel
    private void addToPanel() {
        add(name);
        add(race);
        add(job);
        add(str);
        add(dex);
        add(con);
        add(inte);
        add(wis);
        add(cha);
    }
}
