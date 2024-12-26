package com.incubyte.assessment.repository;
import java.util.List;

/**
 * Generic repository interface for managing data operations.
 *
 * @param <T> The type of the entity being managed.
 */
public interface BaseRepository<T> {

    /**
     * Adds an item to the repository.
     *
     * @param item The item to add.
     */
    void add(T item);

    /**
     * Retrieves an item by its unique identifier.
     *
     * @param id The unique identifier of the item.
     * @return The item if found, null otherwise.
     */
    T getById(String id);

    /**
     * Retrieves all items from the repository.
     *
     * @return A list of all items.
     */
    List<T> getAll();

    /**
     * Checks if an item exists in the repository by its unique identifier.
     *
     * @param id The unique identifier of the item.
     * @return True if the item exists, false otherwise.
     */
    boolean existsById(String id);

}