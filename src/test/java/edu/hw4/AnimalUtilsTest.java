package edu.hw4;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AnimalUtilsTest {
    @Test
    void sortAnimalsByHeightASC() {
        Animal cat = new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 2, 30, 5, true);
        Animal dog = new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 3, 40, 8, true);
        Animal bird = new Animal("Bird", Animal.Type.BIRD, Animal.Sex.F, 1, 10, 2, false);

        List<Animal> animals = List.of(cat, dog, bird);

        List<Animal> sortedAnimals = AnimalUtils.sortAnimalsByHeightASC(animals);

        assertThat(sortedAnimals)
            .extracting(Animal::height)
            .containsExactly(10, 30, 40);
    }

    @Test
    void sortAnimalsByWeightDESC() {
        Animal cat = new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 2, 30, 5, true);
        Animal dog = new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 3, 40, 8, true);
        Animal bird = new Animal("Bird", Animal.Type.BIRD, Animal.Sex.F, 1, 10, 2, false);

        List<Animal> animals = List.of(cat, dog, bird);

        List<Animal> sortedAnimals = AnimalUtils.sortAnimalsByWeightDESC(animals);

        assertThat(sortedAnimals)
            .extracting(Animal::weight)
            .containsExactly(8, 5, 2);
    }

    @Test
    void countAnimalsByType() {
        Animal cat = new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 2, 30, 5, true);
        Animal dog = new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 3, 40, 8, true);
        Animal bird = new Animal("Bird", Animal.Type.BIRD, Animal.Sex.F, 1, 10, 2, false);

        List<Animal> animals = List.of(cat, dog, bird);

        Map<Animal.Type, Long> countByType = AnimalUtils.countAnimalsByType(animals);

        assertThat(countByType)
            .containsEntry(Animal.Type.CAT, 1L)
            .containsEntry(Animal.Type.DOG, 1L)
            .containsEntry(Animal.Type.BIRD, 1L);
    }

    @Test
    void findAnimalWithLongestName() {
        Animal cat = new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 2, 30, 5, true);
        Animal dog = new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 3, 40, 8, true);
        Animal bird = new Animal("Bird", Animal.Type.BIRD, Animal.Sex.F, 1, 10, 2, false);

        List<Animal> animals = List.of(cat, dog, bird);

        Animal animalWithLongestName = AnimalUtils.findAnimalWithLongestName(animals);

        assertThat(animalWithLongestName.type())
            .isEqualTo(Animal.Type.BIRD);
    }

    @Test
    void findMostFrequentSex() {
        Animal cat1 = new Animal("Cat1", Animal.Type.CAT, Animal.Sex.M, 2, 30, 5, true);
        Animal cat2 = new Animal("Cat2", Animal.Type.CAT, Animal.Sex.M, 3, 25, 6, true);
        Animal dog = new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 3, 40, 8, true);
        Animal bird = new Animal("Bird", Animal.Type.BIRD, Animal.Sex.F, 1, 10, 2, false);
        Animal bird2 = new Animal("Bird", Animal.Type.BIRD, Animal.Sex.F, 1, 10, 2, false);

        List<Animal> animals = List.of(cat1, cat2, dog, bird, bird2);

        Animal.Sex mostFrequentSex = AnimalUtils.findMostFrequentSex(animals);

        assertThat(mostFrequentSex).isEqualTo(Animal.Sex.F);
    }

    @Test
    void findHeaviesAnimalOfType() {
        Animal cat1 = new Animal("Cat1", Animal.Type.CAT, Animal.Sex.M, 2, 30, 5, true);
        Animal cat2 = new Animal("Cat2", Animal.Type.CAT, Animal.Sex.F, 3, 25, 6, true);
        Animal dog = new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 3, 40, 8, true);
        Animal dog2 = new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 3, 40, 8, true);
        Animal bird = new Animal("Bird", Animal.Type.BIRD, Animal.Sex.F, 1, 10, 2, false);

        List<Animal> animals = List.of(cat1, cat2, dog, dog2, bird);

        Map<Animal.Type, Animal> heaviestAnimals = AnimalUtils.findHeaviesAnimalOfType(animals);

        assertThat(heaviestAnimals)
            .containsEntry(Animal.Type.CAT, cat2)
            .containsEntry(Animal.Type.DOG, dog2)
            .containsEntry(Animal.Type.BIRD, bird);
    }

    @Test
    void kMostOldestAnimal_valid() {
        Animal cat1 = new Animal("Cat1", Animal.Type.CAT, Animal.Sex.M, 2, 30, 5, true);
        Animal cat2 = new Animal("Cat2", Animal.Type.CAT, Animal.Sex.F, 5, 25, 6, true);
        Animal dog = new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 7, 40, 8, true);
        Animal bird = new Animal("Bird", Animal.Type.BIRD, Animal.Sex.F, 3, 10, 2, false);

        List<Animal> animals = List.of(cat1, cat2, dog, bird);

        Animal oldestAnimal = AnimalUtils.kMostOldestAnimal(animals, 2);

        assert oldestAnimal != null;
        assertThat(oldestAnimal.age())
            .isEqualTo(5);
    }

    @Test
    void kMostOldestAnimal_invalid() {
        Animal cat1 = new Animal("Cat1", Animal.Type.CAT, Animal.Sex.M, 2, 30, 5, true);
        Animal cat2 = new Animal("Cat2", Animal.Type.CAT, Animal.Sex.F, 5, 25, 6, true);
        Animal dog = new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 7, 40, 8, true);
        Animal bird = new Animal("Bird", Animal.Type.BIRD, Animal.Sex.F, 3, 10, 2, false);

        List<Animal> animals = List.of(cat1, cat2, dog, bird);

        Animal oldestAnimal1 = AnimalUtils.kMostOldestAnimal(animals, -1);
        Animal oldestAnimal2 = AnimalUtils.kMostOldestAnimal(animals, 10000);

        assertThat(oldestAnimal1).isNull();
        assertThat(oldestAnimal2).isNull();
    }

    @Test
    void mostHeaviestAnimalThatKcmHeight() {
        Animal cat1 = new Animal("Cat1", Animal.Type.CAT, Animal.Sex.M, 2, 30, 5, true);
        Animal cat2 = new Animal("Cat2", Animal.Type.CAT, Animal.Sex.F, 5, 25, 6, true);
        Animal dog = new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 7, 40, 8, true);
        Animal bird = new Animal("Bird", Animal.Type.BIRD, Animal.Sex.F, 3, 10, 2, false);

        List<Animal> animals = List.of(cat1, cat2, dog, bird);

        Optional<Animal> heaviestAnimal = AnimalUtils.mostHeaviestAnimalThatKcmHeight(animals, 28);

        assertThat(heaviestAnimal)
            .isPresent()
            .hasValueSatisfying(animal -> assertThat(animal.weight()).isEqualTo(6));
    }

    @Test
    void countLegsOfAllAnimals_valid() {
        Animal cat1 = new Animal("Cat1", Animal.Type.CAT, Animal.Sex.M, 2, 30, 5, true);
        Animal cat2 = new Animal("Cat2", Animal.Type.CAT, Animal.Sex.F, 5, 25, 6, true);
        Animal dog = new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 7, 40, 8, true);
        Animal bird = new Animal("Bird", Animal.Type.BIRD, Animal.Sex.F, 3, 10, 2, false);

        List<Animal> animals = List.of(cat1, cat2, dog, bird);

        int totalLegs = AnimalUtils.countLegsOfAllAnimals(animals);

        assertThat(totalLegs).isEqualTo(14);
    }

    @Test
    void countLegsOfAllAnimals_invalid() {
        List<Animal> animals = List.of();

        int totalLegs = AnimalUtils.countLegsOfAllAnimals(animals);

        assertThat(totalLegs).isEqualTo(0);
    }

    @Test
    public void testFindAnimalsWithMismatchedAgeAndPaws() {
        Animal cat = new Animal("Whiskers", Animal.Type.CAT, Animal.Sex.M, 3, 4, 5, false);
        Animal dog = new Animal("Fido", Animal.Type.DOG, Animal.Sex.M, 4, 4, 6, true);
        List<Animal> animals = List.of(cat, dog);

        List<Animal> result = AnimalUtils.findAnimalsWithMismatchedAgeAndPaws(animals);

        assertThat(result).containsExactly(cat);
    }

    @Test
    public void testFindAnimalsThatBitesAndHigherThan100cm() {
        Animal dog = new Animal("Fido", Animal.Type.DOG, Animal.Sex.M, 5, 120, 6, true);
        Animal cat = new Animal("Whiskers", Animal.Type.CAT, Animal.Sex.M, 3, 90, 5, false);
        Animal cat2 = new Animal("Whiskers", Animal.Type.CAT, Animal.Sex.M, 3, 150, 5, false);
        List<Animal> animals = List.of(dog, cat, cat2);

        List<Animal> result = AnimalUtils.findAnimalsThatBitesAndHigherThanKcm(animals, 100);

        assertThat(result).containsExactly(dog);
    }

    @Test
    public void testCountAnimalsWhoseWeightExceedsHeight() {
        Animal cat = new Animal("Whiskers", Animal.Type.CAT, Animal.Sex.M, 3, 90, 5, false);
        Animal dog = new Animal("Fido", Animal.Type.DOG, Animal.Sex.M, 5, 120, 6, true);
        Animal dog2 = new Animal("Fido", Animal.Type.DOG, Animal.Sex.F, 5, 1, 6, true);
        Animal dog3 = new Animal("Fido", Animal.Type.DOG, Animal.Sex.M, 5, 2, 6, true);
        List<Animal> animals = List.of(cat, dog, dog2, dog3);

        Long result = AnimalUtils.countAnimalsWhoseWeightExceedsHeight(animals);

        assertThat(result).isEqualTo(2L);
    }

    @Test
    public void testAnimalsWithMultiWordNames() {
        Animal animal1 = new Animal("Fluffy Cat", Animal.Type.CAT, Animal.Sex.M, 3, 90, 5, false);
        Animal animal2 = new Animal("Big Brown Dog", Animal.Type.DOG, Animal.Sex.M, 5, 120, 6, true);
        List<Animal> animals = List.of(animal1, animal2);

        List<Animal> result = AnimalUtils.animalsWithMultiWordNames(animals);

        assertThat(result).containsExactly(animal2);
    }

    @Test
    public void testHasTallDog() {
        Animal cat = new Animal("Whiskers", Animal.Type.CAT, Animal.Sex.M, 3, 90, 5, false);
        Animal dog = new Animal("Fido", Animal.Type.DOG, Animal.Sex.M, 5, 120, 6, true);
        List<Animal> animals = List.of(cat, dog);

        boolean result = AnimalUtils.hasTallDog(animals, 100);

        assertThat(result).isTrue();
    }

    @Test
    public void testCalculateTotalWeightByAnimalsThatAgeBetween() {
        Animal cat = new Animal("Whiskers", Animal.Type.CAT, Animal.Sex.M, 3, 90, 5, false);
        Animal dog = new Animal("Fido", Animal.Type.DOG, Animal.Sex.M, 5, 120, 6, true);
        Animal dog2 = new Animal("Fido", Animal.Type.DOG, Animal.Sex.M, 6, 120, 6, true);
        List<Animal> animals = List.of(cat, dog, dog2);

        Integer result = AnimalUtils.calculateTotalWeightByAnimalsThatAgeBetween(animals, 3, 5);

        assertThat(result).isEqualTo(11);
    }

    @Test
    void sortAnimalsByTypeAscBySexAscByNameAsc() {
        Animal cat1 = new Animal("Cat1", Animal.Type.CAT, Animal.Sex.M, 2, 25, 10, true);
        Animal cat2 = new Animal("Cat2", Animal.Type.CAT, Animal.Sex.F, 3, 22, 9, true);
        Animal dog1 = new Animal("Dog1", Animal.Type.DOG, Animal.Sex.M, 4, 30, 15, true);

        List<Animal> animals = List.of(cat1, cat2, dog1);
        List<Animal> sortedAnimals = AnimalUtils.sortAnimalsByTypeAscBySexAscByNameAsc(animals);

        assertThat(sortedAnimals).containsExactly(cat1, cat2, dog1);
    }

    @Test
    void spidersBiteMoreThatDogs() {
        Animal spider = new Animal("Spider1", Animal.Type.SPIDER, Animal.Sex.M, 1, 5, 1, true);
        Animal spider2 = new Animal("Spider1", Animal.Type.SPIDER, Animal.Sex.M, 1, 5, 1, false);
        Animal dog = new Animal("Dog1", Animal.Type.DOG, Animal.Sex.F, 3, 30, 15, true);
        Animal dog2 = new Animal("Dog2", Animal.Type.DOG, Animal.Sex.M, 5, 35, 18, false);
        Animal dog3 = new Animal("Dog2", Animal.Type.DOG, Animal.Sex.M, 5, 35, 18, false);

        List<Animal> animals = List.of(spider, spider2, dog, dog2, dog3);
        boolean result = AnimalUtils.spidersBiteMoreThatDogs(animals);

        assertThat(result).isTrue();
    }

    @Test
    void theMostHeavierFishInAllCollections() {
        Animal fish1 = new Animal("Fish1", Animal.Type.FISH, Animal.Sex.M, 2, 10, 3, true);
        Animal fish2 = new Animal("Fish2", Animal.Type.FISH, Animal.Sex.F, 1, 12, 6, true);
        Animal fish3 = new Animal("Fish3", Animal.Type.FISH, Animal.Sex.M, 1, 12, 4, true);
        Animal fish4 = new Animal("Fish4", Animal.Type.FISH, Animal.Sex.F, 1, 12, 5, true);

        List<Animal> collection1 = List.of(fish1, fish2);
        List<Animal> collection2 = List.of(fish3, fish4);

        Animal result = AnimalUtils.theMostHeavierFishInAllCollections(List.of(collection1, collection2));

        assertThat(result).isEqualTo(fish2);
    }

    @Test
    void findInvalidAnimals() {
        Animal validAnimal = new Animal("ValidAnimal", Animal.Type.CAT, Animal.Sex.F, 2, 20, 8, true);
        Animal invalidType = new Animal("InvalidTypeAnimal", null, Animal.Sex.F, 2, 20, 8, true);
        Animal negativeAge = new Animal("NegativeAgeAnimal", Animal.Type.DOG, Animal.Sex.M, -1, 30, 15, true);
        Animal invalidSex = new Animal("InvalidSexAnimal", Animal.Type.DOG, null, 4, 30, 15, true);

        List<Animal> animals = List.of(validAnimal, invalidType, negativeAge, invalidSex);

        Map<String, Set<AnimalUtils.ValidationError>> result = AnimalUtils.findInvalidAnimals(animals);

        assertThat(result).containsKeys("InvalidTypeAnimal", "NegativeAgeAnimal", "InvalidSexAnimal");
        assertThat(result.get("InvalidTypeAnimal")).containsExactly(new AnimalUtils.ValidationError("Invalid animal type"));
        assertThat(result.get("NegativeAgeAnimal")).containsExactly(new AnimalUtils.ValidationError("Invalid age"));
        assertThat(result.get("InvalidSexAnimal")).containsExactly(new AnimalUtils.ValidationError("Invalid sex"));
    }

    @Test
    void findInvalidAnimalsToString() {
        Animal validAnimal = new Animal("ValidAnimal", Animal.Type.CAT, Animal.Sex.F, 2, 20, 8, true);
        Animal invalidType = new Animal("InvalidTypeAnimal", null, Animal.Sex.F, 2, 20, 8, true);
        Animal negativeAge = new Animal("NegativeAgeAnimal", Animal.Type.DOG, Animal.Sex.M, -1, 30, 15, true);
        Animal invalidSex = new Animal("InvalidSexAnimal", Animal.Type.DOG, null, 4, 30, 15, true);

        List<Animal> animals = List.of(validAnimal, invalidType, negativeAge, invalidSex);

        Map<String, String> result = AnimalUtils.findInvalidAnimalsToString(animals);

        assertThat(result).containsKeys("InvalidTypeAnimal", "NegativeAgeAnimal", "InvalidSexAnimal");
        assertThat(result.get("InvalidTypeAnimal")).isEqualTo("Invalid animal type");
        assertThat(result.get("NegativeAgeAnimal")).isEqualTo("Invalid age");
        assertThat(result.get("InvalidSexAnimal")).isEqualTo("Invalid sex");
    }
}
