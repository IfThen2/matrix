package com.github.ifthen2.matrix.vector;


import com.github.ifthen2.matrix.element.MatrixElement;
import com.github.ifthen2.matrix.element.SimpleMatrixElement;
import com.github.ifthen2.matrix.value.MatrixValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SimpleMatrixVector<T extends MatrixValue> implements MatrixVector<T> {

    private final MatrixElement<T>[] elements;
    private final int index;
    private final VectorOrientation orientation;

    public enum VectorOrientation {
        ROW,
        COLUMN,
        ;
    }

    public SimpleMatrixVector(MatrixElement<T>[] elements, int index,
        VectorOrientation orientation) {

        Preconditions.checkNotNull(elements, "elements must not be null");
        Preconditions.checkNotNull(orientation, "orientation must not be null");
        Preconditions.checkArgument(index > 0, "index must be > 0. passed {}", index);

        this.elements = elements;
        this.index = index;
        this.orientation = orientation;
    }

    @Override
    public MatrixElement<T>[] getElements() {
        return elements;
    }

    @Override
    public MatrixElement<T> getElement(int index) {
        return elements[index - 1];
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getDimension() {
        return elements.length;
    }

    @Override
    public VectorOrientation getOrientation() {
        return orientation;
    }

    @Override
    public MatrixVector<T> scale(T scalar) {

        MatrixElement<T>[] scaledElements = new MatrixElement[this.getDimension()];

        IntStream.range(1, this.getDimension()).forEach(index ->
            scaledElements[index - 1] = new SimpleMatrixElement(getElement(index).getRow(),
                getElement(index).getColumn(), getElement(index).getValue().multiply(scalar)));

        return new SimpleMatrixVector<T>(scaledElements, this.index, this.orientation);
    }

    @Override
    public MatrixVector<T> add(MatrixVector<T> otherVector) {

        Preconditions.checkNotNull(otherVector, "otherVector must not be null");
        Preconditions.checkArgument(this.getDimension() == otherVector.getDimension(),
            "addition is not defined for vectors of different dimensions. this={} otherVector={}",
            this.getDimension(), otherVector.getDimension());

        MatrixElement<T>[] summedElements = new MatrixElement[this.getDimension()];

        IntStream.range(1, this.getDimension()).forEach(index ->
            summedElements[index - 1] = getElement(index).add(otherVector.getElement(index)));

        return new SimpleMatrixVector<>(summedElements, this.index, this.orientation);
    }

    @Override
    public T dotProduct(MatrixVector<T> v2) {
        Preconditions.checkArgument(this.orientation != v2.getOrientation(),
            "dot product is not defined for {} * {}", this.orientation, v2.getOrientation());
        Preconditions.checkArgument(this.getDimension() == v2.getDimension(),
            "Cannot get dot product from vectors with differing dimensions. v1={} v2={}",
            this.getDimension(), v2.getDimension());

        Stream<MatrixElement<T>> zipStream = Streams.zip(
            Arrays.stream(this.elements),
            Arrays.stream(v2.getElements()),
            MatrixElement::multiply);

        MatrixElement<T> result = zipStream.reduce(MatrixElement::add).get();

        return result.getValue();
    }
}