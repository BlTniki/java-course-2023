package edu.hw5.task2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public final class FridayThe13thUtils {
    private final static int THIRTEENTH_DAY_OF_MONTH_AS_INT = 13;
    private final static int NUMBER_OF_MONTH_IN_YEAR = 12;

    private FridayThe13thUtils() {
    }

    /**
     * Finds all occurrences of Friday the 13th in the specified year.
     *
     * @param year The year for which to find Friday the 13th dates
     * @return A list of LocalDate objects representing all occurrences of Friday the 13th in the given year
     */
    public static List<LocalDate> findFridaysThe13th(@Range(from = 1, to = Integer.MAX_VALUE) int year) {
        List<LocalDate> fridaysThe13th = new ArrayList<>();
        for (int month = 1; month <= NUMBER_OF_MONTH_IN_YEAR; month++) {
            LocalDate date = LocalDate.of(year, month, THIRTEENTH_DAY_OF_MONTH_AS_INT);
            if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                fridaysThe13th.add(date);
            }
        }
        return fridaysThe13th;
    }

    /**
     * Finds the next occurrence of Friday the 13th after the specified date.
     *
     * @param date The date after which to find the next occurrence of Friday the 13th
     * @return The LocalDate object representing the next occurrence of Friday the 13th after the specified date
     */
    public static LocalDate findNextFridayThe13th(final @NotNull LocalDate date) {
        int year = date.getYear();

        List<LocalDate> goodFridaysThe13th = findFridaysThe13th(year)
            .stream()
            .filter(d -> d.isAfter(date))
            .toList();

        while (goodFridaysThe13th.isEmpty()) {
            year++;
            goodFridaysThe13th = findFridaysThe13th(year);
        }

        return goodFridaysThe13th.getFirst();
    }

    public static void main(String[] args) {
        int year1 = 1925;
        int year2 = 2024;

        System.out.println("Fridays the 13th in " + year1 + ": " + findFridaysThe13th(year1));
        System.out.println("Fridays the 13th in " + year2 + ": " + findFridaysThe13th(year2));

        LocalDate currentDate = LocalDate.now();
        LocalDate nextFridayThe13th = findNextFridayThe13th(currentDate);
        System.out.println("Next Friday the 13th after " + currentDate + ": " + nextFridayThe13th);
    }
}
