package com.github.ifthen2.matrix.operation.matrix.impl;

import static java.util.Objects.requireNonNull;

import com.github.ifthen2.matrix.Matrix;
import com.github.ifthen2.matrix.operation.matrix.MatrixOperation;
import com.github.ifthen2.matrix.value.MatrixValue;

public class MatrixAddOperation<T extends MatrixValue<T>> implements MatrixOperation<T> {

    private final Matrix<T> receiver;
    private final Matrix<T> operand;

    public MatrixAddOperation(Matrix<T> receiver, Matrix<T> operand) {
        this.receiver = requireNonNull(receiver, "receiver must not be null");
        this.operand = requireNonNull(operand, "operand must not be null");
    }

    @Override
    public Matrix<T> perform() {
        return receiver.add(operand);
    }
}
