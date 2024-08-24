package model;

import java.util.Random;

// Represents a dice of any size
public class Dice {
    Random rand;

    //EFFECTS: constructs a new dice
    public Dice() {
        rand = new Random();
    }

    //REQUIRES: num > 0
    //EFFECTS: rolls a 6 sided die
    public int roll(int num) {
        return rand.nextInt(num) + 1;
    }

}
