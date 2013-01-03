package org.mimacom.fun.mariokart;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.skype.ContactList;
import com.skype.Friend;
import com.skype.Skype;
import com.skype.SkypeException;
import com.skype.User.Status;


public class Inviter extends JFrame {
    private SelectionPanel selection;

    private SimpleSender simpleSender = new SimpleSender();

    private ImageSender imageSender = new ImageSender();

    private MemeSender memeSender = new MemeSender();

    private JComponent inviteSimple = createInviteSimple();

    private JComponent inviteImage = createInviteImage();

    private JComponent inviteMeme = createInviteMeme();

    private JList contacts;

    private JComboBox memes;

    private Fader message = new Fader(getBackground());

    public Inviter(SelectionPanel selection) throws SkypeException {
        this.selection = selection;
        contacts = createContactsList();
        loadSelection();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700, 300);
        setTitle("Mario Kart - Inviter");
        setLayout(new BorderLayout());
        JPanel invite = new JPanel();
        invite.setLayout(new BoxLayout(invite, BoxLayout.LINE_AXIS));
        invite.add(Box.createHorizontalGlue());
        invite.add(inviteSimple);
        invite.add(Box.createHorizontalGlue());
        invite.add(inviteImage);
        invite.add(Box.createHorizontalGlue());
        invite.add(inviteMeme);
        invite.add(Box.createHorizontalGlue());
        add(invite, BorderLayout.SOUTH);
        JScrollPane scroll = new JScrollPane(contacts);
        add(scroll, BorderLayout.CENTER);
        message.setHorizontalAlignment(SwingConstants.CENTER);
        add(message, BorderLayout.NORTH);
    }

    private JButton createInviteSimple() {
        JButton res = new JButton("Invite simple");
        res.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveSelection();
                invite(simpleSender);
            }
        });
        return res;
    }

    private JButton createInviteImage() {
        JButton res = new JButton("Invite image");
        res.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveSelection();
                invite(imageSender);
            }
        });
        return res;
    }

    private boolean containsItem(ComboBoxModel model, Object item) {
        for (int i = 0; i < model.getSize(); i++) {
            if (model.getElementAt(i).equals(item)) {
                return true;
            }
        }
        return false;
    }

    private JPanel createInviteMeme() {
        JPanel res = new JPanel();
        memes = new JComboBox();
        memes.setPreferredSize(new Dimension(250, 20));
        memes.setEditable(true);
        res.add(memes);
        JButton button = new JButton("Invite meme");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String current = (String)memes.getSelectedItem();
                String name = current;
                int pos = 1;
                Pattern memePattern = Pattern.compile("(.+?) (\\d+)");
                Matcher m = memePattern.matcher(current);
                if (m.matches()) {
                    name = m.group(1);
                    pos = Integer.parseInt(m.group(2));
                }
                try {
                    memeSender.setMeme(name, pos);
                    if (!containsItem(memes.getModel(), current)) {
                        memes.addItem(current);
                    }
                    invite(memeSender);
                    saveSelection();
                } catch (Exception ex) {
                    message.setText("Meme not found: " + ex.getMessage(), Color.RED);
                }
            }
        });
        res.add(button);
        return res;
    }



    private void invite(Sender s) {
        List<Friend> invitees = new ArrayList<Friend>();
        for (int i = 0; i < contacts.getModel().getSize(); i++) {
            if (contacts.isSelectedIndex(i)) {
                invitees.add((Friend)contacts.getModel().getElementAt(i));
            }
        }
        String[] ids = new String[invitees.size()];
        for (int i = 0; i < invitees.size(); i++) {
            ids[i] = invitees.get(i).getId();
        }
        try {
            s.sendMessage(ids, selection.getSelection());
            message.setText("Invitation sent", Color.GREEN);
        } catch (SenderException ex) {
            message.setText("Invitation not sent: " + ex.getMessage(), Color.RED);
        }
    }

    private void loadSelection() {
        File file = new File("selection.txt");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            List<String> ids = new ArrayList<String>();
            List<String> memeNames = new ArrayList<String>();
            boolean isId = true;
            while (in.ready()) {
                String line = in.readLine();
                if (line.length() == 0) {
                    isId = false;
                } else if (isId) {
                    ids.add(line);
                } else {
                    memeNames.add(line);
                }
            }
            in.close();
            for (int i = 0; i < contacts.getModel().getSize(); i++) {
                if (ids.contains(((Friend)contacts.getModel().getElementAt(i)).getId())) {
                    contacts.getSelectionModel().addSelectionInterval(i, i);
                } else {
                    contacts.getSelectionModel().removeSelectionInterval(i, i);
                }
            }
            memes.removeAllItems();
            for (String memeName : memeNames) {
                memes.addItem(memeName);
            }
        } catch (IOException e) {}
        if (memes.getModel().getSize() == 0) {
            memes.addItem("Pissed-off-Obama");
            memes.addItem("SGTHARTMAN");
            memes.addItem("chuck-norris");
            memes.addItem("jesus-says");
        }
    }

    private void saveSelection() {
        File file = new File("selection.txt");
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            for (int i = 0; i < contacts.getModel().getSize(); i++) {
                if (contacts.isSelectedIndex(i)) {
                    out.write(((Friend)contacts.getModel().getElementAt(i)).getId());
                    out.newLine();
                }
            }
            out.newLine();
            for (int i = 0; i < memes.getModel().getSize(); i++) {
                out.write((String)memes.getModel().getElementAt(i));
                out.newLine();
            }
            out.close();
        } catch (IOException e) {}
    }

    private JList createContactsList() throws SkypeException {
        ContactList cl = Skype.getContactList();
        List<Friend> friends = new ArrayList<Friend>();
        for (Friend f : cl.getAllFriends()) {
            if (f.getOnlineStatus() == Status.ONLINE) {
                friends.add(f);
            }
        }
        Collections.sort(friends, new Comparator<Friend>() {
            @Override
            public int compare(Friend f1, Friend f2) {
                try {
                    return f1.getFullName().compareToIgnoreCase(f2.getFullName());
                } catch (SkypeException e) {
                    return 0;
                }
            }
        });
        JList res = new JList(friends.toArray());
        res.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                try {
                    setText(((Friend)value).getFullName());
                } catch (SkypeException e) {
                    setText("error");
                }
                return this;
            }
        });
        res.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                int start = Math.min(index0, index1);
                int end = Math.max(index0, index1);
                for (int i = start; i <= end; i++) {
                    if (isSelectedIndex(i)) {
                        removeSelectionInterval(i, i);
                    } else {
                        addSelectionInterval(i, i);
                    }
                }
            }
        });
        return res;
    }
}
