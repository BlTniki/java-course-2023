package edu.hw3.task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class DictUtilsTest {
    public static Arguments[] valid_lists() {
        var lists = new ArrayList<Arguments>();

        var l1 = List.of("a", "bb", "a", "bb");
        var exp1 = new HashMap<String, Integer>();
        exp1.put("bb", 2);
        exp1.put("a", 2);
        lists.add(Arguments.of(l1, exp1));

        var l2 = List.of("this", "and", "that", "and");
        var exp2 = new HashMap<String, Integer>();
        exp2.put("that", 1);
        exp2.put("and", 2);
        exp2.put("this", 1);
        lists.add(Arguments.of(l2, exp2));

        var l3 = List.of("код", "код", "код", "bug");
        var exp3 = new HashMap<String, Integer>();
        exp3.put("код", 3);
        exp3.put("bug", 1);
        lists.add(Arguments.of(l3, exp3));

        var l4 = List.of(1, 1, 2, 2);
        var exp4 = new HashMap<Integer, Integer>();
        exp4.put(1, 2);
        exp4.put(2, 2);
        lists.add(Arguments.of(l4, exp4));

        return lists.toArray(new Arguments[0]);
    }

    @ParameterizedTest
    @MethodSource("valid_lists")
    @DisplayName("Проверяем на валидных")
    <T> void freqDict(List<T> list, Map<T, Integer> expectedMap) {
        // when
        var actualMap = DictUtils.freqDict(list);

        // then
        assertThat(actualMap)
            .containsExactlyInAnyOrderEntriesOf(expectedMap);
    }
}
