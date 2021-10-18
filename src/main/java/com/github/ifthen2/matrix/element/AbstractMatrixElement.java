package com.github.ifthen2.matrix.element;

import com.github.ifthen2.matrix.value.MatrixValue;
import com.google.common.base.Preconditions;

/**
 * Skeletal implementation of the MatrixElement interface to minimize the effort required to
 * implement.
 */
public abstract class AbstractMatrixElement<T extends MatrixValue<T>> implements MatrixElement<T> {

    final int row;
    final int column;
    final T value;

    public AbstractMatrixElement(int row, int column, T value) {

        Preconditions.checkArgument(row > 0, "row must be > 0. passed {}", row);
        Preconditions.checkArgument(column > 0, "column must be > 0. passed {}", column);

        this.row = row;
        this.column = column;
        this.value = value;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public T getValue() {
        return value;
    }
}
