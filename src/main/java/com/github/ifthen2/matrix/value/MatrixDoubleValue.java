package com.github.ifthen2.matrix.value;

public class MatrixDoubleValue extends AbstractMatrixValue<Double> {

    public MatrixDoubleValue(Double value) {
        super(value);
    }

    public MatrixDoubleValue() {
        super(0.0d);
    }

    @Override
    public MatrixValue<Double> add(MatrixValue<Double> e2) {
        return new MatrixDoubleValue(value + e2.getValue());
    }

    @Override
    public MatrixValue<Double> multiply(MatrixValue<Double> e2) {
        return new MatrixDoubleValue(value * e2.getValue());
    }
}
