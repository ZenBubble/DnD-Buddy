package ui.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import ui.StyleInfo;

// Represents a spinner for choosing stats
public class StatSpinner extends JPanel {
    private JLabel title;
    private SpinnerNumberModel entry;
    private JSpinner spinner;

    // EFFECTS: constructs a spinner with given title and boundary limit of 20
    public StatSpinner(String tag) {
        super();
        setBackground(StyleInfo.BACKGROUND);
        title = new JLabel(tag + ":");
        add(title);
        entry = new SpinnerNumberModel(0,0,20,1);
        spinner = new JSpinner(entry);   
        add(spinner);
    }

    // EFFETCS: returns current spinner value
    public int getValue() {
        return (int) entry.getNumber();
    }

}
