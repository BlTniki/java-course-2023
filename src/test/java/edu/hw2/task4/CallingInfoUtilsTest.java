package edu.hw2.task4;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CallingInfoUtilsTest {

    @Test
    void callingInfo() {
        // given
        String expectedClassName = "edu.hw2.task4.CallingInfoUtilsTest";
        String expectedMethodName = "callingInfo";

        // when
        CallingInfo actualCallingInfo = CallingInfoUtils.callingInfo();

        // then
        assertThat(actualCallingInfo.className())
            .isEqualTo(expectedClassName);
        assertThat(actualCallingInfo.methodName())
            .isEqualTo(expectedMethodName);
    }
}
