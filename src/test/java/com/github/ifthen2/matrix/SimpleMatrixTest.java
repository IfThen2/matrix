package com.github.ifthen2.matrix;


import com.github.ifthen2.matrix.element.MatrixElement;
import com.github.ifthen2.matrix.element.SimpleMatrixElement;
import com.github.ifthen2.matrix.value.MatrixBooleanValue;
import com.github.ifthen2.matrix.value.MatrixNumberValue;
import com.github.ifthen2.matrix.value.MatrixValue;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Unit Tests
 */
public class SimpleMatrixTest {

    static final Logger LOGGER = LoggerFactory.getLogger(SimpleMatrixTest.class);

    @ParameterizedTest
    @MethodSource("TestMatrixAddition")
    <T extends MatrixValue<T>> void testMatrixAddition(Matrix<T> subject, Matrix<T> operand,
        Matrix<T> expected) {

        Matrix<T> summedMatrix = subject.addMatrix(operand);

        LOGGER.info("subject -> {}", subject);
        LOGGER.info("operand -> {}", operand);
        LOGGER.info("sum     -> {}", summedMatrix);

        Assertions.assertEquals(expected, summedMatrix);
    }

    @ParameterizedTest
    @MethodSource("TestMatrixMultiplication")
    public <T extends MatrixValue<T>> void testMatrixComposeOperation(Matrix<T> subject,
        Matrix<T> operand, Matrix<T> expected) {

        Matrix<T> composedMatrix = subject.multiply(operand);

        LOGGER.info("{}", subject);
        LOGGER.info("{}", operand);
        LOGGER.info("{}", composedMatrix);

        Assertions.assertEquals(expected, composedMatrix);
    }


    /**
     * JUnit 5 Method Argument Factory for tests of Matrix Addition.
     */
    private static Stream<Arguments> TestMatrixAddition() {
        return Stream.of(
            Arguments.of(getNumericSubject(), getNumericOperand(),
                getNumericAdditionResult()),
            Arguments.of(getBooleanSubject(), getBooleanOperand(),
                getBooleanAdditionResult()),
            Arguments.of(getMatrixSubject(), getMatrixOperand(), getMatrixAdditionResult())
        );
    }

    /**
     * JUnit 5 Method Argument Factory for tests of Matrix Multiplication.
     */
    private static Stream<Arguments> TestMatrixMultiplication() {
        return Stream.of(
            Arguments.of(getNumericSubject(), getNumericOperand(),
                getNumericMultiplicationResult()),
            Arguments.of(getBooleanSubject(), getBooleanOperand(),
                getBooleanMultiplicationResult()),
            Arguments.of(getMatrixSubject(), getMatrixOperand(), getMatrixMultiplicationResult())
        );
    }

    private static Matrix<MatrixNumberValue> getNumericSubject() {

        Set<MatrixElement<MatrixNumberValue>> set = new HashSet<>();
        Set<MatrixElement<Matrix<Matrix<Matrix<Matrix<MatrixNumberValue>>>>>> inceptionSet = new HashSet<>();
        Matrix<Matrix<Matrix<Matrix<Matrix<MatrixNumberValue>>>>> inceptionMatrix = new SimpleMatrix<>(
            inceptionSet);

        set.add(new SimpleMatrixElement<>(1, 1, new MatrixNumberValue(1.0d)));
        set.add(new SimpleMatrixElement<>(1, 2, new MatrixNumberValue(2.0d)));
        set.add(new SimpleMatrixElement<>(2, 1, new MatrixNumberValue(3.0d)));
        set.add(new SimpleMatrixElement<>(2, 2, new MatrixNumberValue(4.0d)));

        return new SimpleMatrix<>(set);
    }

    private static Matrix<MatrixBooleanValue> getBooleanSubject() {

        Set<MatrixElement<MatrixBooleanValue>> set = new HashSet<>();

        set.add(new SimpleMatrixElement<>(1, 1, new MatrixBooleanValue(true)));
        set.add(new SimpleMatrixElement<>(1, 2, new MatrixBooleanValue(false)));
        set.add(new SimpleMatrixElement<>(2, 1, new MatrixBooleanValue(false)));
        set.add(new SimpleMatrixElement<>(2, 2, new MatrixBooleanValue(true)));

        return new SimpleMatrix<>(set);
    }

    private static Matrix<Matrix<MatrixNumberValue>> getMatrixSubject() {

        Set<MatrixElement<Matrix<MatrixNumberValue>>> set = new HashSet<>();

        set.add(new SimpleMatrixElement<>(1, 1, getNumericSubject()));
        set.add(new SimpleMatrixElement<>(1, 2, getNumericSubject()));
        set.add(new SimpleMatrixElement<>(2, 1, getNumericSubject()));
        set.add(new SimpleMatrixElement<>(2, 2, getNumericSubject()));

        return new SimpleMatrix<>(set);
    }

    private static Matrix<MatrixNumberValue> getNumericOperand() {

        Set<MatrixElement<MatrixNumberValue>> set = new HashSet<>();

        set.add(new SimpleMatrixElement<>(1, 1, new MatrixNumberValue(5.0d)));
        set.add(new SimpleMatrixElement<>(1, 2, new MatrixNumberValue(6.0d)));
        set.add(new SimpleMatrixElement<>(2, 1, new MatrixNumberValue(7.0d)));
        set.add(new SimpleMatrixElement<>(2, 2, new MatrixNumberValue(8.0d)));

        return new SimpleMatrix<>(set);
    }

