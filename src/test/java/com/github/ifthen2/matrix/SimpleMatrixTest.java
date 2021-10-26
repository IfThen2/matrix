package com.github.ifthen2.matrix;


import com.github.ifthen2.matrix.element.MatrixElement;
import com.github.ifthen2.matrix.element.SimpleMatrixElement;
import com.github.ifthen2.matrix.operation.matrix.MatrixOperation;
import com.github.ifthen2.matrix.operation.matrix.impl.MatrixAddOperation;
import com.github.ifthen2.matrix.operation.matrix.impl.MatrixComposeOperation;
import com.github.ifthen2.matrix.operation.matrix.impl.MatrixRowSwapOperation;
import com.github.ifthen2.matrix.operation.matrix.impl.MatrixScaleOperation;
import com.github.ifthen2.matrix.transform.MatrixTransformer;
import com.github.ifthen2.matrix.value.MatrixBooleanValue;
import com.github.ifthen2.matrix.value.MatrixNumberValue;
import com.github.ifthen2.matrix.value.MatrixValue;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
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

    static final double DOUBLE_DELTA = 1e-15;

    static final int NUM_ROWS = 2;
    static final int NUM_COLS = 2;

    @ParameterizedTest
    @MethodSource("TestMatrixScalarOperations")
    public <T extends MatrixValue<T>> void testMatrixScale(Matrix<T> matrix, T scalar) {
        MatrixOperation<T> scaleOperation = new MatrixScaleOperation<>(matrix,
            scalar);
        MatrixTransformer<T> transformer = new MatrixTransformer<>(scaleOperation);
        Matrix<T> scaledMatrix = transformer.performOperation();

        LOGGER.info("{}", matrix);
        LOGGER.info("{}", scaledMatrix);

        //TODO assertions
    }

    @ParameterizedTest
    @MethodSource("TestMatrixMatrixOperations")
    public <T extends MatrixValue<T>> void testMatrixAddition(Matrix<T> matrix, Matrix<T> matrix2) {
        MatrixOperation<T> addOperation = new MatrixAddOperation<>(matrix, matrix2);
        MatrixTransformer<T> transformer = new MatrixTransformer<>(addOperation);
        Matrix<T> summedMatrix = transformer.performOperation();

        LOGGER.info("{}", matrix);
        LOGGER.info("{}", summedMatrix);

        //TODO assertions
    }

    @ParameterizedTest
    @MethodSource("TestMatrixRowOperations")
    public <T extends MatrixValue<T>> void testMatrixRowSwapOperation(Matrix<T> matrix, int row1,
        int row2) {
        MatrixOperation<T> rowSwapOperation = new MatrixRowSwapOperation<>(matrix,
            row1, row2);
        MatrixTransformer<T> transformer = new MatrixTransformer<>(
            rowSwapOperation);
        Matrix<T> swappedMatrix = transformer.performOperation();

        LOGGER.info("{}", matrix);
        LOGGER.info("{}", swappedMatrix);

        //TODO assertions
    }

    @ParameterizedTest
    @MethodSource("TestMatrixMatrixOperations")
    public <T extends MatrixValue<T>> void testMatrixComposeOperation(Matrix<T> matrix,
        Matrix<T> matrix2) {
        MatrixOperation<T> composeOperation = new MatrixComposeOperation<>(matrix, matrix2);
        MatrixTransformer<T> transformer = new MatrixTransformer<>(
            composeOperation);
        Matrix<T> composedMatrix = transformer.performOperation();

        LOGGER.info("{}", matrix);
        LOGGER.info("{}", matrix2);
        LOGGER.info("{}", composedMatrix);

        //TODO assertions
    }
//

    /**
     * Provides a test Matrix whose elements numbers.
     */
    private static Matrix<MatrixNumberValue> getTestNumberMatrix() {

        Set<MatrixElement<MatrixNumberValue>> set = new HashSet<>();

        for (int x = 1; x <= NUM_ROWS; x++) {
            for (int y = 1; y <= NUM_COLS; y++) {
                set.add(new SimpleMatrixElement<>(x, y, new MatrixNumberValue(x + 1.0 * y + 1.0)));
            }
        }

        return new SimpleMatrix<>(set);
    }

    /**
     * Provides a test Matrix whose elements are boolean values;
     */
    private static Matrix<MatrixBooleanValue> getTestBooleanMatrix() {
        Set<MatrixElement<MatrixBooleanValue>> set = new HashSet<>();

        for (int x = 1; x <= NUM_ROWS; x++) {
            for (int y = 1; y <= NUM_COLS; y++) {
                set.add(new SimpleMatrixElement<>(x, y,
                    new MatrixBooleanValue(x % 2 == 0)));
            }
        }

        return new SimpleMatrix<>(set);
    }

    /**
     * Provides a test Matrix whose elements are Matrices whose elements are numbers.
     */
    private static Matrix<Matrix<MatrixNumberValue>> getTestNumberMatrixMatrix() {

        Set<MatrixElement<Matrix<MatrixNumberValue>>> set = new HashSet<>();

        for (int x = 1; x <= NUM_ROWS; x++) {
            for (int y = 1; y <= NUM_COLS; y++) {
                set.add(new SimpleMatrixElement<>(x, y,
                    getTestNumberMatrix()));
            }
        }

        return new SimpleMatrix<>(set);
    }

    /**
     * JUnit 5 Method Argument Factory for tests of Matrix on Matrix operations.
     */
    private static Stream<Arguments> TestMatrixMatrixOperations() {
        return Stream.of(
            Arguments.of(getTestNumberMatrix(), getTestNumberMatrix()),
            Arguments.of(getTestBooleanMatrix(), getTestBooleanMatrix()),
            Arguments.of(getTestNumberMatrixMatrix(), getTestNumberMatrixMatrix())
        );
    }

    /**
     * JUnit 5 Method Argument Factory for tests of Matrix Row operations.
     */
    private static Stream<Arguments> TestMatrixRowOperations() {
        return Stream.of(
            Arguments.of(getTestNumberMatrix(), 1, 2),
            Arguments.of(getTestBooleanMatrix(), 1, 2)
        );
    }

    /**
     * JUnit 5 Method Argument Factory for tests of Matrix scaling operations.
     */
    private static Stream<Arguments> TestMatrixScalarOperations() {
        return Stream.of(
            Arguments.of(getTestNumberMatrix(), new MatrixNumberValue(2.0)),
            Arguments.of(getTestBooleanMatrix(), new MatrixBooleanValue(true))
        );
    }
}
