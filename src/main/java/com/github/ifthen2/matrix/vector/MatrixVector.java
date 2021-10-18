package com.github.ifthen2.matrix.vector;


import com.github.ifthen2.matrix.element.MatrixElement;
import com.github.ifthen2.matrix.value.MatrixValue;
import com.github.ifthen2.matrix.vector.SimpleMatrixVector.VectorOrientation;
import java.util.Set;

/**
 * Simple generic interface to represent Matrix Vectors.. not really sure about this yet.
 */
public interface MatrixVector<T extends MatrixValue<T>> {

    /**
     * Get all elements
     *
     * @return set of this vectors elements
     */
    Set<MatrixElement<T>> getElements();

    /**
     * Get a single element
     *
     * @param index requested index
     * @return element at requested index
     */
    MatrixElement<T> getElement(int index);

    /**
     * Get this vectors index
     *
     * @return the row or column # of this vector
     */
    int getIndex();

    /**
     * Get this vectors dimension
     *
     * @return the size of this vector
     */
    int getDimension();

    /**
     * Get this vectors orientation
     *
     * @return whether this vector is a ROW or COLUMN
     */
    VectorOrientation getOrientation();

    /**
     * Scale this vector by a scalar value, and return the result as a new Vector.
     *
     * @param scalar - value to scale by
     * @return new scaled Vector
     */
    MatrixVector<T> scale(T scalar);

    /**
     * Add this vector to another, and return the result as a new Vector.
     *
     * @param otherVector - vector to add with
     * @return new summed Vector
     */
    MatrixVector<T> add(MatrixVector<T> otherVector);

    /**
     * Multiply two Matrix Vectors, and return the result as a new Vector.
     *
     * @param v2 vector to multiply with
     * @return new resultant Vector
     */
    T dotProduct(MatrixVector<T> v2);
}