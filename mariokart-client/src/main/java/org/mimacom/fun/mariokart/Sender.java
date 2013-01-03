package org.mimacom.fun.mariokart;




public interface Sender {
    void sendMessage(String[] ids, Selection selection) throws SenderException;
}
