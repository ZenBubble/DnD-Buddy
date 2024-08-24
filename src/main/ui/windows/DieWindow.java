package ui.windows;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.GuiController;
import ui.StyleInfo;

// Represents a window displaying a dice and the option to roll them
public class DieWindow extends InteractableWindow {
    private JLabel dieFace;

    // EFFECTS: constructs a die window with die face of 0
    public DieWindow() {
        super();
        setLayout(new GridLayout());
        dieFace = new JLabel("0", StyleInfo.DIE, JLabel.CENTER);
        dieFace.setHorizontalTextPosition(JLabel.CENTER);
        dieFace.setVerticalTextPosition(JLabel.CENTER);
        dieFace.setFont(new Font("SANS_SERIF", 0, 50));
        dieFace.setBounds(0, 0, 800, 500);
        add(dieFace);
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.PAGE_AXIS));
        btnPanel.setBackground(StyleInfo.BACKGROUND);
        btnPanel.add(constructDieButton(4));
        btnPanel.add(constructDieButton(6));
        btnPanel.add(constructDieButton(20));
        btnPanel.add(constructDieButton(100));
        btnPanel.add(Box.createRigidArea(new Dimension(StyleInfo.WIDTH / 3, StyleInfo.HEIGHT)));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(btnPanel);
    }

    // MODIFIES: this
    // EFFECTS: constructs a die button that rolls dice and updates die face with resulting value
    private JButton constructDieButton(int i) {
        JButton b = new JButton("Roll d" + i); 
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dieFace.setText(Integer.toString(GuiController.getApp().rollDice(i)));
            }
        }); 
        return b;
        
    }
}
