package com.github.ifthen2.matrix.operation.matrix.impl;

import static java.util.Objects.requireNonNull;

import com.github.ifthen2.matrix.Matrix;
import com.github.ifthen2.matrix.operation.matrix.MatrixOperation;
import com.github.ifthen2.matrix.value.MatrixValue;

public class MatrixScaleOperation implements MatrixOperation {

    private final Matrix receiver;
    private final MatrixValue scalar;

    public MatrixScaleOperation(Matrix receiver, MatrixValue scalar) {
        this.receiver = requireNonNull(receiver, "receiver must not be null");
        this.scalar = scalar;
    }

    @Override
    public Matrix perform() {
        return receiver.scale(scalar);
    }
}