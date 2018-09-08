package com.uber.ugsb.schema.model;

import com.uber.ugsb.schema.Vocabulary;
import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.structure.Direction;

import java.util.Objects;

/**
 * An index hint. Currently used only with the JanusGraph back end.
 */
public class Index extends SchemaElement {
    private static final long serialVersionUID = Vocabulary.serialVersionUID;

    private final RelationType relationType;
    private RelationType orderBy;
    private Direction direction;
    private Order order;

    /**
     * Constructs an index hint for the given relation type
     */
    public Index(RelationType relationType) {
        this.relationType = relationType;
    }

    /**
     * Gets the indexed relation type
     */
    public RelationType getRelationType() {
        return relationType;
    }

    /**
     * Gets the ordering constraint of the index, if any
     */
    public RelationType getOrderBy() {
        return orderBy;
    }

    /**
     * Sets the ordering constraint of the index.
     */
    public void setOrderBy(RelationType orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * Gets the direction constraint of the index
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the direction constraint of the index
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Gets the ordering constraint of the index
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Sets the ordering constraint of the index
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public int hashCode() {
        return Objects.hash(relationType, orderBy, direction, order);
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof Index
                && Objects.equals(relationType, ((Index) other).getRelationType())
                && Objects.equals(orderBy, ((Index) other).getOrderBy())
                && Objects.equals(direction, ((Index) other).getDirection())
                && Objects.equals(order, ((Index) other).getOrder());
    }

    @Override
    public String toString() {
        return "Index[" + relationType.getName() + "]";
    }
}
