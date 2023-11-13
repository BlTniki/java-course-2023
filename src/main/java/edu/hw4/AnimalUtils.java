package edu.hw4;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AnimalUtils {
    private AnimalUtils() {
    }

    /*
    task 1
     */
    public static List<Animal> sortAnimalsByHeightASC(@NotNull Collection<Animal> animals) {
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::height))
            .toList();
    }

    /*
    task 2
     */
    public static List<Animal> sortAnimalsByWeightDESC(@NotNull Collection<Animal> animals) {
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::weight).reversed())
            .toList();
    }

    /*
    task 3
     */
    public static Map<Animal.Type, Long> countAnimalsByType(@NotNull Collection<Animal> animals) {
        return animals.stream()
            .collect(Collectors.groupingBy(Animal::type, Collectors.counting()));
    }

    /*
    task 4
     */
    public static Animal findAnimalWithLongestName(@NotNull Collection<Animal> animals) {
        return animals.stream()
            .max(Comparator.comparingInt(a -> a.name().length()))
            .orElse(null);
    }

    /*
    task 5
     */
    public static Animal.Sex findMostFrequentSex(@NotNull Collection<Animal> animals) {
        return animals.stream()
            .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }

    /*
    task 6
     */
    public static Map<Animal.Type, Animal> findHeaviesAnimalOfType(@NotNull Collection<Animal> animals) {
        return animals.stream()
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.collectingAndThen(
                    Collectors.maxBy(Comparator.comparingInt(Animal::weight)),
                    a -> a.orElse(null)
                )
            ));
    }

    /*
    task 7
     */
    public static @Nullable Animal kMostOldestAnimal(@NotNull Collection<Animal> animals, int k) {
        if (k < 1) {
            return null;
        }
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::age).reversed())
            .skip(k - 1)
            .findFirst()
            .orElse(null);
    }

    /*
    task 8
     */
    public static @NotNull Optional<Animal> mostHeaviestAnimalThatKcmHeight(
        @NotNull Collection<Animal> animals, int k
    ) {
        return animals.stream()
            .filter(animal -> animal.height() < k)
            .max(Comparator.comparingInt(Animal::weight));
    }

    /*
    task 9
     */
    public static int countLegsOfAllAnimals(@NotNull Collection<Animal> animals) {
        return animals.stream()
            .mapToInt(Animal::paws)
            .sum();
    }

    /*
    task 10
     */
    public static List<Animal> findAnimalsWithMismatchedAgeAndPaws(@NotNull Collection<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.paws() != animal.age())
            .collect(Collectors.toList());
    }

    /*
    task 11
     */
    public static List<Animal> findAnimalsThatBitesAndHigherThanKcm(@NotNull Collection<Animal> animals, int k) {
        return animals.stream()
            .filter(animal -> animal.bites() && animal.height() > k)
            .collect(Collectors.toList());
    }

    /*
    task 12
     */
    public static @NotNull Long countAnimalsWhoseWeightExceedsHeight(@NotNull Collection<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.weight() > animal.height())
            .count();
    }

    /*
    task 13
     */
    public static List<Animal> animalsWithMultiWordNames(@NotNull List<Animal> animals) {
        return animals.stream()
            .filter(animal -> {
                String[] words = animal.name().split(" ");
                return words.length > 2;
            })
            .collect(Collectors.toList());
    }

    /*
    task 14
     */
    public static boolean hasTallDog(@NotNull List<Animal> animals, int k) {
        return animals.stream()
            .anyMatch(animal -> animal.type() == Animal.Type.DOG && animal.height() > k);
    }

    /*
    task 15
     */
    public static @NotNull Integer calculateTotalWeightByAnimalsThatAgeBetween(
        List<Animal> animals, int k, int l) {
        return animals.stream()
            .filter(animal -> animal.age() >= k && animal.age() <= l)
            .mapToInt(Animal::weight)
            .sum();
    }

    /*
    task 16
     */
    public static List<Animal> sortAnimalsByTypeAscBySexAscByNameAsc(@NotNull List<Animal> animals) {
        return animals.stream()
            .sorted(Comparator.comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name)
            ).toList();
    }

    /*
    task 17
     */
    public static boolean spidersBiteMoreThatDogs(@NotNull Collection<Animal> animals) {
        final int animalsEnoughForAnswer = 1;

        var typeCount = countAnimalsByType(animals);

        long spiderBites = animals.stream()
            .filter(animal -> animal.type() == Animal.Type.SPIDER && animal.bites())
            .count();

        long dogBites = animals.stream()
            .filter(animal -> animal.type() == Animal.Type.DOG && animal.bites())
            .count();

        return typeCount.containsKey(Animal.Type.SPIDER) && typeCount.get(Animal.Type.SPIDER) > animalsEnoughForAnswer
            && typeCount.containsKey(Animal.Type.DOG) && typeCount.get(Animal.Type.DOG) > animalsEnoughForAnswer
    && (double) spiderBites / typeCount.get(Animal.Type.SPIDER) > (double) dogBites / typeCount.get(Animal.Type.DOG);
    }

    /*
    task 18
     */
    public static Animal theMostHeavierFishInAllCollections(@NotNull Collection<Collection<Animal>> animals) {
        return animals.stream()
            .flatMap(Collection::stream)
            .filter(animal -> animal.type().equals(Animal.Type.FISH))
            .max(Comparator.comparingInt(Animal::weight))
            .orElse(null);
    }


    /*
    task 19
     */
    public record ValidationError(String message) {
    }

    @SuppressWarnings("checkstyle:InnerTypeLast")
    public static @NotNull Map<String, Set<ValidationError>> findInvalidAnimals(@NotNull Collection<Animal> animals) {
        Map<String, Set<ValidationError>> invalidAnimals = new HashMap<>();

        for (Animal animal : animals) {
            Set<ValidationError> errors = new HashSet<>();

            if (animal.type() == null) {
                errors.add(new ValidationError("Invalid animal type"));
            }

            if (animal.age() < 0) {
                errors.add(new ValidationError("Invalid age"));
            }

            if (animal.sex() == null) {
                errors.add(new ValidationError("Invalid sex"));
            }

            if (!errors.isEmpty()) {
                invalidAnimals.put(animal.name(), errors);
            }
        }

        return invalidAnimals;
    }

    /*
    task 20
     */
    @SuppressWarnings("checkstyle:InnerTypeLast")
    public static Map<String, String> findInvalidAnimalsToString(@NotNull Collection<Animal> animals) {
        return findInvalidAnimals(animals).entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().stream()
                    .map(ValidationError::message)
                    .collect(Collectors.joining("; "))
            ));
    }
}
