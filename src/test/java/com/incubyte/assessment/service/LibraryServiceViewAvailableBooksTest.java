package com.incubyte.assessment.service;

import com.incubyte.assessment.model.Book;
import com.incubyte.assessment.model.BookDto;
import com.incubyte.assessment.repository.library.LibraryRepository;
import com.incubyte.assessment.repository.library.LibraryRepositoryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.List;

import static com.incubyte.assessment.util.AppConstants.DEFAULT_REPOSITORY_TYPE;
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

    LibraryRepository libraryRepository;

    private Book book1;
    private Book book2;

    private BookDto validBookDto1;
    private BookDto validBookDto2;

    @BeforeEach
    void setUp() {
        //Initializing variables and objects required for the test.

        libraryRepository = LibraryRepositoryFactory.getInstance().createRepository(DEFAULT_REPOSITORY_TYPE);
        libraryService = new LibraryServiceImpl(libraryRepository);

        LibraryRepositoryFactory.getInstance().clearCache();

        validBookDto1 = new BookDto("12345", "Test Book 1", "Author Name 1", 2020, true);
        validBookDto2 = new BookDto("6789", "Test Book 2", "Author Name 2", 2020, true);

        book1 = new Book("12345", "Test Book 1", "Author Name 1", 2020);
        book2 = new Book("6789", "Test Book 2", "Author Name 2", 2020);
    }

    @Test
    void testViewAvailableBooks_Success_Scenario1() {
        //Applying test pre-conditions
        libraryService.addBook(validBookDto1);
        libraryService.addBook(validBookDto2);

        //Performing test operation
        List<BookDto> availableBooks = libraryService.viewAvailableBooks();

        //Asserting test outcomes
        assertEquals(2, availableBooks.size());
        assertEquals(book1.getIsbn(), availableBooks.get(0).isbn());
        assertEquals(book2.getIsbn(), availableBooks.get(1).isbn());
        assertTrue(availableBooks.get(0).isAvailable());
        assertTrue(availableBooks.get(1).isAvailable());
    }

    @Test
    void testViewAvailableBooks_Success_Scenario2() {
        //Applying test pre-conditions
        libraryService.addBook(validBookDto1);
        libraryService.addBook(validBookDto2);
        libraryService.borrowBook(book1.getIsbn());

        //Performing test operation
        List<BookDto> availableBooks = libraryService.viewAvailableBooks();

        //Asserting test outcomes
        assertEquals(1, availableBooks.size());
        assertNotEquals(book1.getIsbn(), availableBooks.get(0).isbn());
        assertEquals(book2.getIsbn(), availableBooks.get(0).isbn());
        assertTrue(availableBooks.get(0).isAvailable());
    }

    @Test
    void testViewAvailableBooks_NoAvailableBooks() {
        //Performing test operation
        List<BookDto> availableBooks = libraryService.viewAvailableBooks();

        //Asserting test outcomes
        assertEquals(0, availableBooks.size());
    }

}
