package com.incubyte.assessment.repository.library;

import com.incubyte.assessment.model.Book;
import com.incubyte.assessment.repository.BaseRepository;

/**
 * Custom repository interface for managing {@link Book} entities.
 * Extends the generic {@link BaseRepository} interface, providing basic CRUD operations.
 */
public interface LibraryRepository extends BaseRepository<Book> {
}
