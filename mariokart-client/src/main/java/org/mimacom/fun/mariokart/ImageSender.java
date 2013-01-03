package org.mimacom.fun.mariokart;


import com.skype.Chat;


public class ImageSender extends AbstractSender {

    @Override
    public void doSendMessage(Chat chat, Selection selection) throws Exception {
        chat.send("http://showcases.mimacom.org/mariokart-server/main/" + Codec.encode(selection.getPerson() + 1, selection.getCar() + 1));
    }

}
