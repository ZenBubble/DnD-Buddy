package ui.windows;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Character;
import model.CharacterList;
import ui.GuiController;
import ui.StyleInfo;

// Represents windows listing characters and actions relating to it
public class CharacterWindow extends InteractableWindow {
    CharacterList characters;
    JList<String> charList;
    JPanel viewerWindow;
    JLabel charDesc;

    // EFFECTS: constructs a character window
    public CharacterWindow() {
        super();
        characters = GuiController.getApp().getCharacters();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Character character : characters.getCharacters()) {
            String name = character.getName();
            listModel.addElement(name);
        }
        charList = new JList<>(listModel);
        charList.setFont(new Font("default", 0, 30));
        charList.setBackground(StyleInfo.TRIM);
        add(constructSelectWindow());
        add(contructViewerWindow());
        charList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                changeViewer(charList.getSelectedValue());
            }
        });
    }

    // EFFECTS: constructs the character preview panel with defult message
    private JPanel contructViewerWindow() {
        charDesc = new JLabel("Select a character from the left");
        charDesc.setFont(new Font("default", 0, 20));
        viewerWindow = new JPanel();
        viewerWindow.add(charDesc);
        viewerWindow.setBackground(StyleInfo.ACCENT);
        return viewerWindow;
    }

    // EFFECTS: contructs the selection panel listing all characters and options
    private JPanel constructSelectWindow() {
        JPanel selectWindow = new JPanel();
        selectWindow.setLayout(new BorderLayout());
        selectWindow.add(charList, BorderLayout.NORTH);
        selectWindow.setBackground(StyleInfo.BACKGROUND);
        JPanel options = new JPanel();
        options.setBackground(StyleInfo.TRIM);
        options.add(constructSelectButton());
        options.add(constructDeleteButton());
        selectWindow.add(options, BorderLayout.SOUTH);
        return selectWindow;
    }

    // EFFECTS: returns a button for selecting character
    private JButton constructSelectButton() {
        JButton select = new JButton("Select Character");
        select.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Character character : characters.getCharacters()) {
                    if (character.getName().equals(charList.getSelectedValue())) {
                        removeAll();
                        add(new CharControlWindow(character));
                        revalidate();
                    }
                }
            }
        });
        return select;
    }

    // EFFECTS: returns a button for deleting character
    private JButton constructDeleteButton() {
        JButton delete = new JButton("Delete Character");
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Character character : characters.getCharacters()) {
                    if (character.getName().equals(charList.getSelectedValue())) {
                        characters.removeCharacter(character);
                        removeAll();
                        if (characters.isEmpty()) {
                            JLabel msg = new JLabel("No more characters! Create a new one");
                            msg.setFont(new Font("MS Comic Sans", 0, 30));
                            add(msg);
                            repaint();
                        } else {
                            add(new CharacterWindow());
                        }
                        revalidate();
                    }
                }
            }
        });
        return delete;
    }

    // MODIFIES: this
    // EFFECTS: updates the viewer panel with given character
    private void changeViewer(String name) {
        Character selChar = null;
        for (Character character : characters.getCharacters()) {
            if (character.getName().equals(name)) {
                selChar = character;
                break;
            }
        }
        charDesc.setText("<html>" + listStats(selChar) + "</html>");
    }

    // EFFECTS: returns description of character
    public static String listStats(Character character) {
        StringBuilder str = new StringBuilder();
        str.append("<br/>" + character.getName() + " the " + character.getJob() + " " + character.getRace());
        str.append("<br/>HP: " + character.getAttr("hp") + ", Max HP: " + character.getAttr("maxHp"));
        str.append("<br/>Level: " + character.getAttr("lvl") + ", Proficiency: " + character.getAttr("prof"));
        str.append("<br/>Armor Class: " + character.getAttr("armr") + ", Hit Dice: " + character.getAttr("hd"));
        str.append(
                "<br/> Strength: " + character.getStat("str") + " + " + character.getStat("strBon"));
        str.append("<br/> Dex: " + character.getStat("dex") + " + " + character.getStat("dexBon"));
        str.append(
                "<br/> Constitution: " + character.getStat("con") + " + " + character.getStat("conBon"));
        str.append(
                "<br/> Intelligence: " + character.getStat("int") + " + " + character.getStat("intBon"));
        str.append(
                "<br/> Wisdom: " + character.getStat("wis") + " + " + character.getStat("wisBon"));
        str.append(
                "<br/> Charisma: " + character.getStat("cha") + " + " + character.getStat("chaBon"));
        return str.toString();
    }
}
