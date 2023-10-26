package edu.project2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Logger l = LogManager.getLogger("maze");
        l.info("lolkek");
    }
}
