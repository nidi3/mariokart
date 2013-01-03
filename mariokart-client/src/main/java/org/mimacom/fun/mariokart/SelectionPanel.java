package org.mimacom.fun.mariokart;


import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class SelectionPanel {
    private Selection selection = new Selection();

    private Icon[] personIcons = loadPersonIcons();

    private JLabel personLabel = new JLabel();

    private JLabel carLabel = new JLabel();

    private JPanel panel = new JPanel();

    public SelectionPanel() {
        carLabel.setFont(carLabel.getFont().deriveFont(30f));
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.setBorder(new LineBorder(Color.black));
        panel.add(Box.createHorizontalGlue());
        panel.add(personLabel);
        panel.add(Box.createHorizontalStrut(30));
        panel.add(carLabel);
        panel.add(Box.createHorizontalGlue());
    }

    public void randomChoose() {
        getSelection().randomChoose();
        personLabel.setIcon(personIcons[getSelection().getPerson()]);
        carLabel.setText(getSelection().getCarName());
    }

    public Selection getSelection() {
        return selection;
    }

    private Icon[] loadPersonIcons() {
        Icon[] res = new Icon[12];
        for (int i = 0; i < res.length; i++) {
            res[i] = new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource((i + 1) + ".png")));
        }
        return res;
    }

    public void setPerson(int person) {
        personLabel.setIcon(personIcons[person]);
    }

    public void setCar(int car) {
        carLabel.setText("" + (car + 1));
    }

    public JComponent getComponent() {
        return panel;
    }
}
