package ui.windows;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Character;
import ui.StyleInfo;

// Represents a window for diplaying character actions
public class CharControlWindow extends InteractableWindow {
    Character character;
    JLabel desc;
    JPanel buttons;

    // EFFECTS: constructs a window controlling given character
    public CharControlWindow(Character character) {
        super();
        this.character = character;
        setLayout(new BorderLayout());
        desc = new JLabel("<html>" + CharacterWindow.listStats(character) + "</html>");
        desc.setFont(new Font("default", 0, 20));
        add(desc, BorderLayout.CENTER);
        buttons = new JPanel();
        buttons.setBackground(StyleInfo.ACCENT);
        buttons.add(constructButton("Rest"));
        buttons.add(constructButton("Level Up"));
        buttons.add(constructButton("Take Damage"));
        add(new JLabel(StyleInfo.CHARACTER), BorderLayout.EAST);
        add(buttons, BorderLayout.SOUTH);
    }

    // EFFECTS: constructs a button with given name and assigns actionlistener
    private JButton constructButton(String name) {
        JButton b = new JButton(name); 
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (e.getActionCommand()) {
                    case ("Rest"):
                        character.longRest();
                        break;
                    case ("Level Up"):
                        character.lvlUp(1);
                        break;
                    case ("Take Damage"):
                        character.takeDamage(1);
                        break;
                }
                desc.setText("<html>" + CharacterWindow.listStats(character) + "</html>");
            }
        }); 
        return b;
    }
}
