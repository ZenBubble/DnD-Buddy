package ui.windows;

import javax.swing.JPanel;

import ui.StyleInfo;

// Represents a generic window for interaction
public abstract class InteractableWindow extends JPanel {
    public InteractableWindow() {
        super();
        setBackground(StyleInfo.BACKGROUND);
        setSize(StyleInfo.WIDTH, StyleInfo.HEIGHT);
    }
}
