package edu.hw3.task3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class DictUtils {
    private DictUtils() {
    }

    /**
     * Count each elem frequency in list.
     * @param list elems to count
     * @return dict with elems frequency
     */
    public static <T> Map<T, Integer> freqDict(final @NotNull List<T> list) {
        final HashMap<T, Integer> dict = new HashMap<>();

        for (var el : list) {
            dict.put(el, dict.getOrDefault(el, 0) + 1);
        }

        return dict;
    }
}
