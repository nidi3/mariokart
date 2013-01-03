package org.mimacom.fun.mariokart;

import com.skype.Accessor;
import com.skype.Call;
import com.skype.ContactList;
import com.skype.Friend;
import com.skype.Skype;
import com.skype.SkypeException;
import com.skype.VoiceMail;
import com.skype.connector.Connector;
import com.skype.connector.ConnectorException;
import org.junit.Test;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: stni
 * Date: 20.06.12
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
public class CallTest {
    @Test
    public void call() throws SkypeException, ConnectorException {
        String audioInputDevice = Skype.getAudioInputDevice();
        String name="echo123";
//        String name = "stefan.niederhauser";
//        ContactList list = Skype.getContactList();
//        for (Friend friend : list.getAllFriends()) {
//            if ("botty.42".equals(friend.getId())) {
//                name = "botty.42";
//                break;
//            }
//        }
        Connector c = Connector.getInstance();
        String res;
//        res=c.execute("VOICEMAIL "+name,"VOICEMAIL");
        res = c.execute("CALL " + name, "CALL");
//        String res = c.execute("CALL stefan.niederhauser","CALL");
        String id = res.substring(5, res.indexOf(' ', 6));
        //res = c.execute("ALTER CALL " + id + " SET_OUTPUT file=\"c:\\temp\\skypeout.wav\"", "ALTER");
        res = c.execute("ALTER CALL " + id + " SET_INPUT file=\"c:\\temp\\myout.wav\"", "ALTER");
//        for (Friend friend : list.getAllFriends()) {
//            System.out.println(friend);
//        }
        //VoiceMail voiceMail = Skype.voiceMail("botty.42");
        //voiceMail.
//        Call call = Skype.call("stefan.niederhauser");
        Call call = Skype.call("botty.42");
        Accessor.setInput(call, new File("c:/temp/out.wav"));
        Accessor.setInput(call, new File("c:/temp/out.wav"));
        Accessor.setInput(call, new File("c:/temp/out.wav"));
        Accessor.setInput(call, new File("c:/temp/out.wav"));
        Accessor.setInput(call, new File("c:/temp/out.wav"));
        Accessor.setInput(call, new File("c:/temp/out.wav"));
        call.finish();
        //call.getsend(Call.DTMF.TYPE_0);
    }
}
