package org.mimacom.fun.mariokart;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.methods.GetMethod;

import com.skype.Chat;


public class MemeSender extends AbstractSender {
    private String id;

    public void setMeme(String name, final int pos) throws IOException {
        Util.doWithGet("http://memegenerator.net/" + name, new Util.Getter() {

            @Override
            public void doGet(GetMethod get) throws IOException {
                String page = get.getResponseBodyAsString();
                // FileWriter fw = new FileWriter("out.html");
                // fw.write(page);
                // fw.close();
                // Pattern fixIdPattern = Pattern.compile("name=\"templateID\" type=\"hidden\" value=\"(\\d+)\"");
                Pattern chooseIdPattern = Pattern.compile("href=\"/create/caption-top-bottom/image/(\\d+)\"");
                // Matcher m = fixIdPattern.matcher(page);
                // if (m.find()) {
                // id = Integer.parseInt(m.group(1));
                // } else {
                Matcher m = chooseIdPattern.matcher(page);
                int count = 0;
                int lastId = -1;
                int iid = -1;
                while (m.find() && count < pos) {
                    iid = Integer.parseInt(m.group(1));
                    if (iid != lastId) {
                        count++;
                    }
                    lastId = iid;
                }
                if (iid == -1) {
                    throw new IOException();
                }
                id = "" + iid;
            }
        });
    }

    @Override
    public void doSendMessage(Chat chat, Selection selection) throws Exception {
        chat.send("http://showcases.mimacom.org/mariokart-server/meme/"
                + Codec.urlEncode(Codec.encode(id, selection.getPerson(), selection.getCar())));
    }

}
