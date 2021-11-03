package com.github.ifthen2.matrix.operation.matrix;

import com.github.ifthen2.matrix.Matrix;
import com.github.ifthen2.matrix.value.MatrixValue;

/**
 * Interface for optionally encapsulating Matrix Operations with Command Pattern.
 */
@FunctionalInterface
public interface MatrixOperation<T extends MatrixValue<T>> {

    /**
     * Perform a Matrix Operation
     *
     * @return a new resultant Matrix
     */
    Matrix<T> perform();
}