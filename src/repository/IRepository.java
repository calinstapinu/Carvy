package repository;

import java.util.List;

/**
 * A generic interface defining CRUD (Create, Read, Update, Delete) operations
 * for managing entities of type {@code T}.
 *
 * @param <T> the type of entity managed by the repository
 */
public interface IRepository<T> {
    void create(T entity);                // Add a new entity
    T read(long id);                      // Retrieve an entity by ID
    List<T> readAll();                    // Retrieve all entities
    void update(T entity);                // Update an existing entity
    void delete(long id);                 // Delete an entity by ID
}
