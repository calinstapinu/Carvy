package org.dealership.repository;

import org.dealership.repository.parsers.EntityParser;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IRepository} that stores repository.data in text files.
 * Each line in the file represents one entity, with fields separated by commas.
 * This repository supports create, read, update, and delete operations.
 *
 * @param <T> the type of entity to manage
 */
public class FileRepository<T> implements IRepository<T> {
    private final File file;
    private final EntityParser<T> parser;

    /**
     * Constructs a new FileRepository for the given file and parser.
     *
     * @param file   the file where repository.data is stored
     * @param parser the parser to serialize and deserialize entities
     */
    public FileRepository(File file, EntityParser<T> parser) {
        this.file = file;
        this.parser = parser;

        try {
            // Ensure the parent directory exists
            File parentDirectory = file.getParentFile();
            if (parentDirectory != null && !parentDirectory.exists()) {
                if (!parentDirectory.mkdirs()) {
                    throw new IOException("Failed to create directories: " + parentDirectory.getAbsolutePath());
                }
            }

            // Ensure the file exists
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new IOException("Failed to create file: " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize FileRepository for file: " + file.getAbsolutePath(), e);
        }
    }

    /**
     * Adds a new entity to the repository.
     *
     * @param entity the entity to create
     */
    @Override
    public void create(T entity) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(parser.toCSV(entity) + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file.", e);
        }
    }

    /**
     * Reads an entity with the specified ID from the repository.
     *
     * @param id the ID of the entity to read
     * @return the entity with the specified ID, or {@code null} if not found
     */
    @Override
    public T read(long id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.lines()
                    .map(parser::fromCSV)
                    .filter(entity -> parser.getId(entity) == id)
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from file.", e);
        }
    }

    /**
     * Reads all entities from the repository.
     *
     * @return a list of all entities in the repository
     */
    @Override
    public List<T> readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.lines()
                    .map(parser::fromCSV)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read all entries from file.", e);
        }
    }

    /**
     * Updates an existing entity in the repository.
     * If the entity does not exist, no changes are made.
     *
     * @param entity the updated entity
     */
    @Override
    public void update(T entity) {
        List<T> allEntities = readAll();
        try (FileWriter writer = new FileWriter(file)) {
            for (T current : allEntities) {
                if (parser.getId(current) == parser.getId(entity)) {
                    writer.write(parser.toCSV(entity) + "\n");
                } else {
                    writer.write(parser.toCSV(current) + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to update the entity in file.", e);
        }
    }

    /**
     * Deletes an entity with the specified ID from the repository.
     * If the entity does not exist, no changes are made.
     *
     * @param id the ID of the entity to delete
     */
    @Override
    public void delete(long id) {
        List<T> allEntities = readAll();
        try (FileWriter writer = new FileWriter(file)) {
            for (T entity : allEntities) {
                if (parser.getId(entity) != id) {
                    writer.write(parser.toCSV(entity) + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete the entity from file.", e);
        }
    }
}