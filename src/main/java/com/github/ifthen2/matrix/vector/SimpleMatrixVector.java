package com.github.ifthen2.matrix.vector;


import static java.util.stream.Collectors.toSet;

import com.github.ifthen2.matrix.element.MatrixElement;
import com.github.ifthen2.matrix.element.SimpleMatrixElement;
import com.github.ifthen2.matrix.value.MatrixValue;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Streams;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Generic implementation of MatrixVector
 */
public class SimpleMatrixVector<T extends MatrixValue<T>> implements MatrixVector<T> {

    private final Set<MatrixElement<T>> elements;
    private final int index;
    private final VectorOrientation orientation;

    public enum VectorOrientation { // TODO CC the differentiation seems so arbitrary and abstract.
        ROW,
        COLUMN,
    }

    public SimpleMatrixVector(Set<MatrixElement<T>> elements, int index,
        VectorOrientation orientation) {

        Preconditions.checkNotNull(elements, "elements must not be null");
        Preconditions.checkNotNull(orientation, "orientation must not be null");
        Preconditions.checkArgument(index > 0, "index must be > 0. passed {}", index);

        this.elements = elements;
        this.index = index;
        this.orientation = orientation;
    }

    @Override
    public Set<MatrixElement<T>> getElements() {
        return elements;
    }

    @Override
    public MatrixElement<T> getElement(int index) {
        return null;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getDimension() {
        return elements.size();
    }

    @Override
    public VectorOrientation getOrientation() {
        return orientation;
    }

    @Override
    public MatrixVector<T> scale(T scalar) {

        Set<MatrixElement<T>> resultElements = elements.stream().map(
            e -> new SimpleMatrixElement<>(e.getRow(), e.getColumn(),
                e.getValue().multiply(scalar))).collect(toSet());

        return new SimpleMatrixVector<>(resultElements, this.index, this.orientation);
    }

    @Override
    public MatrixVector<T> add(MatrixVector<T> v2) {

        Preconditions.checkNotNull(v2, "v2 must not be null");
        Preconditions.checkArgument(this.getDimension() == v2.getDimension(),
            "addition is not defined for vectors of different dimensions. this={} v2={}",
            this.getDimension(), v2.getDimension());

        Set<MatrixElement<T>> resultElements = Streams
            .zip(elements.stream(), v2.getElements().stream(),
                MatrixElement::add).collect(toSet());

        return new SimpleMatrixVector<>(resultElements, this.index, this.orientation);
    }

    @Override
    public T dotProduct(MatrixVector<T> v2) {
        Preconditions.checkArgument(this.orientation != v2.getOrientation(),
            "dot product is not defined for {} * {}", this.orientation, v2.getOrientation());
        Preconditions.checkArgument(this.getDimension() == v2.getDimension(),
            "Cannot get dot product from vectors with differing dimensions. v1={} v2={}",
            this.getDimension(), v2.getDimension());

        Stream<MatrixElement<T>> zipStream = Streams.zip(
            this.elements.stream().sorted(MatrixElement.ROW_COL_ORDER),
            v2.getElements().stream().sorted(MatrixElement.ROW_COL_ORDER),
            MatrixElement::multiply);

        Optional<MatrixElement<T>> result = zipStream.reduce(MatrixElement::add);

        return result.map(MatrixElement::getValue)
            .orElseThrow(() -> new RuntimeException("dot product failed"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleMatrixVector<?> that = (SimpleMatrixVector<?>) o;
        return index == that.index && Objects.equal(elements, that.elements)
            && orientation == that.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(elements, index, orientation);
    }

    @Override
    public String toString() {
        return "SimpleMatrixVector{" +
            "elements=" + elements +
            ", index=" + index +
            ", orientation=" + orientation +
            '}';
    }
}