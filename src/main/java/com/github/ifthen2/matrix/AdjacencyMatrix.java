package com.github.ifthen2.matrix;

import com.github.ifthen2.matrix.element.MatrixElement;
import com.github.ifthen2.matrix.value.MatrixBooleanValue;
import java.util.Set;

//TODO CC WIP
public class AdjacencyMatrix extends SimpleMatrix<MatrixBooleanValue> {

    public AdjacencyMatrix(
        Set<MatrixElement<MatrixBooleanValue>> matrixElements) {
        super(matrixElements);
    }
}
