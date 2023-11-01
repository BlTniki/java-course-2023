package edu.hw3.task5;

import java.util.List;
import java.util.Optional;

public final class ContactsUtils {
    private ContactsUtils() {
    }

    /**
     * Sort contacts in alphabet order (ASC or DESC).
     * By default, contacts sorts by last name
     * but if contact has no last name first name will be used.
     * @param fcs FCs to sort
     * @param order ASC or DESC
     * @return sorted FCs
     */
    public static List<String> parseContacts(final List<String> fcs, Order order) {
        Optional<List<String>> optionalFcs = Optional.ofNullable(fcs);

        return optionalFcs.orElse(List.of())
            .stream()
            .map(Person::new)
            .sorted(order.comparator)
            .map(Person::toString)
            .toList();
    }

}
