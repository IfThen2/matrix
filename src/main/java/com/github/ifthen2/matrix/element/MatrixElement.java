package com.github.ifthen2.matrix.element;

import com.github.ifthen2.matrix.value.MatrixValue;

public interface MatrixElement<T extends MatrixValue> {

    public T getValue();

    MatrixElement<T> add(MatrixElement<T> operand);

    MatrixElement<T> multiply(MatrixElement<T> operand);

    MatrixElement<T> multiply(T operand);

    int getRow();

    int getColumn();
}
