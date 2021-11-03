package com.github.ifthen2.matrix.element;

import com.github.ifthen2.matrix.value.MatrixValue;
import com.google.common.base.Preconditions;

/**
 * Generic implementation of a MatrixElement.
 */
public class SimpleMatrixElement<T extends MatrixValue<T>> extends AbstractMatrixElement<T> {

    public SimpleMatrixElement(int row, int column, T value) {
        super(row, column, value);
    }

    /**
     * @throws NullPointerException if operand is null
     */
    @Override
    public MatrixElement<T> add(MatrixElement<T> operand) {

        Preconditions.checkNotNull(operand, "operand must not be null");

        T newVal = value.add(operand.getValue());

        return new SimpleMatrixElement<>(getRow(), getColumn(), newVal);
    }

    /**
     * @throws NullPointerException if operand is null
     */
    @Override
    public MatrixElement<T> multiply(MatrixElement<T> operand) {

        Preconditions.checkNotNull(operand, "operand must not be null");

        T newVal = value.multiply(operand.getValue());

        return new SimpleMatrixElement<>(getRow(), getColumn(), newVal);
    }

    /**
     * @throws NullPointerException if operand is null
     */
    @Override
    public MatrixElement<T> multiply(T operand) {
        Preconditions.checkNotNull(operand, "operand must not be null");

        T newVal = value.multiply(operand);

        return new SimpleMatrixElement<>(getRow(), getColumn(), newVal);
    }
    

    @Override
    public String toString() {
        return "SimpleMatrixElement{" +
            "row=" + row +
            ", column=" + column +
            ", value=" + value +
            '}';
    }
}