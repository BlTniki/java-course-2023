package edu.project1;

import org.jetbrains.annotations.NotNull;

interface Dictionary {

    /**
     * Returns random word from dictionary.
     * @return random word
     */
    @NotNull String randomWord();
}
