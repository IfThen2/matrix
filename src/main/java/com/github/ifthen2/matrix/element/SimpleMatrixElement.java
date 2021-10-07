package com.github.ifthen2.matrix.element;

import com.github.ifthen2.matrix.value.MatrixValue;
import com.google.common.base.Preconditions;

public class SimpleMatrixElement<T extends MatrixValue> extends AbstractMatrixElement<T> {

    public SimpleMatrixElement(int row, int column, T value) {
        super(row, column, value);
    }

    @Override
    public MatrixElement<T> add(MatrixElement<T> operand) {

        Preconditions.checkNotNull(operand, "operand must not be null");

        T newVal = (T) value.add(operand.getValue());

        return new SimpleMatrixElement<T>(getRow(), getColumn(), newVal);
    }

    @Override
    public MatrixElement<T> multiply(MatrixElement<T> operand) {

        Preconditions.checkNotNull(operand, "operand must not be null");

        MatrixValue<T> newVal = value.multiply(operand.getValue());

        return new SimpleMatrixElement<T>(getRow(), getColumn(), (T) newVal);
    }

    @Override
    public MatrixElement<T> multiply(T operand) {
        Preconditions.checkNotNull(operand, "operand must not be null");

        MatrixValue<T> newVal = value.multiply(operand);

        return new SimpleMatrixElement<T>(getRow(), getColumn(), (T) newVal);
    }

    @Override
    public String toString() {
        return String.format("|%7.2f|", value.getValue());
    }
}