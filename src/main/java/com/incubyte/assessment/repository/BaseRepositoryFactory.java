package com.incubyte.assessment.repository;

public interface BaseRepositoryFactory<T> {
    BaseRepository<T> createRepository(RepositoryType type);
}
