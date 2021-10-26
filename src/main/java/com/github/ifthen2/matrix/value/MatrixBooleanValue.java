package com.github.ifthen2.matrix.value;

import com.google.common.base.Objects;

/**
 * Implementation of MatrixValue for boolean values. Useful, for example, in adjacency matrices.
 */
public class MatrixBooleanValue implements MatrixValue<MatrixBooleanValue> {

    private final boolean value;

    public MatrixBooleanValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public MatrixBooleanValue add(MatrixBooleanValue e2) {
        return new MatrixBooleanValue(value | e2.getValue());
    }

    @Override
    public MatrixBooleanValue multiply(MatrixBooleanValue e2) {
        return new MatrixBooleanValue(value & e2.getValue());
    }

    @Override
    public MatrixBooleanValue additiveIdentity() {
        return new MatrixBooleanValue(false);
    }

    @Override
    public MatrixBooleanValue multiplicativeIdentity() {
        return new MatrixBooleanValue(true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MatrixBooleanValue that = (MatrixBooleanValue) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value + "";
    }
}
