package org.mimacom.fun.mariokart;


import java.awt.Color;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class Selection {

    private String[] personNames = new String[] { "Mario", "Luigi", "Peach", "Yoshi", "R.O.B", "Toad", "Donkey Kong", "Wario", "Bowser",
            "Daisy", "Knochentrocken", "Waluigi" };

    private int person;

    private int car;

    private Random rnd = new Random();

    public Selection() {}

    public Selection(int person, int car) {
        this.person = person;
        this.car = car;
    }

    void randomChoose() {
        setCar(rnd.nextInt(32));
        setPerson(rnd.nextInt(personNames.length));
    }

    public int getPerson() {
        return person;
    }

    public String getPersonName() {
        return personNames[person];
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public int getCar() {
        return car;
    }

    public String getCarName() {
        return "#" + (car + 1);
    }

    public void setCar(int car) {
        this.car = car;
    }
}
