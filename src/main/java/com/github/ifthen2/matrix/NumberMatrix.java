//package com.github.ifthen2.matrix;
//
//import com.github.ifthen2.matrix.element.MatrixElement;
//import com.github.ifthen2.matrix.value.MatrixDoubleValue;
//import com.github.ifthen2.matrix.value.MatrixValue;
//import com.github.ifthen2.matrix.vector.BasicMatrixVector;
//import com.github.ifthen2.matrix.vector.BasicMatrixVector.VectorOrientation;
//import com.google.common.base.Preconditions;
//import com.google.common.collect.ArrayTable;
//import com.google.common.collect.ImmutableList;
//import com.google.common.collect.Table;
//import com.google.common.collect.Tables;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Objects;
//import java.util.function.BiPredicate;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Simple Matrix Representation.
// *
// * @author ifthen2
// */
//public class NumberMatrix implements Matrix<MatrixDoubleValue> {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(NumberMatrix.class);
//
//    private static final BiPredicate<Matrix, Matrix> ROW_DIMENSIONS_MATCH =
//        (m1, m2) -> m1.getRowDim() == m2.getRowDim();
//    private static final BiPredicate<Matrix, Matrix> COL_DIMENSIONS_MATCH =
//        (m1, m2) -> m1.getColDim() == m2.getColDim();
//    private static final BiPredicate<Matrix, Matrix> COMPOSITION_DIMENSIONS_MATCH =
//        (m1, m2) -> m1.getColDim() == m2.getRowDim();
//
//    //    private static final Predicate<double[][]> IS_RECTANGULAR_DOUBLE = e -> Arrays
////        .stream(e).allMatch(arr -> arr.length == e.length);
//    private static final BiPredicate<MatrixElement, Integer> ELEMENT_IN_ROW = (e, i) ->
//        e.getRow()
//            == i;
//
//    private final int rowDim;
//    private final int colDim;
//    private final Table<Integer, Integer, MatrixElement<MatrixDoubleValue>> elementTable;
//
//    private NumberMatrix(Table<Integer, Integer, MatrixElement<MatrixDoubleValue>> elementTable) {
//
//        //TODO precon
//        this.elementTable = elementTable;
//
//        this.rowDim = elementTable.rowKeySet().stream().max(Integer::compareTo)
//            .orElseThrow(() -> new IllegalArgumentException("Invalid Table Provided"));
//        this.colDim = elementTable.columnKeySet().stream().max(Integer::compareTo)
//            .orElseThrow(() -> new IllegalArgumentException("Invalid Table Provided"));
//    }
//
//    private NumberMatrix(Collection<MatrixElement<MatrixDoubleValue>> elements) {
//        this(elements.stream()
//            .collect(Collectors.groupingBy(MatrixElement::getRow)) // group by row
//            .entrySet().stream() // stream over rows
//            .map(entry -> entry.getValue()
//                .toArray(new MatrixElement[entry.getKey()])) // each row to array
//            .toArray(MatrixElement[][]::new));
//    }
//
//    public NumberMatrix(double[][] elements) {
//        Preconditions
//            .checkArgument(IS_RECTANGULAR_DOUBLE.test(elements),
//                "array must be rectangular");
//
//        this.elementTable = ArrayTable.create(
//            IntStream.rangeClosed(1, elements.length)
//                .boxed()
//                .collect(Collectors.toList()),
//            IntStream.rangeClosed(1, elements[0].length)
//                .boxed()
//                .collect(Collectors.toList()));
//
//        this.rowDim = elements.length;
//        this.colDim = elements[1].length;
//
//        IntStream.rangeClosed(1, this.rowDim)
//            .forEach(rowIndex -> {
//                IntStream.rangeClosed(1, this.colDim)
//                    .forEach(colIndex -> {
//                        elementTable
//                            .put(rowIndex, colIndex, new MatrixElement(rowIndex, colIndex,
//                                new MatrixDoubleValue(elements[rowIndex - 1][colIndex - 1])));
//                    });
//            });
//    }
//
//    private NumberMatrix(MatrixElement<MatrixDoubleValue>[][] elements) {
//
//        Preconditions
//            .checkArgument(IS_RECTANGULAR.test(elements), "array must be rectangular");
//        Arrays.stream(elements)
//            .flatMap(e -> Arrays.stream(e))
//            .forEach(e -> Preconditions.checkNotNull(e, "no elements can be null"));
//
//        this.elementTable = ArrayTable.create(
//            IntStream.rangeClosed(1, elements.length)
//                .boxed()
//                .collect(Collectors.toList()),
//            IntStream.rangeClosed(1, elements[0].length)
//                .boxed()
//                .collect(Collectors.toList()));
//
//        this.rowDim = elements.length;
//        this.colDim = elements[1].length;
//
//        IntStream.rangeClosed(1, this.rowDim)
//            .forEach(rowIndex -> {
//                IntStream.rangeClosed(1, this.colDim)
//                    .forEach(colIndex -> {
//                        elementTable.put(rowIndex, colIndex, new MatrixElement(rowIndex, colIndex,
//                            new MatrixDoubleValue(
//                                elements[rowIndex - 1][colIndex - 1].getValue().getValue())));
//                    });
//            });
//    }
//
//    @Override
//    public int getRowDim() {
//        return rowDim;
//    }
//
//    @Override
//    public int getColDim() {
//        return colDim;
//    }
//
//    /**
//     * Returns a row vector at the given index.
//     *
//     * @throws IndexOutOfBoundsException if rowIndex is negative or greater than rowDimension
//     */
//    @Override
//    public BasicMatrixVector getRowVector(int rowIndex) {
//
//        Preconditions
//            .checkPositionIndex(rowIndex - 1, this.rowDim - 1, "rowIndex is out of bounds.");
//
//        return new BasicMatrixVector(elementTable
//            .values()
//            .stream()
//            .filter(e -> e.getRow() == rowIndex)
//            .collect(ImmutableList.toImmutableList())
//            .toArray(new MatrixElement[this.colDim]), rowIndex, VectorOrientation.ROW);
//    }
//
//    /**
//     * Returns a column vector at the given index.
//     *
//     * @throws IndexOutOfBoundsException if colIndex is negative or greater than colDimension
//     */
//    @Override
//    public BasicMatrixVector getColumnVector(int colIndex) {
//
//        Preconditions
//            .checkPositionIndex(colIndex - 1, this.colDim - 1,
//                "columnIndex is out of bounds.");
//
//        return new BasicMatrixVector(elementTable
//            .values()
//            .stream()
//            .filter(e -> e.getColumn() == colIndex)
//            .collect(ImmutableList.toImmutableList())
//            .toArray(new MatrixElement[this.rowDim]), colIndex, VectorOrientation.COLUMN);
//    }
//
//    /**
//     * Returns a single element from this Matrix given valid row and column indices
//     *
//     * @param rowIndex row number the requested element is contained in
//     * @param colIndex column number the requested element is contained in
//     * @return the requested element from this Matrix
//     * @throws IndexOutOfBoundsException if either index is invalid
//     */
//    @Override
//    public MatrixElement<MatrixDoubleValue> getElement(int rowIndex, int colIndex) {
//
//        Preconditions
//            .checkPositionIndex(rowIndex - 1, this.rowDim - 1, "colIndex is out of bounds.");
//        Preconditions
//            .checkPositionIndex(colIndex - 1, this.colDim - 1, "colIndex is out of bounds.");
//
//        return elementTable.get(rowIndex, colIndex);
//    }
//
//    /**
//     * Returns an unmodifiable collection of all values, which may contain duplicates. Changes to
//     * the table will update the returned collection.
//     *
//     * @return the elements of this Matrix
//     */
//    @Override
//    public Collection<MatrixElement<MatrixDoubleValue>> getElements() {
//        return elementTable.values();
//    }
//
//    @Override
//    public MatrixElement<MatrixDoubleValue>[][] getRectangularArray() {
//
//        return elementTable.values().stream()
//            .collect(Collectors.groupingBy(MatrixElement::getRow)) // group by row
//            .entrySet().stream() // stream over rows
//            .map(entry -> entry.getValue()
//                .toArray(new MatrixElement[entry.getKey()])) // each row to array
//            .toArray(MatrixElement[][]::new); // and that array[] to an array...
//    }
//
//    /**
//     * Add a Matrix to this one to produce a new Matrix
//     *
//     * @param m2 Matrix to add to this one
//     * @return a new Matrix which is the sum of this Matrix and the other.
//     * @throws IllegalArgumentException if Matrix dimensions don't match
//     */
//    @Override
//    public Matrix<MatrixDoubleValue> add(Matrix<MatrixDoubleValue> m2) {
//
//        Preconditions.checkNotNull(m2, "m2 must not be null");
//        Preconditions
//            .checkArgument(ROW_DIMENSIONS_MATCH.and(COL_DIMENSIONS_MATCH).test(this, m2),
//                "addition is not defined for matrices of different dimensions. this={}x{} m2={}x{}",
//                this.rowDim,
//                this.colDim,
//                m2.getRowDim(),
//                m2.getColDim());
//
//        LOGGER.info("Adding M2 to Matrix");
//
//        return new NumberMatrix(Tables.transformValues(elementTable,
//            e -> e.add(m2.getElement(e.getRow(), e.getColumn()))));
//    }
//
//    /**
//     * Scale this matrix by a constant scalar to produce a new Matrix
//     *
//     * @param scalar number to scale this Matrix by
//     * @return a new Matrix which has been scaled by the scalar
//     */
//    @Override
//    public Matrix<MatrixDoubleValue> scale(MatrixValue<T> scalar) {
//
//        LOGGER.info("Scaling Matrix by {}", scalar);
//
//        return new NumberMatrix(
//            Tables.transformValues(elementTable, e -> e.getValue().multiply(scalar)));
//
//    }
//
//    /**
//     * Compose a new Matrix from this one and another to produce a new Matrix.
//     *
//     * @param m2 Matrix to compose with this one
//     * @return a new Matrix which is the composed of this one and the other
//     * @throws IllegalArgumentException if Matrix dimensions don't match for composition
//     */
//    @Override
//    public Matrix composeMatrix(Matrix m2) {
//
//        Preconditions.checkArgument(COMPOSITION_DIMENSIONS_MATCH.test(this, m2),
//            "composition is not defined when #A-rows != #B-columns. this={}{}, other={}{}",
//            this.rowDim,
//            this.colDim,
//            m2.getRowDim(),
//            m2.getColDim());
//
//        MatrixElement[][] resultElements = new MatrixElement[this.rowDim][m2
//            .getColDim()];
//
//        IntStream.rangeClosed(1, this.rowDim)
//            .forEach(rowIndex -> {
//                IntStream.rangeClosed(1, m2.getColDim())
//                    .forEach(colIndex -> {
//                        resultElements[rowIndex - 1][colIndex - 1] = MatrixElement.builder()
//                            .setRow(rowIndex)
//                            .setColumn(colIndex)
//                            .setValue(this
//                                .getRowVector(rowIndex)
//                                .dotProduct(m2.getColumnVector(colIndex)))
//                            .build();
//                    });
//            });
//
//        return new NumberMatrix(resultElements);
//    }
//
//    /**
//     * Transpose this Matrix (turn all rows into columns) to produce a new Matrix.
//     *
//     * @return a new Matrix which is the transposed version of this one.
//     */
//    @Override
//    public NumberMatrix transpose() {
//
//        LOGGER.info("Transposing...");
//
//        return new NumberMatrix(Tables.transpose(elementTable));
//    }
//
//    /**
//     * Adds one row of this matrix to another to produce a new Matrix
//     *
//     * @param firstRowIndex index of row to be added to
//     * @param secondRowIndex index of row to add
//     * @return a new Matrix with the two requested rows summed in the position of the first
//     * @throws IndexOutOfBoundsException if either index is invalid
//     */
//    @Override
//    public Matrix addRowToRow(int firstRowIndex, int secondRowIndex) {
//
//        Preconditions
//            .checkPositionIndex(firstRowIndex - 1, this.rowDim - 1,
//                "firstRowIndex is out of bounds.");
//        Preconditions
//            .checkPositionIndex(secondRowIndex - 1, this.colDim - 1,
//                "secondRowIndex is out of bounds.");
//
//        LOGGER.info("Adding Row {} to Row {}", firstRowIndex, secondRowIndex);
//
//        return new NumberMatrix(Tables.transformValues(elementTable,
//            e -> ELEMENT_IN_ROW.test(e, firstRowIndex)
//                ? e.add(this.getElement(secondRowIndex, e.getColumn()))
//                : e));
//    }
//
//
//    /**
//     * Scale a single row of this matrix by a scalar value to produce a new Matrix.
//     *
//     * @param rowIndex index of row to be scaled
//     * @param scalar scalar value
//     * @return a new Matrix with the requested row scaled
//     * @throws IndexOutOfBoundsException if rowIndex is invalid
//     */
//    @Override
//    public Matrix scaleRow(int rowIndex, double scalar) {
//
//        Preconditions
//            .checkPositionIndex(rowIndex - 1, this.rowDim - 1, "rowIndex is out of bounds.");
//
//        LOGGER.info("Scaling Row {} By {}", rowIndex, scalar);
//
//        return new NumberMatrix(Tables.transformValues(elementTable,
//            e -> ELEMENT_IN_ROW.test(e, rowIndex)
//                ? e.scale(scalar)
//                : e));
//    }
//
//    /**
//     * Swaps one row of this Matrix with another to produce a new Matrix.
//     *
//     * @param firstRowIndex index of row to be added to
//     * @param secondRowIndex index of row to add
//     * @return a new Matrix with the two requested rows swapped
//     * @throws IndexOutOfBoundsException if either index is invalid
//     */
//    @Override
//    public Matrix swapRow(int firstRowIndex, int secondRowIndex) {
//
//        Preconditions
//            .checkPositionIndex(firstRowIndex - 1, this.rowDim - 1,
//                "firstRowIndex is out of bounds.");
//        Preconditions
//            .checkPositionIndex(secondRowIndex - 1, this.colDim - 1,
//                "secondRowIndex is out of bounds.");
//
//        LOGGER.info("Swapping Row {} and Row {}", firstRowIndex, secondRowIndex);
//
//        return new NumberMatrix(Tables.transformValues(elementTable,
//            e -> {
//                if (ELEMENT_IN_ROW.test(e, firstRowIndex)) {
//                    return new MatrixElement(secondRowIndex, e.getColumn(), e.getValue());
//                } else if (ELEMENT_IN_ROW.test(e, secondRowIndex)) {
//                    return new MatrixElement(firstRowIndex, e.getColumn(), e.getValue());
//                } else {
//                    return e;
//                }
//            }));
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        NumberMatrix that = (NumberMatrix) o;
//        return rowDim == that.rowDim && colDim == that.colDim && elementTable
//            .equals(that.elementTable);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(rowDim, colDim, elementTable);
//    }
//
//    @Override
//    public String toString() {
//        elementTable.toString();
//        StringBuilder sb = new StringBuilder();
//        sb.append("\n");
//        for (int row = 0; row < rowDim; row++) {
//            for (int col = 0; col < colDim; col++) {
//                sb.append(this.getElement(row + 1, col + 1));
//                sb.append(" ");
//            }
//            sb.append("\n");
//        }
//
//        return sb.toString();
//    }
//}