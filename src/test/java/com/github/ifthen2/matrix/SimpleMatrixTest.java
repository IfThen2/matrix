package com.github.ifthen2.matrix;


import com.github.ifthen2.matrix.element.MatrixElement;
import com.github.ifthen2.matrix.element.SimpleMatrixElement;
import com.github.ifthen2.matrix.operation.matrix.MatrixOperation;
import com.github.ifthen2.matrix.operation.matrix.impl.MatrixAddOperation;
import com.github.ifthen2.matrix.operation.matrix.impl.MatrixComposeOperation;
import com.github.ifthen2.matrix.operation.matrix.impl.MatrixRowSwapOperation;
import com.github.ifthen2.matrix.operation.matrix.impl.MatrixScaleOperation;
import com.github.ifthen2.matrix.transform.MatrixTransformer;
import com.github.ifthen2.matrix.value.MatrixNumberValue;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Unit Tests
 */
public class SimpleMatrixTest {

    static final Logger LOGGER = LoggerFactory.getLogger(SimpleMatrixTest.class);

    static final double DOUBLE_DELTA = 1e-15;

    static final int NUM_ROWS = 10;
    static final int NUM_COLS = 10;

    Matrix<MatrixNumberValue> subject;

    /**
     * Fixture initialization (common initialization for all tests).
     **/
    @BeforeEach
    public void setUp() {
        subject = getTestMatrix();
    }

    @Test
    public void testMatrixScale() {
        MatrixOperation<MatrixNumberValue> scaleOperation = new MatrixScaleOperation<>(subject,
            new MatrixNumberValue(2.0d));
        MatrixTransformer<MatrixNumberValue> transformer = new MatrixTransformer<>(scaleOperation);
        Matrix<MatrixNumberValue> scaledMatrix = transformer.performOperation();

        LOGGER.info("{}", subject);
        LOGGER.info("{}", scaledMatrix);

        //TODO assertions
    }

    @Test
    public void testMatrixAddition() {
        MatrixOperation<MatrixNumberValue> addOperation = new MatrixAddOperation<>(subject,
            getTestMatrix());
        MatrixTransformer<MatrixNumberValue> transformer = new MatrixTransformer<>(addOperation);
        Matrix<MatrixNumberValue> summedMatrix = transformer.performOperation();

        LOGGER.info("{}", subject);
        LOGGER.info("{}", summedMatrix);

        //TODO assertions
    }

    @Test
    public void testMatrixRowSwapOperation() {
        MatrixOperation<MatrixNumberValue> rowSwapOperation = new MatrixRowSwapOperation<>(subject,
            1, 2);
        MatrixTransformer<MatrixNumberValue> transformer = new MatrixTransformer<>(
            rowSwapOperation);
        Matrix<MatrixNumberValue> swappedMatrix = transformer.performOperation();

        LOGGER.info("{}", subject);
        LOGGER.info("{}", swappedMatrix);

        //TODO assertions
    }

    @Test
    public void testMatrixComposeOperation() {
        MatrixOperation<MatrixNumberValue> composeOperation = new MatrixComposeOperation<>(subject,
            getTestMatrix());
        MatrixTransformer<MatrixNumberValue> transformer = new MatrixTransformer<>(
            composeOperation);
        Matrix<MatrixNumberValue> composedMatrix = transformer.performOperation();

        LOGGER.info("{}", subject);
        LOGGER.info("{}", composedMatrix);

        //TODO assertions
    }

    //TODO CC make this a factor from junit5..
    private Matrix<MatrixNumberValue> getTestMatrix() {
        Set<MatrixElement<MatrixNumberValue>> set = new HashSet<>();

        for (int x = 1; x <= NUM_ROWS; x++) {
            for (int y = 1; y <= NUM_COLS; y++) {
                set.add(new SimpleMatrixElement<>(x, y, new MatrixNumberValue(x + 1.0 * y + 1.0)));
            }
        }

        return new SimpleMatrix<>(set);
    }
}
