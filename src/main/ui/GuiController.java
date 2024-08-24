package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import javax.swing.*;
import ui.windows.CharacterWindow;
import ui.windows.DieWindow;
import ui.windows.NewCharacterWindow;

// Represents the handling of the graphical user interface
public class GuiController extends JFrame implements ActionListener {
    private static JLabel label;
    private static JMenuBar menuBar;
    private static JMenu menu;
    private static JMenuItem menuItem;

    private static DndBuddyApp app;

    // EFFECTS: Constructs the JFrame and menu bar
    public GuiController() {
        super();
        try {
            app = new DndBuddyApp();
            new Thread(app).start();
        } catch (FileNotFoundException e) {
            System.out.println("no file found");
        }
        this.setIconImage(StyleInfo.ICON.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("DnDBuddy");
        this.setResizable(false);
        this.setSize(StyleInfo.WIDTH, StyleInfo.HEIGHT);
        this.getContentPane().setBackground(StyleInfo.BACKGROUND);
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        loadMainMenu();
        constructMenuBar();
    }

    // EFFECTS: constructs main menu bar
    private void constructMenuBar() {
        loadFileMenuBar();
        loadCharacterMenuBar();
        loadDiceMenuBar();
    }

    // MODIFIES: this
    // EFFECTS: displays main starting window
    private void loadMainMenu() {
        label = new JLabel("DND Buddy", StyleInfo.ICON, JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setFont(new Font("SANS_SERIF", 0, 100));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setBounds(0, 0, 800, 500);
        this.add(label);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds a file sub menu to menu bar
    private void loadFileMenuBar() {
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);
        menuItem = new JMenuItem("Save", KeyEvent.VK_T);
        menuItem.addActionListener(this);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menu.add(menuItem);
        menuItem = new JMenuItem("Load", KeyEvent.VK_T);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds a character sub menu to menu bar
    private void loadCharacterMenuBar() {
        menu = new JMenu("Character");
        menu.setMnemonic(KeyEvent.VK_C);
        menuBar.add(menu);
        menuItem = new JMenuItem("New Character");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem = new JMenuItem("Character List");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds a dice sub menu to menu bar
    private void loadDiceMenuBar() {
        menu = new JMenu("Dice");
        menuBar.add(menu);
        menu.setMnemonic(KeyEvent.VK_D);
        menuItem = new JMenuItem("Roll Simulator");
        menu.add(menuItem);
        menuItem.addActionListener(this);
        this.setVisible(true);
    }

    // EFFECTS: handles user input
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Exit":
                app.printEvents();
                System.exit(ABORT);
                break;
            case "Load":
                app.loadCharacters();
                break;
            case "Save":
                app.saveCharacters();
                break;
            case "Roll Simulator":
                displayRollSimulator();
                break;
            case "New Character":
                displayNewCharacter();
                break;
            case "Character List":
                if (app.getCharacters().isEmpty()) {
                    displayNoCharacters();
                } else {
                    displayCharacterList();
                }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes all elements and displays new character menu
    private void displayNewCharacter() {
        this.getContentPane().removeAll();
        add(new NewCharacterWindow());
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: removes all elements and displays new roll simulator
    private void displayRollSimulator() {
        this.getContentPane().removeAll();
        add(new DieWindow());
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: removes all elements and displays character list
    private void displayCharacterList() {
        this.getContentPane().removeAll();
        add(new CharacterWindow());
        this.setVisible(true);  
    }

    // MODIFIES: this
    // EFFECTS: removes all elements and displays empoty character list message
    private void displayNoCharacters() {
        this.getContentPane().removeAll();
        JLabel msg = new JLabel("<html>No characters found!<br>Click Character -> New Character to get started</html>");
        msg.setFont(new Font("MS Comic Sans", 0, 30));
        this.add(msg);
        this.setVisible(true);  
    }

    // EFFECTS: returns app 
    public static DndBuddyApp getApp() {
        return app;
    }
}