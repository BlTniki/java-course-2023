package edu.project2.controller;

import edu.project2.Maze;
import edu.project2.UserInterruptException;
import edu.project2.generator.GenerationType;
import edu.project2.generator.Generator;
import edu.project2.generator.GeneratorManager;
import edu.project2.generator.GeneratorUtils;
import edu.project2.renderer.Renderer;
import edu.project2.renderer.RendererManager;
import edu.project2.solver.SolverType;
import java.util.Scanner;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class CliController {
    private final static String BAD_INPUT_MESSAGE = "BAD INPUT! try again...";
    private final Logger mazeLogger;
    private final Logger systemLogger;
    private final Scanner scanner;
    private final GeneratorManager generatorManager;
    private final Renderer renderer;
    private Maze maze;

    public CliController(
        @NotNull Logger mazeLogger,
        @NotNull Logger systemLogger,
        @NotNull Scanner scanner,
        @NotNull GeneratorManager generatorManager,
        @NotNull RendererManager rendererManager
    ) {
        this.mazeLogger = mazeLogger;
        this.systemLogger = systemLogger;
        this.scanner = scanner;
        this.generatorManager = generatorManager;
        this.renderer = rendererManager.getRenderer();
    }

    public void run() {
        try {
            generateMazeTask();
            solveMazeTask();
        } catch (UserInterruptException e) {
            systemLogger.info("Stopping the app...");
        }
    }

    private @Nullable String readUserInput(@NotNull String message) {
        systemLogger.info(message);

        if (!scanner.hasNextLine()) {
            return null;
        }

        return scanner.nextLine();
    }

    private void generateMazeTask() {
        // ask for generation type
        GenerationType genType;
        final StringBuilder askGenType = new StringBuilder("\nChoose generation algorithm (type a num):");
        final GenerationType[] genTypesArr = GenerationType.values();
        for (int i = 0; i < genTypesArr.length; i++) {
            askGenType.append("\n\t%d. %s".formatted(i, genTypesArr[i].toString()));
        }
        while (true) {
            final String genTypeAnswer = readUserInput(askGenType.toString());
            if (genTypeAnswer == null) {
                throw new UserInterruptException();
            }
            try {
                final int genTypeIndex = Integer.parseInt(genTypeAnswer);
                genType = genTypesArr[genTypeIndex];
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                systemLogger.info(BAD_INPUT_MESSAGE);
                continue;
            }
            break;
        }

        final Generator generator = generatorManager.getInstanceOf(genType);

        // ask for maze size
        int height;
        int width;
        final String askMazeSize = "\nType maze height and width (HxW, at least 3x3):";
        while (true) {
            final String mazeSizeAnswer = readUserInput(askMazeSize);
            if (mazeSizeAnswer == null) {
                throw new UserInterruptException();
            }
            if (!mazeSizeAnswer.matches("^[0-9]+x[0-9]+$")) {
                systemLogger.info(BAD_INPUT_MESSAGE);
                continue;
            }
            final String[] mazeSizeAnswerSplit = mazeSizeAnswer.split("x");
            final int tmpHeight = Integer.parseInt(mazeSizeAnswerSplit[0]);
            final int tmpWidth = Integer.parseInt(mazeSizeAnswerSplit[1]);
            if (tmpHeight < GeneratorUtils.MIN_MAZE_HEIGHT || tmpWidth < GeneratorUtils.MIN_MAZE_WIDTH) {
                systemLogger.info(BAD_INPUT_MESSAGE);
                continue;
            }
            height = tmpHeight;
            width = tmpWidth;
            break;
        }

        // generate maze
        systemLogger.info("Start generating maze...");
        maze = generator.generate(height, width);
        systemLogger.info("Done...");
        mazeLogger.info(renderer.render(maze));
    }

    private void solveMazeTask() {
        // ask solver type
        SolverType solverType;
        final StringBuilder askSolver

//        new BreadthFirstSearchSolver().solve(
//            maze,
//            new Coordinate(0, 1),
//            new Coordinate(maze.getHeight() - 1, maze.getWidth() - 2)
//        );
    }
}
