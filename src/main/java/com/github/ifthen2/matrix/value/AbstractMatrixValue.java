package com.github.ifthen2.matrix.value;

public abstract class AbstractMatrixValue<T> implements MatrixValue<T> {

    final T value;

    public AbstractMatrixValue(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }
}
