package org.mimacom.fun.mariokart;


import com.skype.Call;
import com.skype.Chat;
import com.skype.Skype;


public abstract class AbstractSender implements Sender {
    @Override
    public void sendMessage(String[] ids, Selection selection) throws SenderException {
        try {
            Chat chat = Skype.chat(ids);
            doSendMessage(chat, selection);
        } catch (Exception e) {
            throw new SenderException(e.getMessage());
        }
    }

    public abstract void doSendMessage(Chat chat, Selection selection) throws Exception;
}
