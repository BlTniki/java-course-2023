package edu.hw1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class NestedArrayUtils {
    private final static Logger LOGGER = LogManager.getLogger();

    private NestedArrayUtils() {
    }

    /**
     * finds minimum value in the array.
     * @param arr array to find
     * @return minimum value in the array
     */
    public static int minOfArray(int[] arr) {
        int min = arr[0];

        for (int elem: arr) {
            if (elem < min) {
                min = elem;
            }
        }

        return min;
    }

    /**
     * finds maximum value in the array.
     * @param arr array to find
     * @return maximum value in the array
     */
    public static int maxOfArray(int[] arr) {
        int max = arr[0];

        for (int elem: arr) {
            if (elem > max) {
                max = elem;
            }
        }

        return max;
    }

    /**
     * Checks whether it is possible to nest the first array in the second.
     * @param firstArray array that needs to be nested into a second array
     * @param secondArray array that will be nested
     * @return true if it possible else false
     */
    public static boolean isNestable(int[] firstArray, int[] secondArray) {
        int firstArrMin = minOfArray(firstArray);
        int firstArrMax = maxOfArray(firstArray);
        int secondArrMin = minOfArray(secondArray);
        int secondArrMax = maxOfArray(secondArray);

        LOGGER.trace("min and max of first array: %d and %d".formatted(firstArrMin, firstArrMax));
        LOGGER.trace("min and max of second array: %d and %d".formatted(secondArrMin, secondArrMax));

        return firstArrMin > secondArrMin && firstArrMax < secondArrMax;
    }
}
