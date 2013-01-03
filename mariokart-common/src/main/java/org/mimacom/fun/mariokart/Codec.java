package org.mimacom.fun.mariokart;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;


public class Codec {
    private static final String VALUES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static Random rnd = new Random();

    static String encode(int person, int car) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append(VALUES.charAt(rnd.nextInt(VALUES.length())));
        }
        sb.setCharAt(7, VALUES.charAt(person % 10));
        sb.setCharAt(3, VALUES.charAt(person / 10));
        sb.setCharAt(29, VALUES.charAt(car % 10));
        sb.setCharAt(17, VALUES.charAt(car / 10));
        return sb.toString();
    }

    static String encode(String id, int person, int car) {
        return encode(person, car) + inv128(id);
    }

    static int decodePerson(String s) {
        return VALUES.indexOf(s.charAt(7)) + 10 * VALUES.indexOf(s.charAt(3));
    }

    static int decodeCar(String s) {
        return VALUES.indexOf(s.charAt(29)) + 10 * VALUES.indexOf(s.charAt(17));
    }

    static String decodeId(String s) {
        if (s.length() <= 32) {
            return null;
        }
        return inv128(s.substring(32));
    }
    
    static String urlDecode(String s) {
        try {
            return s == null ? null : URLDecoder.decode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // cannot happen
            throw new AssertionError();
        }
    }
    
    static String urlEncode(String s) {
        try {
            return s == null ? null : URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // cannot happen
            throw new AssertionError();
        }
    }


    private static String inv128(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, (char)(128 - sb.charAt(i)));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(encode(10, 24));
        String id = encode("chuck-norris", 5, 3);
        System.out.println(id);
        System.out.println(decodeId(id));
        System.out.println(decodePerson(id));
        System.out.println(decodeCar(id));
    }
}
