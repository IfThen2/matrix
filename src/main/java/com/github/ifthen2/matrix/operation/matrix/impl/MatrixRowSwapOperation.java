package com.github.ifthen2.matrix.operation.matrix.impl;

import static java.util.Objects.requireNonNull;

import com.github.ifthen2.matrix.Matrix;
import com.github.ifthen2.matrix.operation.matrix.MatrixOperation;
import com.github.ifthen2.matrix.value.MatrixValue;
import com.google.common.base.Preconditions;

/**
 * Defines a row swap Operation for a Matrix.
 */
public class MatrixRowSwapOperation<T extends MatrixValue<T>> implements MatrixOperation<T> {

    private final Matrix<T> receiver;
    private final int firstRowIndex;
    private final int secondRowIndex;

    public MatrixRowSwapOperation(Matrix<T> receiver, int firstRowIndex, int secondRowIndex) {
        this.receiver = requireNonNull(receiver, "receiver must not be null");
        this.firstRowIndex = Preconditions
            .checkElementIndex(firstRowIndex, receiver.getRowDim(),
                "r1 index out of bounds.");
        this.secondRowIndex = Preconditions
            .checkPositionIndex(secondRowIndex, receiver.getRowDim(),
                "r2 index out of bounds.");
    }

    @Override
    public Matrix<T> perform() {
        return receiver.swapRow(firstRowIndex, secondRowIndex);
    }
}