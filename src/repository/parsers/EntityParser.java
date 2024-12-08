package repository.parsers;

/**
 * Interface for parsing entities to/from CSV format.
 *
 * @param <T> the type of entity
 */
public interface EntityParser<T> {

    /**
     * Converts an entity to a CSV-formatted string.
     *
     * @param entity the entity to convert
     * @return the CSV-formatted string
     */
    String toCSV(T entity);

    /**
     * Parses a CSV-formatted string into an entity.
     *
     * @param csv the CSV string
     * @return the parsed entity
     */
    T fromCSV(String csv);

    /**z
     * Retrieves the ID of the entity.
     *
     * @param entity the entity
     * @return the ID of the entity
     */
    long getId(T entity);
}