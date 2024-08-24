package ui.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.StyleInfo;

// Represents an entry field with a title and an entry box
public class EntryField extends JPanel {
    private JLabel title;
    private JTextField entry;

    // EFFECTS: Constructs an entryfield
    public EntryField(String tag) {
        super();
        setBackground(StyleInfo.BACKGROUND);
        title = new JLabel(tag + ":");
        add(title);
        entry = new JTextField(10);
        add(entry);
    }

    // EFFECTS: Returns the text of the entry box
    public String getEntry() {
        return entry.getText();
    }
}
