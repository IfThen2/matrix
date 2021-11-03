package com.github.ifthen2.matrix;

import com.github.ifthen2.matrix.element.MatrixElement;
import com.github.ifthen2.matrix.value.MatrixValue;
import com.github.ifthen2.matrix.vector.MatrixVector;
import java.util.Set;
import java.util.function.BiPredicate;

/**
 * Generic interface for Matrices. Defines basic operations on elements of type {@link MatrixValue}
 */
public interface Matrix<T extends MatrixValue<T>> extends MatrixValue<Matrix<T>> {

    BiPredicate<MatrixElement<?>, Integer> ELEMENT_IN_ROW = (e, i) -> e.getRow() == i;

    /**
     * Get the element at specified row/col indices.
     *
     * @param rowIndex - row of requested element
     * @param columnIndex - column of requested element
     * @return the requested element, or null if not found
     */
    MatrixElement<T> getElement(int rowIndex, int columnIndex);

    /**
     * Get all elements from this Matrix
     *
     * @return all elements
     */
    Set<MatrixElement<T>> getElements();

    /**
     * Get the number of rows
     */
    int getRowDim();

    /**
     * Get the number of columns
     */
    int getColDim();

    /**
     * Add another Matrix to this one, and return a new Matrix as the result.
     *
     * @param otherMatrix - Matrix to be added to this one
     * @return new summed Matrix
     */
    Matrix<T> addMatrix(Matrix<T> otherMatrix);

    /**
     * Scale this matrix by a simple scalar value, and return a new Matrix as the result.
     *
     * @param scalar - value to scale each element by
     * @return new scaled Matrix
     */
    Matrix<T> scaleMatrix(T scalar);

    /**
     * Compose this matrix with another one, and return a new Matrix as the result.
     *
     * @param otherMatrix - Matrix to compose with this one.
     * @return new composed Matrix
     */
    Matrix<T> composeMatrix(Matrix<T> otherMatrix);

    /**
     * Transpose the rows and columns of this Matrix, and return a new Matrix as the result.
     *
     * @return new transposed Matrix
     */
    Matrix<T> transpose();

    /**
     * Get Row Vector representation of element sub-set.
     *
     * @param rowIndex - row number requested
     * @return MatrixVector row representation
     */
    MatrixVector<T> getRowVector(int rowIndex);

    /**
     * Get Column Vector representation of element sub-set.
     *
     * @param columnIndex - column number requested
     * @return MatrixVector column representation
     */
    MatrixVector<T> getColumnVector(int columnIndex);

    /**
     * Add one row of this Matrix to another, and return the result as a new Matrix.
     *
     * @param firstRowIndex - first row number
     * @param secondRowIndex - second row number
     * @return new resultant Matrix
     */
    Matrix<T> addRowToRow(int firstRowIndex, int secondRowIndex, T scalar);

    /**
     * Scale one row of this Matrix by a scalar value, and return the result as a new Matrix.
     *
     * @param rowIndex - row number to scale
     * @param scalar - value to scale by
     * @return new resultant Matrix
     */
    Matrix<T> scaleRow(int rowIndex, T scalar);

    /**
     * Swap one row of this Matrix with another, and return the result as a new Matrix.
     *
     * @param firstRowIndex - first row number
     * @param secondRowIndex - second row number
     * @return new resultant Matrix
     */
    Matrix<T> swapRow(int firstRowIndex, int secondRowIndex);

    @Override
    default Matrix<T> add(Matrix<T> e2) {
        return this.addMatrix(e2);
    }

    @Override
    default Matrix<T> multiply(Matrix<T> e2) {
        return this.composeMatrix(e2);
    }
}