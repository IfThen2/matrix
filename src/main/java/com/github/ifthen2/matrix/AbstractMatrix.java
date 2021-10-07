package com.github.ifthen2.matrix;

import com.github.ifthen2.matrix.element.MatrixElement;
import com.github.ifthen2.matrix.element.SimpleMatrixElement;
import com.github.ifthen2.matrix.value.MatrixValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayTable;
import com.google.common.collect.Table;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AbstractMatrix<T extends MatrixValue> implements Matrix<T> {

    final int rowDim;
    final int colDim;
    final Table<Integer, Integer, MatrixElement<T>> elementTable;

    public AbstractMatrix(T[][] elements) {
        Preconditions
            .checkArgument(Matrix.IS_RECTANGULAR.test(elements),
                "array must be rectangular");

        this.elementTable = ArrayTable.create(
            IntStream.rangeClosed(1, elements.length)
                .boxed()
                .collect(Collectors.toList()),
            IntStream.rangeClosed(1, elements[0].length)
                .boxed()
                .collect(Collectors.toList()));

        this.rowDim = elements.length;
        this.colDim = elements[1].length;

        IntStream.rangeClosed(1, this.rowDim)
            .forEach(rowIndex -> {
                IntStream.rangeClosed(1, this.colDim)
                    .forEach(colIndex -> {
                        elementTable
                            .put(rowIndex, colIndex, new SimpleMatrixElement(rowIndex, colIndex,
                                elements[rowIndex - 1][colIndex - 1]));
                    });
            });
    }

    public AbstractMatrix(MatrixElement<T>[][] elements) {

        Preconditions
            .checkArgument(IS_RECTANGULAR.test(elements), "array must be rectangular");
        Arrays.stream(elements)
            .flatMap(e -> Arrays.stream(e))
            .forEach(e -> Preconditions.checkNotNull(e, "no elements can be null"));

        this.elementTable = ArrayTable.create(
            IntStream.rangeClosed(1, elements.length)
                .boxed()
                .collect(Collectors.toList()),
            IntStream.rangeClosed(1, elements[0].length)
                .boxed()
                .collect(Collectors.toList()));

        this.rowDim = elements.length;
        this.colDim = elements[1].length;

        IntStream.rangeClosed(1, this.rowDim)
            .forEach(rowIndex -> {
                IntStream.rangeClosed(1, this.colDim)
                    .forEach(colIndex -> {
                        elementTable
                            .put(rowIndex, colIndex, new SimpleMatrixElement<T>(rowIndex, colIndex,
                                elements[rowIndex - 1][colIndex - 1].getValue()));
                    });
            });
    }

    @Override
    public Collection<MatrixElement<T>> getElements() {
        return null;
    }

    @Override
    public int getRowDim() {
        return rowDim;
    }

    @Override
    public int getColDim() {
        return 0;
    }
}
