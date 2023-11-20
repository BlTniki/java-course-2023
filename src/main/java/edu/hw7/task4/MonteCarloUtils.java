package edu.hw7.task4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.Range;

public final class MonteCarloUtils {
    private MonteCarloUtils() {
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public static double calcPi(
            @Range(from = 1, to = Integer.MAX_VALUE) int dotCount,
            @Range(from = 1, to = Integer.MAX_VALUE) int threadCount) {
        int remainingDots = dotCount;
        int dotsPerThread = dotCount < threadCount ? 1 : dotCount / threadCount;

        List<MonteCarloThread> threads = new ArrayList<>(threadCount);
        for (int i = 0; i < threadCount - 1 && remainingDots > dotsPerThread; i++) {
            MonteCarloThread thread = new MonteCarloThread(dotsPerThread);
            thread.start();
            threads.add(thread);
            remainingDots -= dotsPerThread;
        }
        threads.add(new MonteCarloThread(remainingDots));
        threads.getLast().start();

        double dotsInsideCircle;
        try {
            for (var thread : threads) {
                thread.join();
            }
            dotsInsideCircle = threads.stream()
                .mapToDouble(MonteCarloThread::getDotsInsideCircle)
                .sum();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return 4 * (dotsInsideCircle / dotCount);
    }

    static final class MonteCarloThread extends Thread {
        private final static double SQUARE_SIDE_HALF = 1;
        private final static double CIRCLE_RADIUS = SQUARE_SIDE_HALF;
        private final Random random;
        private final int dotsToGenerate;
        private int dotsInsideCircle;

        private MonteCarloThread(int dotsToGenerate) {
            this.random = new Random();
            this.dotsToGenerate = dotsToGenerate;
            this.dotsInsideCircle = 0;
        }

        public int getDotsInsideCircle() {
            return dotsInsideCircle;
        }

        @Override
        public void run() {
            for (int i = 0; i < dotsToGenerate; i++) {
                final double x = random.nextDouble(-SQUARE_SIDE_HALF, SQUARE_SIDE_HALF);
                final double y = random.nextDouble(-SQUARE_SIDE_HALF, SQUARE_SIDE_HALF);

                if (Math.sqrt(x * x + y * y) < Math.sqrt(CIRCLE_RADIUS)) {
                    dotsInsideCircle++;
                }
            }
        }
    }
}
