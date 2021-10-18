package com.github.ifthen2.matrix.transform;

import com.github.ifthen2.matrix.Matrix;
import com.github.ifthen2.matrix.operation.matrix.MatrixOperation;
import com.github.ifthen2.matrix.value.MatrixValue;
import java.util.Objects;

/**
 * Optional Invoker for Matrix Operations
 */
public class MatrixTransformer<T extends MatrixValue<T>> {

    private final MatrixOperation<T> operation;

    public MatrixTransformer(MatrixOperation<T> operation) {
        this.operation = Objects.requireNonNull(operation, "operation must not be null");
    }

    /**
     * Performs the requested Matrix Operation
     *
     * @return new resultant Matrix
     */
    public Matrix<T> performOperation() {
        return operation.perform();
    }
}