package com.incubyte.assessment.service;

import com.incubyte.assessment.model.Book;
import com.incubyte.assessment.model.BookDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link LibraryServiceImpl#viewAvailableBooks()} method.
 * This class contains unit tests for viewing the list of available books in the library system.
 * <p>It tests various scenarios such as:
 * <p>- Successfully returning the list of available books.
 * <p>- Handling proper response when there are no book available in the library.
 */
public class LibraryServiceViewAvailableBooksTest {

    @InjectMocks
    private LibraryServiceImpl libraryService;

    private Book book1;
    private Book book2;

    private BookDto validBookDto1;
    private BookDto validBookDto2;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validBookDto1 = new BookDto("12345", "Test Book 1", "Author Name 1", 2020, true);
        validBookDto2 = new BookDto("6789", "Test Book 2", "Author Name 2", 2020, true);

        book1 = new Book("12345", "Test Book 1", "Author Name 1", 2020);
        book2 = new Book("6789", "Test Book 2", "Author Name 2", 2020);
    }

    @Test
    void testViewAvailableBooks_Success_Scenario1() {
        libraryService.addBook(validBookDto1);
        libraryService.addBook(validBookDto2);

        List<BookDto> availableBooks = libraryService.viewAvailableBooks();

        assertEquals(2, availableBooks.size());
        assertEquals(book1.getIsbn(), availableBooks.get(0).isbn());
        assertEquals(book2.getIsbn(), availableBooks.get(1).isbn());
        assertTrue(availableBooks.get(0).isAvailable());
        assertTrue(availableBooks.get(1).isAvailable());
    }

    @Test
    void testViewAvailableBooks_Success_Scenario2() {
        libraryService.addBook(validBookDto1);
        libraryService.addBook(validBookDto2);

        libraryService.borrowBook(book1.getIsbn());

        List<BookDto> availableBooks = libraryService.viewAvailableBooks();

        assertEquals(1, availableBooks.size());
        assertNotEquals(book1.getIsbn(), availableBooks.get(0).isbn());
        assertEquals(book2.getIsbn(), availableBooks.get(0).isbn());
        assertTrue(availableBooks.get(0).isAvailable());
    }

    @Test
    void testViewAvailableBooks_NoAvailableBooks() {
        List<BookDto> availableBooks = libraryService.viewAvailableBooks();
        assertEquals(0, availableBooks.size());
    }

}
