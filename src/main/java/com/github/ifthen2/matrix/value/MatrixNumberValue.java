package com.github.ifthen2.matrix.value;

import com.google.common.base.Objects;

/**
 * Implementation of MatrixValue for numbers, using a simple double to hold the value.
 */
public class MatrixNumberValue implements MatrixValue<MatrixNumberValue> {

    private final double value;

    public MatrixNumberValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public MatrixNumberValue add(MatrixNumberValue e2) {
        return new MatrixNumberValue(value + e2.getValue());
    }

    @Override
    public MatrixNumberValue multiply(MatrixNumberValue e2) {
        return new MatrixNumberValue(value * e2.getValue());
    }

    @Override
    public MatrixNumberValue additiveIdentity() {
        return new MatrixNumberValue(0);
    }

    @Override
    public MatrixNumberValue multiplicativeIdentity() {
        return new MatrixNumberValue(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MatrixNumberValue that = (MatrixNumberValue) o;
        return Double.compare(that.value, value) == 0;
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
