package edu.hw3.task5;

import java.util.Comparator;

public enum Order {
    ASC(Comparator.comparing(Person::getValToCompare)),
    DESC(Comparator.comparing(Person::getValToCompare).reversed());
    public final Comparator<Person> comparator;

    Order(Comparator<Person> comparator) {
        this.comparator = comparator;
    }
}
