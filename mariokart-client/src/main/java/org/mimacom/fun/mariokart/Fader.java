package org.mimacom.fun.mariokart;


import java.awt.Color;

import javax.swing.JLabel;


public class Fader extends JLabel {
    private class Worker implements Runnable {
        private int step=20;

        private Color from;

        public void run() {
            for (;;) {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {}
                if (step < 20) {
                    step++;
                    setForeground(new Color(from.getRed() + step * (to.getRed() - from.getRed()) / 20, from.getGreen() + step
                            * (to.getGreen() - from.getGreen()) / 20, from.getBlue() + step * (to.getBlue() - from.getBlue()) / 20));
                }
            }
        }

        public void init(Color from) {
            step = 0;
            this.from = from;
        }
    }

    private Color to;

    private Worker worker;

    public Fader(Color to) {
        this.to = to;
        worker = new Worker();
        setText("");
        new Thread(worker).start();
    }

    public void setText(String text, Color color) {
        super.setText(text);
        setForeground(color);
        worker.init(color);
    }
}
