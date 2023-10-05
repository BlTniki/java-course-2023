package edu.hw1;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NestedArrayUtilsTest {

    @Test
    @DisplayName("Проверим нахождение минимума")
    void minOfArray() {
        // given
        int[] arr = {0, 2, 3, 40, -1, 10};
        int expectedInt = -1;

        // when
        int returnedInt = NestedArrayUtils.minOfArray(arr);

        // then
        assertEquals(returnedInt, expectedInt);
    }

    @Test
    @DisplayName("Проверим нахождение максимума")
    void maxOfArray() {
        // given
        int[] arr = {0, 2, 3, 40, -1, 10};
        int expectedInt = 40;

        // when
        int returnedInt = NestedArrayUtils.maxOfArray(arr);

        // then
        assertEquals(returnedInt, expectedInt);
    }

    record ArrayBundle(int[] a1, int[] a2, boolean expectedAnswer) {
    }

    @Test
    @DisplayName("Тестирование проверки на вложенность")
    void isNestable() {
        // given
        List<ArrayBundle> bundleList = List.of(
            new ArrayBundle(new int[]{1, 2, 3, 4}, new int[]{0, 6}, true),
            new ArrayBundle(new int[]{3, 1}, new int[]{4, 0}, true),
            new ArrayBundle(new int[]{9, 9, 8}, new int[]{8, 9}, false),
            new ArrayBundle(new int[]{1, 2, 3, 4}, new int[]{2, 3}, false)
        );

        for (ArrayBundle bundle : bundleList) {
            // when
            boolean returnedAnswer = NestedArrayUtils.isNestable(
                bundle.a1,
                bundle.a2
            );

            // then
            assertEquals(returnedAnswer, bundle.expectedAnswer);
        }
    }

    @Test
    @DisplayName("Проверка на нулл поинтеры")
    void checkNullPointer() {
        assertThatThrownBy(() -> NestedArrayUtils.minOfArray(null))
            .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> NestedArrayUtils.maxOfArray(null))
            .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> NestedArrayUtils.isNestable(null, null))
            .isInstanceOf(NullPointerException.class);
    }
}
