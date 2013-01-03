package org.mimacom.fun.mariokart;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class Chooser extends JFrame {
    private SelectionPanel selection;

    private JButton choose = createChooseButton();

    public Chooser(SelectionPanel selection) {
        this.selection = selection;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300, 300);
        setTitle("Mario Kart - Kart Chooser");
        setLayout(new BorderLayout());
        JPanel cp = new JPanel();
        cp.add(choose);
        add(cp, BorderLayout.SOUTH);
        add(selection.getComponent(), BorderLayout.CENTER);
        selection.randomChoose();
    }


    private JButton createChooseButton() {
        JButton res = new JButton("Choose");
        res.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selection.randomChoose();
            }
        });
        return res;
    }

    
}
