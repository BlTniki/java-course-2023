package edu.project2;

import edu.project2.controller.CliController;
import edu.project2.generator.GeneratorManager;
import edu.project2.renderer.cliRenderer.CliRendererManager;
import java.util.Random;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        final CliController controller = new CliController(
            LogManager.getLogger("maze"),
            LogManager.getLogger(),
            new Scanner(System.in),
            new GeneratorManager(new Random()),
            new CliRendererManager()
        );
        controller.run();
    }
}
