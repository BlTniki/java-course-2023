package edu.hw3.task4;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import org.jetbrains.annotations.Range;

@SuppressWarnings("checkstyle:MagicNumber")
public final class RomanUtils {
    private static final Map<Integer, String> CONVERSIONS;

    static {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
        CONVERSIONS = Collections.unmodifiableMap(map.descendingMap());
    }

    private RomanUtils() {
    }

    public static String convertToRoman(@Range(from = 1, to = 3999) final int n) {
        int tmp = n;
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Integer, String> entry : CONVERSIONS.entrySet()) {
            while (tmp >= entry.getKey()) {
                result.append(entry.getValue());
                tmp -= entry.getKey();
            }
        }
        return result.toString();
    }
}
