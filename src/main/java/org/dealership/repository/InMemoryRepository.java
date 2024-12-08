package org.dealership.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Generic in-memory repository for managing entities
 * This repository stores entities in memory and provides basic CRUD operations.
 *
 * @param <T> the type of entity managed by the repository
 */
public class InMemoryRepository<T> implements IRepository<T> {
    private final Map<Long, T> storage = new HashMap<>();
    private long currentId = 0; // Auto-increment ID generator


    /**
     * Adds a new entity to the repository.
     * The entity's ID is auto-generated and assigned
     *
     * @param entity the entity to be added
     * @throws RuntimeException if the entity does not have a `setId` method
     */
    @Override
    public void create(T entity) {
        currentId++;
        try {
            // Using reflection to set the entity ID (assuming `setId` exists)
            entity.getClass().getMethod("setId", long.class).invoke(entity, currentId);
        } catch (Exception e) {
            throw new RuntimeException("Entity must have a `setId` method.", e);
        }
        storage.put(currentId, entity);
    }

    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity to retrieve
     * @return the entity with the specified ID, or {@code null} if not found
     */
    @Override
    public T read(long id) {
        return storage.get(id);
    }

    /**
     * Retrieves all entities in the repository.
     *
     * @return a list of all entities
     */
    @Override
    public List<T> readAll() {
        return new ArrayList<>(storage.values());
    }

    /**
     * Updates an existing entity in the repository.
     * The entity must already exist, and its ID is retrieved using reflection.
     *
     * @param entity the entity to update
     * @throws IllegalArgumentException if the entity does not exist in the repository
     * @throws RuntimeException if the entity does not have a `getId` method
     */
    @Override
    public void update(T entity) {
        try {
            // Using reflection to get the entity ID (assuming `getId` exists)
            long id = (long) entity.getClass().getMethod("getId").invoke(entity);
            if (storage.containsKey(id)) {
                storage.put(id, entity);
            } else {
                throw new IllegalArgumentException("Entity with ID " + id + " does not exist.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Entity must have a `getId` method.", e);
        }
    }

    /**
     * Deletes an entity from the repository by its ID.
     *
     * @param id the ID of the entity to delete
     */
    @Override
    public void delete(long id) {
        storage.remove(id);
    }
}
