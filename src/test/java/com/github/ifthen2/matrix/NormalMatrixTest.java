package com.github.ifthen2.matrix;


import com.github.ifthen2.matrix.operation.matrix.MatrixOperation;
import com.github.ifthen2.matrix.operation.matrix.impl.ComposeMatrixOperation;
import com.github.ifthen2.matrix.operation.matrix.impl.MatrixAddOperation;
import com.github.ifthen2.matrix.operation.matrix.impl.MatrixRowSwapOperation;
import com.github.ifthen2.matrix.operation.matrix.impl.MatrixScaleOperation;
import com.github.ifthen2.matrix.transform.MatrixTransformer;
import com.github.ifthen2.matrix.value.MatrixDoubleValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NormalMatrixTest {

    static final Logger LOGGER = LoggerFactory.getLogger(NormalMatrixTest.class);

    static final double DELTA = 1e-15;

    static final int numRows = 10;
    static final int numCols = 10;

    Matrix subject;

    /**
     * Fixture initialization (common initialization for all tests).
     **/
    @BeforeEach
    public void setUp() {
        subject = getTestMatrix();
    }

    @Test
    public void testMatrixScale() {
        MatrixOperation scaleOperation = new MatrixScaleOperation(subject,
            new MatrixDoubleValue(2.0d));
        MatrixTransformer transformer = new MatrixTransformer(scaleOperation);
        Matrix scaledMatrix = transformer.performOperation();

        LOGGER.info("{}", subject);
        LOGGER.info("{}", scaledMatrix);
    }

    @Test
    public void testMatrixAddition() {
        MatrixOperation addOperation = new MatrixAddOperation(subject, getTestMatrix());
        MatrixTransformer transformer = new MatrixTransformer(addOperation);
        Matrix summedMatrix = transformer.performOperation();

        LOGGER.info("{}", subject);
        LOGGER.info("{}", summedMatrix);
    }

    @Test
    public void testMatrixRowSwapOperation() {
        MatrixOperation rowSwapOperation = new MatrixRowSwapOperation(subject, 1, 2);
        MatrixTransformer transformer = new MatrixTransformer(rowSwapOperation);
        Matrix swappedMatrix = transformer.performOperation();

        LOGGER.info("{}", subject);
        LOGGER.info("{}", swappedMatrix);
    }

    @Test
    public void testMatrixComposeOperation() {
        MatrixOperation composeOperation = new ComposeMatrixOperation(subject, getTestMatrix());
        MatrixTransformer transformer = new MatrixTransformer(composeOperation);
        Matrix composedMatrix = transformer.performOperation();

        LOGGER.info("{}", subject);
        LOGGER.info("{}", composedMatrix);
    }

    private Matrix getTestMatrix() {
        MatrixDoubleValue[][] elements = new MatrixDoubleValue[2][2];

        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                elements[x][y] = new MatrixDoubleValue(x + 1.0 * y + 1.0);
            }
        }

        return new SimpleMatrix(elements);
    }
}
