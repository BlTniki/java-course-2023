package edu.hw5.task1;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ComputerClubUtilsTest {
    static Arguments[] valid_sessions() {
        return new Arguments[] {
            Arguments.of("2022-03-12, 20:20 - 2022-03-12, 23:50", Duration.ofHours(3).plusMinutes(30)),
            Arguments.of("2022-04-01, 21:30 - 2022-04-02, 01:20", Duration.ofHours(3).plusMinutes(50))
        };
    }

    static Arguments[] invalid_sessions() {
        return new Arguments[] {
            Arguments.of(""),
            Arguments.of("dwadawd"),
            Arguments.of("2022-04-01, 21:30"),
            Arguments.of("2022-04-01, 21:30  2022-04-02, 01:20"),
            Arguments.of("2022-04-01, 21:30 - 2022-04-02, 01:1231220"),
            Arguments.of("20234234232-04-01, 21:30 - 2022-04-02, 01:20")
        };
    }

    @ParameterizedTest
    @MethodSource("valid_sessions")
    void calculateTotalDuration_valid(String session, Duration expected) {
        // when
        Duration actual = ComputerClubUtils.parseDuration(session);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("invalid_sessions")
    void calculateTotalDuration_invalid(String session) {
        assertThatThrownBy(() -> ComputerClubUtils.parseDuration(session))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void CalculateAvgDuration() {
        List<String> sessions = List.of(
            "2022-03-12, 20:20 - 2022-03-12, 23:50",
            "2022-04-01, 21:30 - 2022-04-02, 01:20"
        );

        Duration totalDuration = ComputerClubUtils.calculateAvgDuration(sessions);
        assertThat(totalDuration).isEqualTo(Duration.ofHours(3).plusMinutes(40));
    }

    @Test
    public void testCalculateTotalDurationWithEmptyList() {
        List<String> sessions = List.of();
        Duration totalDuration = ComputerClubUtils.calculateAvgDuration(sessions);
        assertThat(totalDuration).isEqualTo(Duration.ZERO);
    }

    @Test
    public void testCalculateTotalDurationWithSingleSession() {
        List<String> sessions = List.of("2022-03-12, 20:20 - 2022-03-12, 22:20");
        Duration totalDuration = ComputerClubUtils.calculateAvgDuration(sessions);
        assertThat(totalDuration).isEqualTo(Duration.ofHours(2));
    }
}
