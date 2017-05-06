package org.fczm.common.util;

import java.util.Date;

public class Debug {

    public static void error(String message) {
        String method = Thread.currentThread().getStackTrace()[2].toString();
        System.err.println(new Date() + " " + method + ": " + message);
    }

}
