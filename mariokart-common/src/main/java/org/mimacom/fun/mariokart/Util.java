package org.mimacom.fun.mariokart;


import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;


public class Util {
    private static HttpClient client = new HttpClient();

    public static void doWithGet(String url, Getter getter) throws IOException {
        GetMethod get = null;
        try {
            get = new GetMethod(url);
            get.getParams().setSoTimeout(15000);
            if (client.executeMethod(get) != HttpStatus.SC_OK) {
                throw new RuntimeException();
            }
            getter.doGet(get);
        } finally {
            if (get != null) {
                get.releaseConnection();
            }
        }
    }

    public static interface Getter {
        void doGet(GetMethod get) throws IOException;
    }
}
