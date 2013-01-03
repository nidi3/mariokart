package org.mimacom.fun.mariokart;


import com.skype.Chat;


public class SimpleSender extends AbstractSender {
    @Override
    public void doSendMessage(Chat chat, Selection selection) throws Exception {
        chat.send("Brum Brum!");
        chat.send(selection.getPersonName() + " with car " + selection.getCarName());
    }

}
