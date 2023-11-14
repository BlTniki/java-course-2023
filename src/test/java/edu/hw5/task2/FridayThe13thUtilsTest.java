package edu.hw5.task2;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class FridayThe13thUtilsTest {
    @Test
    @DisplayName("Поиск пятниц 13-го в 2023 году должен возвращать корректные даты")
    void findFridaysThe13th_shouldReturnCorrectDatesForYear2023() {
        // given
        int year = 2023;
        List<LocalDate> expected = List.of(
            LocalDate.of(2023, 1, 13),
            LocalDate.of(2023, 10, 13)
        );

        // when
        List<LocalDate> actual = FridayThe13thUtils.findFridaysThe13th(year);

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("Поиск следующей пятницы 13-го после заданной даты должен возвращать корректную дату")
    void findNextFridayThe13th_shouldReturnCorrectDateAfterGivenDate() {
        // given
        LocalDate inputDate = LocalDate.of(2023, 11, 1);
        LocalDate expected = LocalDate.of(2024, 9, 13);

        LocalDate actual = FridayThe13thUtils.findNextFridayThe13th(inputDate);

        assertThat(actual).isEqualTo(expected);
    }
}
