package com.github.ifthen2.matrix.operation.matrix.impl;

import static java.util.Objects.requireNonNull;

import com.github.ifthen2.matrix.Matrix;
import com.github.ifthen2.matrix.operation.matrix.MatrixOperation;
import com.google.common.base.Preconditions;

public class MatrixRowSwapOperation implements MatrixOperation {

    private final Matrix receiver;
    private final int firstRowIndex;
    private final int secondRowIndex;

    public MatrixRowSwapOperation(Matrix receiver, int firstRowIndex, int secondRowIndex) {
        this.receiver = requireNonNull(receiver, "receiver must not be null");
        this.firstRowIndex = Preconditions
            .checkElementIndex(firstRowIndex, receiver.getRowDim(),
                "r1 index out of bounds.");
        this.secondRowIndex = Preconditions
            .checkPositionIndex(secondRowIndex, receiver.getRowDim(),
                "r2 index out of bounds.");
    }

    @Override
    public Matrix perform() {
        return receiver.swapRow(firstRowIndex, secondRowIndex);
    }
}