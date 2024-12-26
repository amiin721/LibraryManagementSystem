package com.incubyte.assessment.service;

import com.incubyte.assessment.model.BookDto;

import java.util.List;

/**
 * Interface for Library Service operations.
 * Provides methods to manage books in the library system.
 */
public interface LibraryService {

    /**
     * Adds a new book to the library.
     *
     * @param book The book details to add.
     * @return The added book details.
     */
    BookDto addBook(BookDto book);

    /**
     * Borrows a book from the library by its ISBN.
     *
     * @param isbn The ISBN of the book to borrow.
     * @return The details of the borrowed book.
     */
    BookDto borrowBook(String isbn);

    /**
     * Returns a borrowed book to the library by its ISBN.
     *
     * @param isbn The ISBN of the book to return.
     * @return The details of the returned book.
     */
    BookDto returnBook(String isbn);

    /**
     * Retrieves a list of all available books in the library.
     *
     * @return A list of available books.
     */
    List<BookDto> viewAvailableBooks();
}

