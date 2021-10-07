package com.github.ifthen2.matrix.operation.matrix.impl;

import static java.util.Objects.requireNonNull;

import com.github.ifthen2.matrix.Matrix;
import com.github.ifthen2.matrix.operation.matrix.MatrixOperation;

public class ComposeMatrixOperation implements MatrixOperation {

    private final Matrix receiver;
    private final Matrix operand;

    public ComposeMatrixOperation(Matrix receiver, Matrix operand) {
        this.receiver = requireNonNull(receiver, "receiver must not be null");
        this.operand = requireNonNull(operand, "operand must not be null");
    }

    @Override
    public Matrix perform() {
        return receiver.composeMatrix(operand);
    }
}
