package com.github.ifthen2.matrix.value;

public interface MatrixValue<T> {

    MatrixValue<T> add(MatrixValue<T> e2);

    MatrixValue<T> multiply(MatrixValue<T> e2);

    T getValue();
}
