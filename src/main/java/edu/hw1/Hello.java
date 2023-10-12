package edu.hw1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Hello {
    private final static Logger LOGGER = LogManager.getLogger();

    private Hello() {
    }

    /**
     * Simply greetings through the logger at the info level.
     */
    public static void sayHi() {
        LOGGER.info("Привет, мир!");
    }
}
