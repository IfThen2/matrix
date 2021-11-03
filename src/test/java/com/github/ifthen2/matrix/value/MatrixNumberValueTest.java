package com.github.ifthen2.matrix.value;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MatrixNumberValueTest {

    static final MatrixNumberValue SUBJECT = new MatrixNumberValue(2.0d);

    @ParameterizedTest
    @MethodSource("getAdditionTestArgs")
    void testSimpleAddition(MatrixNumberValue subject, MatrixNumberValue operand,
        MatrixNumberValue expected) {

        MatrixNumberValue result = subject.add(operand);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("getMultiplicationTestArgs")
    void testSimpleMultiplication(MatrixNumberValue subject, MatrixNumberValue operand,
        MatrixNumberValue expected) {

        MatrixNumberValue result = subject.multiply(operand);

        assertEquals(expected, result);
    }

    /**
     * JUnit 5 Method Argument Factory for numeric addition tests
     */
    static Stream<Arguments> getAdditionTestArgs() {
        return Stream.of(
            Arguments.of(SUBJECT, new MatrixNumberValue(3.0d), new MatrixNumberValue(5.0d)),
            Arguments.of(SUBJECT, new MatrixNumberValue(4.0d), new MatrixNumberValue(6.0d)),
            Arguments.of(SUBJECT, new MatrixNumberValue(5.0d), new MatrixNumberValue(7.0d))
        );
    }

    /**
     * JUnit 5 Method Argument Factory numeric multiplication tests
     */
    static Stream<Arguments> getMultiplicationTestArgs() {
        return Stream.of(
            Arguments.of(SUBJECT, new MatrixNumberValue(3.0d), new MatrixNumberValue(6.0d)),
            Arguments.of(SUBJECT, new MatrixNumberValue(4.0d), new MatrixNumberValue(8.0d)),
            Arguments.of(SUBJECT, new MatrixNumberValue(5.0d), new MatrixNumberValue(10.0d))
        );
    }
}
