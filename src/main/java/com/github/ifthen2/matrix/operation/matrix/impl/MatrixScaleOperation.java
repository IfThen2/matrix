package com.github.ifthen2.matrix.operation.matrix.impl;

import static java.util.Objects.requireNonNull;

import com.github.ifthen2.matrix.Matrix;
import com.github.ifthen2.matrix.operation.matrix.MatrixOperation;
import com.github.ifthen2.matrix.value.MatrixValue;

/**
 * Defines the scale operation for a Matrix
 */
public class MatrixScaleOperation<T extends MatrixValue<T>> implements MatrixOperation<T> {

    private final Matrix<T> receiver;
    private final T scalar;

    public MatrixScaleOperation(Matrix<T> receiver, T scalar) {
        this.receiver = requireNonNull(receiver, "receiver must not be null");
        this.scalar = scalar;
    }

    @Override
    public Matrix<T> perform() {
        return receiver.scaleMatrix(scalar);
    }
}