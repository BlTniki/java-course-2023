package edu.hw5.task5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class CarLicenseUtilsTest {
    static Arguments[] licenses() {
        return new Arguments[] {
            Arguments.of("А123ВЕ77", true),
            Arguments.of("А123ВЕ177", true),
            Arguments.of("1234АА77", true),
            Arguments.of("АО36578", true),
            Arguments.of("АН733147", true),
            Arguments.of("8776АЕ64", true),
            Arguments.of("СМ655К78", true),
            Arguments.of("123CD12377", true),
            Arguments.of("123D12377", true),
            Arguments.of("138М01277", true),
            Arguments.of("ТАО00278", true),
            Arguments.of("А123477", true),
            Arguments.of("123А77", true),
            Arguments.of("1234А77", true),
            Arguments.of("КАО00278", true),
            Arguments.of("САО00278", true),
            Arguments.of("123АВЕ777", false),
            Arguments.of("А123ВЕ7777", false),
            Arguments.of("А000ВЕ177", false),
            Arguments.of("", false),
            Arguments.of(null, false),
            Arguments.of("dawdaw", false)
        };
    }

    @ParameterizedTest
    @MethodSource("licenses")
    void isLicenseValid(String license, boolean expected) {
        assertThat(CarLicenseUtils.isLicenseValid(license))
            .isEqualTo(expected);
    }
}
