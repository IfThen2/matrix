package com.github.ifthen2.matrix;

import com.github.ifthen2.matrix.element.MatrixElement;
import com.github.ifthen2.matrix.value.MatrixValue;
import com.github.ifthen2.matrix.vector.MatrixVector;
import com.google.common.base.Predicate;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiPredicate;

public interface Matrix<T extends MatrixValue> {


    Predicate<Object[][]> IS_RECTANGULAR = e -> Arrays.stream(e)
        .allMatch(arr -> arr.length == e.length);
    BiPredicate<Matrix<?>, Matrix<?>> ROW_DIMENSIONS_MATCH =
        (m1, m2) -> m1.getRowDim() == m2.getRowDim();
    BiPredicate<Matrix<?>, Matrix<?>> COL_DIMENSIONS_MATCH =
        (m1, m2) -> m1.getColDim() == m2.getColDim();
    BiPredicate<MatrixElement<?>, Integer> ELEMENT_IN_ROW = (e, i) -> e.getRow() == i;
    BiPredicate<Matrix, Matrix> COMPOSITION_DIMENSIONS_MATCH =
        (m1, m2) -> m1.getColDim() == m2.getRowDim();

    MatrixElement<T> getElement(int rowIndex, int columnIndex);

    Collection<MatrixElement<T>> getElements();

    int getRowDim();

    int getColDim();

    MatrixElement<T>[][] getRectangularArray();

    Matrix<T> add(Matrix<T> otherMatrix);

    Matrix<T> scale(T scalar);

    Matrix<T> composeMatrix(Matrix<T> transform);

    Matrix<T> transpose();

    MatrixVector<T> getRowVector(int rowIndex);

    MatrixVector<T> getColumnVector(int columnIndex);

    Matrix<T> addRowToRow(int firstRowIndex, int secondRowIndex);

    Matrix<T> scaleRow(int rowIndex, T scalar);

    Matrix<T> swapRow(int firstRowIndex, int secondRowIndex);

}