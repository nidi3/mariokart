package com.skype;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: stni
 * Date: 20.06.12
 * Time: 19:54
 * To change this template use File | Settings | File Templates.
 */
public class Accessor {
    public static void setInput(Call call, File file) throws SkypeException {
        Utils.executeWithErrorCheck("ALTER CALL " + call.getId() + " SET_INPUT file=\"" + file.getAbsolutePath() + "\"");
    }
}
