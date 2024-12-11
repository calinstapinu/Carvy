package org.dealership.model;

/**
 * Interface representing an entity with a unique ID.
 * All entities that need to be stored in repositories should implement this interface.
 */
public interface HasID {
    /**
     * Gets the unique ID of the entity.
     *
     * @return the unique ID
     */
    long getId();

    /**
     * Sets the unique ID of the entity.
     *
     * @param id the unique ID to set
     */
    void setId(long id);
}