    private static Matrix<MatrixBooleanValue> getBooleanOperand() {

        Set<MatrixElement<MatrixBooleanValue>> set = new HashSet<>();

        set.add(new SimpleMatrixElement<>(1, 1, new MatrixBooleanValue(false)));
        set.add(new SimpleMatrixElement<>(1, 2, new MatrixBooleanValue(true)));
        set.add(new SimpleMatrixElement<>(2, 1, new MatrixBooleanValue(false)));
        set.add(new SimpleMatrixElement<>(2, 2, new MatrixBooleanValue(false)));

        return new SimpleMatrix<>(set);
    }

    private static Matrix<Matrix<MatrixNumberValue>> getMatrixOperand() {

        Set<MatrixElement<Matrix<MatrixNumberValue>>> set = new HashSet<>();

        set.add(new SimpleMatrixElement<>(1, 1, getNumericOperand()));
        set.add(new SimpleMatrixElement<>(1, 2, getNumericOperand()));
        set.add(new SimpleMatrixElement<>(2, 1, getNumericOperand()));
        set.add(new SimpleMatrixElement<>(2, 2, getNumericOperand()));

        return new SimpleMatrix<>(set);
    }

    private static Matrix<MatrixNumberValue> getNumericAdditionResult() {

        Set<MatrixElement<MatrixNumberValue>> set = new HashSet<>();

        set.add(new SimpleMatrixElement<>(1, 1, new MatrixNumberValue(6.0d)));
        set.add(new SimpleMatrixElement<>(1, 2, new MatrixNumberValue(8.0d)));
        set.add(new SimpleMatrixElement<>(2, 1, new MatrixNumberValue(10.0d)));
        set.add(new SimpleMatrixElement<>(2, 2, new MatrixNumberValue(12.0d)));

        return new SimpleMatrix<>(set);
    }

    private static Matrix<MatrixBooleanValue> getBooleanAdditionResult() {

        Set<MatrixElement<MatrixBooleanValue>> set = new HashSet<>();

        set.add(new SimpleMatrixElement<>(1, 1, new MatrixBooleanValue(true)));
        set.add(new SimpleMatrixElement<>(1, 2, new MatrixBooleanValue(true)));
        set.add(new SimpleMatrixElement<>(2, 1, new MatrixBooleanValue(false)));
        set.add(new SimpleMatrixElement<>(2, 2, new MatrixBooleanValue(true)));

        return new SimpleMatrix<>(set);
    }

    private static Matrix<Matrix<MatrixNumberValue>> getMatrixAdditionResult() {

        Set<MatrixElement<Matrix<MatrixNumberValue>>> set = new HashSet<>();

        set.add(new SimpleMatrixElement<>(1, 1, getNumericAdditionResult()));
        set.add(new SimpleMatrixElement<>(1, 2, getNumericAdditionResult()));
        set.add(new SimpleMatrixElement<>(2, 1, getNumericAdditionResult()));
        set.add(new SimpleMatrixElement<>(2, 2, getNumericAdditionResult()));

        return new SimpleMatrix<>(set);
    }

    private static Matrix<MatrixNumberValue> getNumericMultiplicationResult() {

        Set<MatrixElement<MatrixNumberValue>> set = new HashSet<>();

        set.add(new SimpleMatrixElement<>(1, 1, new MatrixNumberValue(19.0d)));
        set.add(new SimpleMatrixElement<>(1, 2, new MatrixNumberValue(22.0d)));
        set.add(new SimpleMatrixElement<>(2, 1, new MatrixNumberValue(43.0d)));
        set.add(new SimpleMatrixElement<>(2, 2, new MatrixNumberValue(50.0d)));

        return new SimpleMatrix<>(set);
    }

    private static Matrix<MatrixBooleanValue> getBooleanMultiplicationResult() {

        Set<MatrixElement<MatrixBooleanValue>> set = new HashSet<>();

        set.add(new SimpleMatrixElement<>(1, 1, new MatrixBooleanValue(false)));
        set.add(new SimpleMatrixElement<>(1, 2, new MatrixBooleanValue(true)));
        set.add(new SimpleMatrixElement<>(2, 1, new MatrixBooleanValue(false)));
        set.add(new SimpleMatrixElement<>(2, 2, new MatrixBooleanValue(false)));

        return new SimpleMatrix<>(set);
    }

    private static Matrix<Matrix<MatrixNumberValue>> getMatrixMultiplicationResult() {

        Set<MatrixElement<Matrix<MatrixNumberValue>>> set = new HashSet<>();

        set.add(new SimpleMatrixElement<>(1, 2, getNumericMultiplicationResult()));
        set.add(new SimpleMatrixElement<>(1, 2, getNumericMultiplicationResult()));
        set.add(new SimpleMatrixElement<>(2, 1, getNumericMultiplicationResult()));
        set.add(new SimpleMatrixElement<>(2, 2, getNumericMultiplicationResult()));

        return new SimpleMatrix<>(set);
    }
}
