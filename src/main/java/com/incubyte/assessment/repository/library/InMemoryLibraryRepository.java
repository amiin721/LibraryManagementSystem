package com.incubyte.assessment.repository.library;

import com.incubyte.assessment.model.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of the {@link LibraryRepository} interface.
 * Stores and manages {@link Book} entities using a {@link HashMap}.
 */
public class InMemoryLibraryRepository implements LibraryRepository {

    private final Map<String, Book> bookStorage = new HashMap<>();

    @Override
    public void add(Book book) {
        bookStorage.put(book.getIsbn(), book);
    }

    @Override
    public Book getById(String id) {
        return bookStorage.get(id);
    }

    @Override
    public List<Book> getAll() {
        return new ArrayList<>(bookStorage.values());
    }

    @Override
    public boolean existsById(String id) {
        return bookStorage.containsKey(id);
    }

}
