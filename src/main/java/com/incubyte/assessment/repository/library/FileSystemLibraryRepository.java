package com.incubyte.assessment.repository.library;

import com.incubyte.assessment.model.Book;

import java.util.List;

/**
 * File-based implementation of the {@link LibraryRepository} interface.
 * This class is intended to provide CRUD operations for {@link Book} entities
 * by storing and retrieving data from the file system.
 *
 * <p>Note:</p>
 * This class serves as an example to show how the library system can be extended
 * without modifying the existing codebase, adhering to the Open/Closed Principle.
 * The methods are placeholders and not fully implemented.
 */
public class FileSystemLibraryRepository implements LibraryRepository {

    @Override
    public void add(Book book) {
    }

    @Override
    public Book getById(String id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }
}
