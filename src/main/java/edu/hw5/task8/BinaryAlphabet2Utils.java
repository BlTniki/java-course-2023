package edu.hw5.task8;

import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * If BinaryAlphabetUtils was so good why there no BinaryAlphabetUtils2....
 */
public final class BinaryAlphabet2Utils {

    private BinaryAlphabet2Utils() {
    }

    /*
    task 8.1
     */
    public static boolean isWordHasOddLength(@NotNull String word) {
        return Pattern.compile("^([01][01])*[01]$")
            .matcher(word)
            .matches();
    }

    /*
    task 8.2
     */
    public static boolean zeroOrOne(@NotNull String word) {
        return Pattern.compile("(^0([01][01])*$)|(^1[01]([01][01])*$)")
            .matcher(word)
            .matches();
    }

    /*
    task 8.4
     */
    public static boolean notContain11or111(@NotNull String word) {
        return Pattern.compile("^(?!1{2,3}$)[01]*$")
            .matcher(word)
            .matches();
    }

    /*
    task 8.5
     */
    public static boolean has1AtOddIndexes(@NotNull String word) {
        return Pattern.compile("^(1[01])*1?")
            .matcher(word)
            .matches();
    }
}
