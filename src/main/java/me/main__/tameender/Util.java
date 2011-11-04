package me.main__.tameender;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    private static final Logger logger = Logger.getLogger("Minecraft");

    public static void log(String message) {
        log(Level.INFO, message);
    }

    public static void log(Level loglevel, String message) {
        logger.log(loglevel, "[TameEnder] message".replaceAll("message", message));
    }
}
