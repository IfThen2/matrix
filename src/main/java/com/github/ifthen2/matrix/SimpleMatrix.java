package com.github.ifthen2.matrix;

import static java.util.stream.Collectors.toSet;

import com.github.ifthen2.matrix.element.MatrixElement;
import com.github.ifthen2.matrix.element.SimpleMatrixElement;
import com.github.ifthen2.matrix.value.MatrixValue;
import com.github.ifthen2.matrix.vector.MatrixVector;
import com.github.ifthen2.matrix.vector.SimpleMatrixVector;
import com.github.ifthen2.matrix.vector.SimpleMatrixVector.VectorOrientation;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Streams;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic implementation of a Matrix as it pertains to Linear maps.
 */
public class SimpleMatrix<T extends MatrixValue<T>> implements Matrix<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMatrix.class);

    final int rowDim;
    final int colDim;
    final Set<MatrixElement<T>> elementTable;

    public SimpleMatrix(Set<MatrixElement<T>> elements) {

        //TODO CC Preconditions...

        this.elementTable = elements;
        this.rowDim = elements.stream().mapToInt(MatrixElement::getRow).max()
            .orElseThrow(() -> new IllegalArgumentException("bad row data"));
        this.colDim = elements.stream().mapToInt(MatrixElement::getColumn).max()
            .orElseThrow(() -> new IllegalArgumentException("bad column data"));
    }

    /**
     * @throws IllegalArgumentException if requested element does not exist
     * @throws IndexOutOfBoundsException if either index exceeds the matrix dimensions
     */
    @Override
    public MatrixElement<T> getElement(int rowIndex, int colIndex) {

        Preconditions
            .checkPositionIndex(rowIndex - 1, this.getRowDim() - 1, "colIndex is out of bounds.");
        Preconditions
            .checkPositionIndex(colIndex - 1, this.getColDim() - 1, "colIndex is out of bounds.");

        Optional<MatrixElement<T>> element = elementTable.stream()
            .filter(e -> e.getColumn() == colIndex && e.getRow() == rowIndex)
            .findAny();

        return element.orElse(null);
    }

    @Override
    public Set<MatrixElement<T>> getElements() {
        return elementTable;
    }

    @Override
    public int getRowDim() {
        return rowDim;
    }

    @Override
    public int getColDim() {
        return colDim;
    }

    /**
     * @throws NullPointerException if second Matrix is null
     * @throws IllegalArgumentException if both Matrices dimensions don't match
     */
    @Override
    public Matrix<T> addMatrix(Matrix<T> otherMatrix) {

        Preconditions.checkNotNull(otherMatrix, "m2 must not be null");
        Preconditions
            .checkArgument(
                (this.rowDim == otherMatrix.getRowDim()) && (this.colDim == otherMatrix
                    .getColDim()),
                "addition is not defined for matrices of different dimensions. this={}x{} otherMatrix={}x{}",
                this.rowDim,
                this.colDim,
                otherMatrix.getRowDim(),
                otherMatrix.getColDim());

        Stream<MatrixElement<T>> m1Stream = elementTable.stream()
            .sorted(MatrixElement.ROW_COL_ORDER);

        Stream<MatrixElement<T>> m2Stream = otherMatrix.getElements().stream()
            .sorted(MatrixElement.ROW_COL_ORDER);

        Set<MatrixElement<T>> newElements = Streams
            .zip(m1Stream, m2Stream, MatrixElement<T>::add)
            .collect(toSet());

        return new SimpleMatrix<>(newElements);
    }

    @Override
    public Matrix<T> scaleMatrix(T scalar) {
        LOGGER.info("Scaling Matrix by {}", scalar);

        Stream<MatrixElement<T>> elementStream = elementTable.stream()
            .map(e -> new SimpleMatrixElement<>(e.getColumn(), e.getRow(),
                e.getValue().multiply(scalar)));

        Set<MatrixElement<T>> newElements = elementStream
            .collect(Collectors.toSet());

        return new SimpleMatrix<>(newElements);
    }

    /**
     * @throws NullPointerException if second Matrix is null
     * @throws IllegalArgumentException if Matrices dimensions aren't compatible for composition
     */
    @Override
    public Matrix<T> composeMatrix(Matrix<T> otherMatrix) {

        Preconditions.checkNotNull(otherMatrix, "m2 must not be null");
        Preconditions.checkArgument(this.rowDim == otherMatrix.getColDim(),
            "composition is not defined when #A-rows != #B-columns. this={}{}, other={}{}",
            this.rowDim,
            this.colDim,
            otherMatrix.getRowDim(),
            otherMatrix.getColDim());

        Set<MatrixVector<T>> rowVectors = IntStream.rangeClosed(1, this.rowDim)
            .mapToObj(this::getRowVector).collect(toSet());

        Set<MatrixVector<T>> colVectors = IntStream.rangeClosed(1, otherMatrix.getColDim())
            .mapToObj(otherMatrix::getColumnVector).collect(toSet());

        Set<MatrixElement<T>> resultElements = new HashSet<>();

        for (MatrixVector<T> row : rowVectors) {
            for (MatrixVector<T> col : colVectors) {
                resultElements.add(
                    new SimpleMatrixElement<>(row.getIndex(), col.getIndex(), row.dotProduct(col)));
            }
        }

        return new SimpleMatrix<>(resultElements);
    }

    @Override
    public Matrix<T> transpose() {

        Set<MatrixElement<T>> newElements = elementTable.stream()
            .map(e -> new SimpleMatrixElement<>(e.getColumn(), e.getRow(), e.getValue()))
            .collect(toSet());

        return new SimpleMatrix<>(newElements);
    }

    /**
     * @throws IndexOutOfBoundsException if index exceeds row dimension
     */
    @Override
    public MatrixVector<T> getRowVector(int rowIndex) {
        Preconditions
            .checkPositionIndex(rowIndex - 1, this.rowDim - 1, "rowIndex is out of bounds.");

        Set<MatrixElement<T>> elements = getRowSet(rowIndex);

        return new SimpleMatrixVector<>(elements, rowIndex, VectorOrientation.ROW);
    }

    /**
     * @throws IndexOutOfBoundsException if index exceeds column dimension
     */
    @Override
    public MatrixVector<T> getColumnVector(int columnIndex) {
        Preconditions
            .checkPositionIndex(columnIndex - 1, this.colDim - 1,
                "columnIndex is out of bounds.");

        Set<MatrixElement<T>> elements = getColumnSet(columnIndex);

        return new SimpleMatrixVector<>(elements, columnIndex, VectorOrientation.COLUMN);
    }

    /**
     * @throws IndexOutOfBoundsException if either index exceeds row dimension
     */
    @Override
    public Matrix<T> addRowToRow(int firstRowIndex, int secondRowIndex, T scalar) {

        Preconditions
            .checkPositionIndex(firstRowIndex - 1, this.rowDim - 1,
                "firstRowIndex is out of bounds.");
        Preconditions
            .checkPositionIndex(secondRowIndex - 1, this.colDim - 1,
                "secondRowIndex is out of bounds.");

        LOGGER.info("Adding Row {} to Row {}", firstRowIndex, secondRowIndex);

        Stream<MatrixElement<T>> elementStream = elementTable.stream()
            .map(((MatrixElement<T> e) -> ELEMENT_IN_ROW.test(e, firstRowIndex)
                ? e.add(this.getElement(secondRowIndex, e.getColumn()).multiply(scalar))
                : e));

        Set<MatrixElement<T>> result = elementStream.collect(toSet());

        return new SimpleMatrix<>(result);
    }

    /**
     * @throws IndexOutOfBoundsException if index exceeds row dimension
     */
    @Override
    public Matrix<T> scaleRow(int rowIndex, T scalar) {

        Preconditions
            .checkPositionIndex(rowIndex - 1, this.rowDim - 1, "rowIndex is out of bounds.");

        LOGGER.info("Scaling Row {} By {}", rowIndex, scalar);

        Stream<MatrixElement<T>> elementStream = elementTable.stream()
            .map(((MatrixElement<T> e) -> ELEMENT_IN_ROW.test(e, rowIndex)
                ? e.multiply(scalar)
                : e));

        Set<MatrixElement<T>> result = elementStream.collect(toSet());

        return new SimpleMatrix<>(result);
    }

    /**
     * @throws IndexOutOfBoundsException if either index exceeds row dimension
     */
    @Override
    public Matrix<T> swapRow(int firstRowIndex, int secondRowIndex) {
        Preconditions
            .checkPositionIndex(firstRowIndex - 1, this.rowDim - 1,
                "firstRowIndex is out of bounds.");
        Preconditions
            .checkPositionIndex(secondRowIndex - 1, this.colDim - 1,
                "secondRowIndex is out of bounds.");

        Stream<MatrixElement<T>> elementStream = elementTable.stream()
            .map((e -> {
                if (ELEMENT_IN_ROW.test(e, firstRowIndex)) {
                    return new SimpleMatrixElement<>(secondRowIndex, e.getColumn(), e.getValue());
                } else if (ELEMENT_IN_ROW.test(e, secondRowIndex)) {
                    return new SimpleMatrixElement<>(firstRowIndex, e.getColumn(), e.getValue());
                } else {
                    return e;
                }
            }));

        Set<MatrixElement<T>> result = elementStream.collect(toSet());

        return new SimpleMatrix<>(result);
    }

    /**
     * Get sub-set of elements as a {@link Set}
     *
     * @param rowIndex row number requested
     * @return set of elements
     */
    private Set<MatrixElement<T>> getRowSet(int rowIndex) {
        return elementTable.stream()
            .filter(e -> e.getRow() == rowIndex)
            .collect(toSet());
    }

    /**
     * Get sub-set of elements as a {@link Set}
     *
     * @param colIndex row number requested
     * @return set of elements
     */
    private Set<MatrixElement<T>> getColumnSet(int colIndex) {
        return elementTable.stream()
            .filter(e -> e.getColumn() == colIndex)
            .collect(toSet());
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        elementTable.stream()
            .sorted(MatrixElement.ROW_COL_ORDER)
            .collect(Collectors.groupingBy(MatrixElement::getRow)).values().forEach(
            row -> {
                sb.append(System.lineSeparator());
                row.forEach(
                    element -> sb.append(String.format("|%8s |", element.getValue().toString())));
            });

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleMatrix<?> that = (SimpleMatrix<?>) o;
        return rowDim == that.rowDim && colDim == that.colDim && Objects
            .equal(elementTable, that.elementTable);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rowDim, colDim, elementTable);
    }
}
