package edu.hw3.task5;

import org.jetbrains.annotations.NotNull;

public final class Person {
    private final String firstName;
    private final String lastName;

    /**
     * Creating new Person from FC like "John Doe".
     * We assume that in FC like "foo bar lol kek..."
     * first word is always first name and other words it's last name.
     *
     * @param fc Person FC. Must contain at least one word
     */
    public Person(final @NotNull String fc) {
        if (fc.isEmpty()) {
            throw new IllegalArgumentException("fc cannot be empty");
        }
        // check for strings like "dwa     " etc.
        if (fc.matches("(^\\s+$)|(^\\s+.+$)|(^.+\\s+$)")) {
            throw new IllegalArgumentException("fc cannot starts or ends with spaces");
        }

        // split the given FC assuming that first word it's first name
        // and other words it's last name
        final String[] fcSplit = fc.split("\\s+", 2);

        this.firstName = fcSplit[0];
        if (fcSplit.length > 1) {
            this.lastName = fcSplit[1];
        } else {
            this.lastName = "";
        }
    }

    /**
     * Return field to compare with other persons.
     * By default, persons compare by last name
     * but if there is no last name first name will be used.
     *
     * @return field for compare
     */
    public String getValToCompare() {
        return lastName.isEmpty() ? firstName : lastName;
    }

    @Override
    public String toString() {
        return lastName.isEmpty() ? firstName : firstName + ' ' + lastName;
    }
}
