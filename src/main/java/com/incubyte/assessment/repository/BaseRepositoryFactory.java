package com.incubyte.assessment.repository;

/**
 * Factory interface for creating repository instances based on the repository type.
 *
 * @param <T> The type of the entity the repository will manage.
 */
public interface BaseRepositoryFactory<T> {

    /**
     * Creates a repository instance for the specified repository type.
     *
     * @param type The type of repository to create.
     * @return A repository instance of the specified type.
     */
    BaseRepository<T> createRepository(RepositoryType type);
}