package org.mimacom.fun.mariokart;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.skype.Skype;
import com.skype.SkypeException;


public class Starter {
    public static void main(String[] args) throws InterruptedException {
        try {
            ExecutorService es = Executors.newFixedThreadPool(1);
            es.submit(new Runnable() {
                public void run() {
                    try {
                        Skype.getVersion();
                    } catch (SkypeException e) {}
                }
            });
            es.shutdown();
            if (!es.awaitTermination(5, TimeUnit.SECONDS)) {
                es.shutdownNow();
                JOptionPane.showMessageDialog(null, "Skype seems not to be active");
            } else {
                SelectionPanel sel = new SelectionPanel();
                Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                Chooser chooser = new Chooser(sel);
                Inviter inviter = new Inviter(sel);
                chooser.setLocation((screen.width - chooser.getWidth() - inviter.getWidth()) / 2, (screen.height - chooser.getHeight()) / 2);
                inviter.setLocation((screen.width + chooser.getWidth() - inviter.getWidth()) / 2, (screen.height - inviter.getHeight()) / 2);
                chooser.setVisible(true);
                inviter.setVisible(true);
            }
        } catch (Throwable e) {
            JOptionPane.showMessageDialog(null, "Problem with Skype: " + e.getMessage());
            System.exit(1);
        }
    }
}
