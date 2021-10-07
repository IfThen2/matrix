package com.github.ifthen2.matrix;

import com.github.ifthen2.matrix.element.MatrixElement;
import com.github.ifthen2.matrix.element.SimpleMatrixElement;
import com.github.ifthen2.matrix.value.MatrixValue;
import com.github.ifthen2.matrix.vector.MatrixVector;
import com.github.ifthen2.matrix.vector.SimpleMatrixVector;
import com.github.ifthen2.matrix.vector.SimpleMatrixVector.VectorOrientation;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Tables;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleMatrix<T extends MatrixValue> extends AbstractMatrix<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMatrix.class);

    public SimpleMatrix(MatrixElement<T>[][] elements) {
        super(elements);
    }

    public SimpleMatrix(T[][] elements) {
        super(elements);
    }

    private SimpleMatrix(Collection<MatrixElement<T>> elements) {

    }


    @Override
    public MatrixElement<T> getElement(int rowIndex, int colIndex) {
        Preconditions
            .checkPositionIndex(rowIndex - 1, this.getRowDim() - 1, "colIndex is out of bounds.");
        Preconditions
            .checkPositionIndex(colIndex - 1, this.getColDim() - 1, "colIndex is out of bounds.");

        return elementTable.get(rowIndex, colIndex);
    }


    @Override
    public MatrixElement<T>[][] getRectangularArray() {
        return new MatrixElement[0][];
    }

    @Override
    public Matrix<T> add(Matrix<T> m2) {
        Preconditions.checkNotNull(m2, "m2 must not be null");
        Preconditions
            .checkArgument(ROW_DIMENSIONS_MATCH.and(COL_DIMENSIONS_MATCH).test(this, m2),
                "addition is not defined for matrices of different dimensions. this={}x{} m2={}x{}",
                this.rowDim,
                this.colDim,
                m2.getRowDim(),
                m2.getColDim());

        LOGGER.info("Adding M2 to Matrix");

        Collection<MatrixElement<T>> collection = elementTable.values().stream()
            .map(e -> e.add(m2.getElement(e.getRow(), e.getColumn()))).collect(Collectors.toList());

        return new SimpleMatrix<T>(collection);
    }

    @Override
    public Matrix<T> scale(T scalar) {
        LOGGER.info("Scaling Matrix by {}", scalar);

        return null;
//        return new SimpleMatrix<T>(
//            Tables.transformValues(elementTable, e -> e.getValue().multiply(scalar)));
    }

    @Override
    public Matrix<T> composeMatrix(Matrix<T> m2) {
        Preconditions.checkArgument(COMPOSITION_DIMENSIONS_MATCH.test(this, m2),
            "composition is not defined when #A-rows != #B-columns. this={}{}, other={}{}",
            this.rowDim,
            this.colDim,
            m2.getRowDim(),
            m2.getColDim());

        MatrixElement<T>[][] resultElements = new MatrixElement[this.rowDim][m2
            .getColDim()];

        IntStream.rangeClosed(1, this.rowDim)
            .forEach(rowIndex -> {
                IntStream.rangeClosed(1, m2.getColDim())
                    .forEach(colIndex -> {
                        resultElements[rowIndex - 1][colIndex - 1] =
                            new SimpleMatrixElement(rowIndex, colIndex, this
                                .getRowVector(rowIndex)
                                .dotProduct(m2.getColumnVector(colIndex)));
                    });
            });

        return new SimpleMatrix<>(resultElements);
    }

    @Override
    public Matrix<T> transpose() {
        LOGGER.info("Transposing...");

        Collection<MatrixElement<T>> collection = elementTable.values().stream()
            .map(e -> new SimpleMatrixElement(e.getColumn(), e.getRow(), e.getValue()))
            .collect(Collectors.toList());

        return new SimpleMatrix<T>(collection);
    }

    @Override
    public MatrixVector<T> getRowVector(int rowIndex) {
        Preconditions
            .checkPositionIndex(rowIndex - 1, this.rowDim - 1, "rowIndex is out of bounds.");

        return new SimpleMatrixVector(elementTable
            .values()
            .stream()
            .filter(e -> e.getRow() == rowIndex)
            .collect(ImmutableList.toImmutableList())
            .toArray(new MatrixElement[this.colDim]), rowIndex, VectorOrientation.ROW);
    }

    @Override
    public MatrixVector<T> getColumnVector(int columnIndex) {
        Preconditions
            .checkPositionIndex(columnIndex - 1, this.colDim - 1,
                "columnIndex is out of bounds.");

        return new SimpleMatrixVector<T>(elementTable
            .values()
            .stream()
            .filter(e -> e.getColumn() == columnIndex)
            .collect(ImmutableList.toImmutableList())
            .toArray(new MatrixElement[this.rowDim]), columnIndex, VectorOrientation.COLUMN);
    }

    @Override
    public Matrix<T> addRowToRow(int firstRowIndex, int secondRowIndex) {
        Preconditions
            .checkPositionIndex(firstRowIndex - 1, this.rowDim - 1,
                "firstRowIndex is out of bounds.");
        Preconditions
            .checkPositionIndex(secondRowIndex - 1, this.colDim - 1,
                "secondRowIndex is out of bounds.");

        LOGGER.info("Adding Row {} to Row {}", firstRowIndex, secondRowIndex);

        return new SimpleMatrix<T>(Tables.transformValues(elementTable,
            e -> ELEMENT_IN_ROW.test(e, firstRowIndex)
                ? e.add(this.getElement(secondRowIndex, e.getColumn()))
                : e));
    }

    @Override
    public Matrix<T> scaleRow(int rowIndex, T scalar) {
        Preconditions
            .checkPositionIndex(rowIndex - 1, this.rowDim - 1, "rowIndex is out of bounds.");

        LOGGER.info("Scaling Row {} By {}", rowIndex, scalar);

        return new SimpleMatrix<T>(Tables.transformValues(elementTable,
            e -> ELEMENT_IN_ROW.test(e, rowIndex)
                ? e.multiply(scalar)
                : e));
    }

    @Override
    public Matrix<T> swapRow(int firstRowIndex, int secondRowIndex) {
        Preconditions
            .checkPositionIndex(firstRowIndex - 1, this.rowDim - 1,
                "firstRowIndex is out of bounds.");
        Preconditions
            .checkPositionIndex(secondRowIndex - 1, this.colDim - 1,
                "secondRowIndex is out of bounds.");

        LOGGER.info("Swapping Row {} and Row {}", firstRowIndex, secondRowIndex);

        return new SimpleMatrix<T>(Tables.transformValues(elementTable,
            e -> {
                if (ELEMENT_IN_ROW.test(e, firstRowIndex)) {
                    System.out.println("found 1");
                    return new SimpleMatrixElement<T>(secondRowIndex, e.getColumn(), e.getValue());
                } else if (ELEMENT_IN_ROW.test(e, secondRowIndex)) {
                    System.out.println("found 2");
                    return new SimpleMatrixElement<T>(firstRowIndex, e.getColumn(), e.getValue());
                } else {
                    System.out.println("found 3");
                    return e;
                }
            }));
    }

    @Override
    public String toString() {
        return "SimpleMatrix{" +
            "elementTable=" + elementTable +
            '}';
    }
}
