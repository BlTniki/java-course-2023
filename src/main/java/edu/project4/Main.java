package edu.project4;

import edu.project4.exception.UserInterruptException;
import edu.project4.fractalFrame.FractalFlame;
import edu.project4.transformer.variation.VariationType;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.SwingUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Main {
    public static final int DEF_X_RES = 2000;
    public static final int DEF_Y_RES = 2000;
    public static final int DEF_ITER = 5000;
    public static final int DEF_SAMPLES = 200_000;
    public static final String TYPE_LINE = "\n\t%d. %s";
    public static final String BAD_NUMBER = "Bad input. Must be a number";
    public static final String BAD_RES = "Bad input. Must be like X_RESxY_RES";
    public static final String BAD_VARI_NUMBERS = "Bad input. Must be a number or numbers separated by ', '";
    private final Logger systemLogger = LogManager.getLogger();
    private final Scanner scanner = new Scanner(System.in);
    final int coresAvailable = Runtime.getRuntime().availableProcessors();

    private int xRes;
    private int yRes;
    private int samples;
    private int samplesPerTask;
    private int it;
    private VariationType[] variTypes;
    private Path path;
    private FractalFlame fractalFlame;
    private boolean doCor;
    private double gammaInc;


    private Main() {
    }

    private void run() {
        try {
            readRes();
            readIters();
            readSamples();
            samplesPerTask = (int) Math.round((double) samples / coresAvailable);
            readVaris();
            fractalFlame = new FractalFlame(
                xRes,
                yRes,
                it,
                variTypes
            );

            SwingUtilities.invokeLater(() -> new CanvasExample(
                fractalFlame.histogram.histoPoints.length,
                fractalFlame.histogram.histoPoints[0].length,
                fractalFlame.histogram.histoPoints
            ));

            //noinspection InfiniteLoopStatement
            while (true) {
                runGeneration();
                readUseGammaCorrection();
                readIncreaseGamma();
                saveFlame();
                systemLogger.info("Let's continue generating");
                readSamples();
                samplesPerTask = (int) Math.round((double) samples / coresAvailable);
            }
        } catch (UserInterruptException e) {
            systemLogger.info("Exiting...");
        }
    }

    private @Nullable String readUserInput(@NotNull String message) {
        systemLogger.info(message);

        if (!scanner.hasNextLine()) {
            return null;
        }

        return scanner.nextLine();
    }

    private void readRes() {
        while (true) {
            final String res = readUserInput("Type resolution (def: 2000x2000)");
            if (res == null) {
                throw new UserInterruptException();
            }
            if (res.isEmpty()) {
                xRes = DEF_X_RES;
                yRes = DEF_Y_RES;
                return;
            }
            if (!res.matches("^\\d+x\\d+$")) {
                systemLogger.info(BAD_RES);
                continue;
            }
            try {
                String[] split = res.split("x");
                xRes = Integer.parseInt(split[0]);
                yRes = Integer.parseInt(split[1]);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                systemLogger.info(BAD_RES);
                continue;
            }
            break;
        }
    }

    private void readIters() {
        while (true) {
            final String itS = readUserInput("Type iterations per sample (def: 5000)");
            if (itS == null) {
                throw new UserInterruptException();
            }
            if (itS.isEmpty()) {
                it = DEF_ITER;
                return;
            }
            try {
                it = Integer.parseInt(itS);
            } catch (NumberFormatException e) {
                systemLogger.info(BAD_NUMBER);
                continue;
            }
            break;
        }
    }

    private void readSamples() {
        while (true) {
            final String samplesS = readUserInput("Type samples (def: 200 000)");
            if (samplesS == null) {
                throw new UserInterruptException();
            }
            if (samplesS.isEmpty()) {
                samples = DEF_SAMPLES;
                return;
            }
            try {
                samples = Integer.parseInt(samplesS);
            } catch (NumberFormatException e) {
                systemLogger.info(BAD_NUMBER);
                continue;
            }
            break;
        }
    }

    private void readVaris() {
        final StringBuilder askVariType = new StringBuilder("\nChoose generation algorithm (type a num):");
        final VariationType[] varis = VariationType.values();
        for (int i = 0; i < varis.length; i++) {
            askVariType.append(TYPE_LINE.formatted(i, varis[i].toString()));
        }
        while (true) {
            systemLogger.info("Type variations numbers using ', ' (def: DISK, SPHERICAL)");
            final String typesS = readUserInput(askVariType.toString());
            if (typesS == null) {
                throw new UserInterruptException();
            }
            if (typesS.isEmpty()) {
                variTypes = new VariationType[] {VariationType.DISK, VariationType.SPHERICAL};
                return;
            }
            if (!typesS.matches("^(\\d, )*\\d$")) {
                systemLogger.info(BAD_VARI_NUMBERS);
                continue;
            }
            String[] varisSplit = typesS.split(", ");
            List<VariationType> vs = new ArrayList<>();
            try {
                for (var v : varisSplit) {
                    vs.add(varis[Integer.parseInt(v)]);
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                systemLogger.info(BAD_VARI_NUMBERS);
                continue;
            }
            variTypes = vs.toArray(new VariationType[0]);
            break;
        }
    }

    private void readPath() {
        while (true) {
            final String pathS = readUserInput("Type the path to save flame (def: ./fire.png)");
            if (pathS == null) {
                throw new UserInterruptException();
            }
            if (pathS.isEmpty()) {
                path = Path.of("./fire.png");
                return;
            }
            try {
                path = Path.of(pathS);
                if (Files.isDirectory(path)) {
                    systemLogger.info("Bad input. Must be a a valid path to a file");
                    continue;
                }
            } catch (InvalidPathException e) {
                systemLogger.info("Bad input. Must be a a valid path");
                continue;
            }
            break;
        }
    }

    private void runGeneration() {
        systemLogger.info("Start generating");

        try (ExecutorService executorService = Executors.newFixedThreadPool(coresAvailable)) {
            for (int i = 0; i < coresAvailable; i++) {
                executorService.submit(() -> fractalFlame.proceedSamples(samplesPerTask, ThreadLocalRandom.current()));
            }
        }

        systemLogger.info("Done generating");
    }

    private void readUseGammaCorrection() {
        String answer = readUserInput("Use gamma correction? (y or any string as no):");
        if (answer == null) {
            throw new UserInterruptException();
        }
        doCor = answer.equals("y");
    }

    private void readIncreaseGamma() {
        while (true) {
            final String gammaS = readUserInput("How much increase gamma? (def: 1.0)");
            if (gammaS == null) {
                throw new UserInterruptException();
            }
            if (gammaS.isEmpty()) {
                gammaInc = 1;
                return;
            }
            try {
                gammaInc = Double.parseDouble(gammaS);
            } catch (NumberFormatException e) {
                systemLogger.info(BAD_NUMBER);
                continue;
            }
            break;
        }
    }

    private void saveFlame() {
        readPath();
        ImageCreator.createImage(PixelUtils.histToPixels(fractalFlame.histogram, doCor, gammaInc), path);
        systemLogger.info("saved at " + path.toAbsolutePath());
    }

    public static void main(String[] args) throws InterruptedException {
        new Main().run();
    }
}
