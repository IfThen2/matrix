package com.github.ifthen2.matrix.vector;


import com.github.ifthen2.matrix.element.MatrixElement;
import com.github.ifthen2.matrix.value.MatrixValue;
import com.github.ifthen2.matrix.vector.SimpleMatrixVector.VectorOrientation;

public interface MatrixVector<T extends MatrixValue> {


    MatrixElement<T>[] getElements();

    MatrixElement<T> getElement(int index);

    int getIndex();

    int getDimension();

    VectorOrientation getOrientation();

    MatrixVector<T> scale(T scalar);

    MatrixVector<T> add(MatrixVector<T> otherVector);

    MatrixValue<T> dotProduct(MatrixVector<T> v2);
}