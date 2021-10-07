package com.github.ifthen2.matrix.transform;

import com.github.ifthen2.matrix.Matrix;
import com.github.ifthen2.matrix.operation.matrix.MatrixOperation;

public class MatrixTransformer {

    private final MatrixOperation operation;

    public MatrixTransformer(MatrixOperation operation) {
        this.operation = operation;
    }

    public Matrix performOperation() {
        return operation.perform();
    }
}