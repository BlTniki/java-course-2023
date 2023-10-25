package edu.hw2.task4;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CallingInfoUtilsTest {

    @Test
    void callingInfo() {
        // when
        CallingInfo actualCallingInfo = CallingInfoUtils.callingInfo();

        // then
        assertThat(actualCallingInfo.className())
            .isEqualTo("edu.hw2.task4.CallingInfoUtilsTest");
        assertThat(actualCallingInfo.methodName())
            .isEqualTo("callingInfo");
    }
}
