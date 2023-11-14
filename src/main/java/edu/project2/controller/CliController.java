package edu.project2.controller;

import edu.project2.Cell;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.exception.UserInterruptException;
import edu.project2.generator.GenerationType;
import edu.project2.generator.Generator;
import edu.project2.generator.GeneratorManager;
import edu.project2.generator.GeneratorUtils;
import edu.project2.renderer.Renderer;
import edu.project2.renderer.RendererManager;
import edu.project2.solver.Solver;
import edu.project2.solver.SolverManager;
import edu.project2.solver.SolverType;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class CliController {
    private final static String BAD_INPUT_MESSAGE = "BAD INPUT! try again...";
    private final static String BAD_POINT_MESSAGE = "BAD POINT! outside maze or just wall...";
    public static final String TYPE_LINE = "\n\t%d. %s";
    private final Logger mazeLogger;
    private final Logger systemLogger;
    private final Scanner scanner;
    private final GeneratorManager generatorManager;
    private final SolverManager solverManager;
    private final Renderer renderer;
    private Maze maze;

    public CliController(
        @NotNull Logger mazeLogger,
        @NotNull Logger systemLogger,
        @NotNull Scanner scanner,
        @NotNull GeneratorManager generatorManager,
        @NotNull SolverManager solverManager,
        @NotNull RendererManager rendererManager
    ) {
        this.mazeLogger = mazeLogger;
        this.systemLogger = systemLogger;
        this.scanner = scanner;
        this.generatorManager = generatorManager;
        this.solverManager = solverManager;
        this.renderer = rendererManager.getRenderer();
    }

    public void run() {
        try {
            systemLogger.info("""

            Welcome to the greatest maze generator! Maze solver included! (Applause)
            If you fill tiered at any point of our adventure you can just Ctrl+D outa here! (Recorded audience laughter)
            """);
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
            askGenType.append(TYPE_LINE.formatted(i, genTypesArr[i].toString()));
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

    @SuppressWarnings("checkstyle:CyclomaticComplexity")
    private void solveMazeTask() {
        // ask solver type
        SolverType solverType;
        final StringBuilder askSolverType = new StringBuilder(
            "\nChoose solving maze algorithm (type a num):"
        );
        final SolverType[] solverTypes = SolverType.values();
        for (int i = 0; i < solverTypes.length; i++) {
            askSolverType.append(TYPE_LINE.formatted(i, solverTypes[i].toString()));
        }
        while (true) {
            final String genTypeAnswer = readUserInput(askSolverType.toString());
            if (genTypeAnswer == null) {
                throw new UserInterruptException();
            }
            try {
                final int genTypeIndex = Integer.parseInt(genTypeAnswer);
                solverType = solverTypes[genTypeIndex];
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                systemLogger.info(BAD_INPUT_MESSAGE);
                continue;
            }
            break;
        }

        final Solver solver = solverManager.getInstanceOf(solverType);

        // ask for start and end points
        final String pointRegex = "^[0-9]+ [0-9]+$";
        final Coordinate defStart = new Coordinate(0, 1);
        final Coordinate defEnd = new Coordinate(maze.getHeight() - 1, maze.getWidth() - 2);
        Coordinate start;
        Coordinate end;
        final String askPoints = """

                Type the coordinates of the points between which the path should be found:
                (points should be in the maze and the walls)
                (if you leave field empty the default points will be used)""";
        systemLogger.info(askPoints);
        // start point
        while (true) {
            final String startAnswer = readUserInput(
                "\nstart point (def: %d %d):".formatted(defStart.row(), defStart.col())
            );
            if (startAnswer == null) {
                throw new UserInterruptException();
            }
            if (startAnswer.isEmpty()) {
                start = defStart;
                break;
            }
            if (!startAnswer.matches(pointRegex)) {
                systemLogger.info(BAD_INPUT_MESSAGE);
                continue;
            }
            final String[] startAnswerSplit = startAnswer.split(" ");
            final int row = Integer.parseInt(startAnswerSplit[0]);
            final int col = Integer.parseInt(startAnswerSplit[1]);
            final Cell toCheck = maze.getCellAt(row, col);
            if (toCheck == null || toCheck.type().equals(Cell.Type.WALL)) {
                systemLogger.info(BAD_POINT_MESSAGE);
                continue;
            }
            start = new Coordinate(row, col);
            break;
        }

        // end point
        while (true) {
            final String startAnswer = readUserInput(
                "\nend point (def: %d %d):".formatted(defEnd.row(), defEnd.col())
            );
            if (startAnswer == null) {
                throw new UserInterruptException();
            }
            if (startAnswer.isEmpty()) {
                end = defEnd;
                break;
            }
            if (!startAnswer.matches(pointRegex)) {
                systemLogger.info(BAD_INPUT_MESSAGE);
                continue;
            }
            final String[] startAnswerSplit = startAnswer.split(" ");
            final int row = Integer.parseInt(startAnswerSplit[0]);
            final int col = Integer.parseInt(startAnswerSplit[1]);
            final Cell toCheck = maze.getCellAt(row, col);
            if (toCheck == null || toCheck.type().equals(Cell.Type.WALL)) {
                systemLogger.info(BAD_POINT_MESSAGE);
                continue;
            }
            end = new Coordinate(row, col);
            break;
        }

        // solve the maze
        systemLogger.info("Solving the maze...");
        final List<Coordinate> path = solver.solve(maze, start, end);
        systemLogger.info("Solve....");
        mazeLogger.info(renderer.render(maze, path));
    }
}
