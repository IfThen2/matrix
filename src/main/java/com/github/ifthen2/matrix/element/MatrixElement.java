package com.github.ifthen2.matrix.element;

import com.github.ifthen2.matrix.value.MatrixValue;
import java.util.Comparator;
import java.util.function.Function;

/**
 * Simple interface to represent a single 'element' or 'cell' of a Matrix.
 */
public interface MatrixElement<T extends MatrixValue<T>> {

    Comparator<MatrixElement<?>> ROW_COL_ORDER = Comparator.comparing(
        (Function<MatrixElement<?>, Integer>) MatrixElement::getRow).thenComparing(
        MatrixElement::getColumn);

    /**
     * Get the underlying value
     *
     * @return a MatrixValue
     */
    T getValue();

    /**
     * Add this element with another, and return the result as a new MatrixElement.
     *
     * @param operand - element to be added to this.
     * @return new summed MatrixElement
     */
    MatrixElement<T> add(MatrixElement<T> operand);

    /**
     * Multiply this element with another, and return the result as a new MatrixElement.
     *
     * @param operand - element to be multiplied with this.
     * @return new resultant MatrixElement
     */
    MatrixElement<T> multiply(MatrixElement<T> operand);

    /**
     * Multiply this element by a scalar value, and return the result as a new MatrixElement.
     *
     * @param operand - scalar value to multiply this element by
     * @return new summed MatrixElement
     */
    MatrixElement<T> multiply(T operand);

    /**
     * Get the row of this element.
     *
     * @return row number
     */
    int getRow();

    /**
     * Get the column of this element
     *
     * @return column number
     */
    int getColumn();
}
