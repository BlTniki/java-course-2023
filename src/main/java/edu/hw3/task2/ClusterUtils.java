package edu.hw3.task2;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public final class ClusterUtils {
    private final static String NON_VALID_INPUT_MESSAGE = "String input non valid!";

    private ClusterUtils() {
    }

    public static List<String> clusterize(final String input) {
        List<String> output = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                stack.add(i);
            } else if (input.charAt(i) == ')') {
                // if there EmptyStackException there must be bad input
                try {
                    final int openIndex = stack.pop();
                    if (stack.isEmpty()) {
                        output.add(input.substring(openIndex, i + 1));
                    }
                } catch (EmptyStackException e) {
                    throw new IllegalArgumentException(NON_VALID_INPUT_MESSAGE);
                }
            }
        }

        // if stuck is not empty there must be bad input
        if (!stack.isEmpty()) {
            throw new IllegalArgumentException(NON_VALID_INPUT_MESSAGE);
        }

        return output;
    }
}
