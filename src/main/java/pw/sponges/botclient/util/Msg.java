package pw.sponges.botclient.util;

/**
 * Copyright (c) 2015 Joe Burnard ("SpongyBacon").
 * By viewing this code, you agree to the terms
 * in the enclosed license.txt file.
 */
public class Msg {

    public static void log(String msg) {
        System.out.println("INFO> " + msg);
    }

    public static void debug(String msg) {
        System.out.println("DEBUG> " + msg);
    }

    public static void warning(String msg) {
        System.out.println("WARNING> " + msg);
    }

}
