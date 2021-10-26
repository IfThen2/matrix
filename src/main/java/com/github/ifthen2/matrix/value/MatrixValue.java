package com.github.ifthen2.matrix.value;

/**
 * Basic interface for Matrix Values. They must simply define add and multiply operations.
 */
public interface MatrixValue<T> {

    /**
     * Add this value to another, and return the result as a new MatrixValue.
     *
     * @param e2 value to add
     * @return new summed MatrixValue
     */
    T add(T e2);

    /**
     * Multiply this value with another, and return the result as a new MatrixValue.
     *
     * @param e2 value to multiply by
     * @return new resultant MatrixValue
     */
    T multiply(T e2);

    T additiveIdentity();

    T multiplicativeIdentity();
}
