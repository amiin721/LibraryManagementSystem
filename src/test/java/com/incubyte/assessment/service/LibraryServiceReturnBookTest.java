package com.incubyte.assessment.service;

import com.incubyte.assessment.exception.CustomException;
import com.incubyte.assessment.model.Book;
import com.incubyte.assessment.model.BookDto;
import com.incubyte.assessment.repository.library.LibraryRepository;
import com.incubyte.assessment.repository.library.LibraryRepositoryFactory;
import com.incubyte.assessment.util.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static com.incubyte.assessment.util.AppConstants.DEFAULT_REPOSITORY_TYPE;
import static com.incubyte.assessment.util.MessageFormatUtil.formatMessage;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link LibraryServiceImpl#returnBook(String)} method.
 * This class contains unit tests for returning a book to the library system.
 * <p>It tests various scenarios such as:
 * <p>- Successfully returning a valid book.
 * <p>- Handling errors when trying to return a book which does not exist.
 */
public class LibraryServiceReturnBookTest {

    @InjectMocks
    private LibraryServiceImpl libraryService;

    LibraryRepository libraryRepository;

    private Book validBook;
    private BookDto validBookDto;

    @BeforeEach
    void setUp() {
        //Initializing variables and objects required for the test.

        libraryRepository = LibraryRepositoryFactory.getInstance().createRepository(DEFAULT_REPOSITORY_TYPE);
        libraryService = new LibraryServiceImpl(libraryRepository);
        LibraryRepositoryFactory.getInstance().clearCache();

        validBookDto = new BookDto("12345", "Test Book", "Author Name", 2020, true);

        validBook = new Book("12345", "Test Book", "Author Name", 2020);
    }

    @Test
    void testReturnBook_Success_Scenario1() {
        //Applying test pre-conditions
        String isbn = validBook.getIsbn();
        libraryService.addBook(validBookDto);

        //Performing test operation
        BookDto bookDto = libraryService.returnBook(isbn);

        //Asserting test outcomes
        assertTrue(bookDto.isAvailable());
        assertEquals(isbn, bookDto.isbn());
    }

    @Test
    void testReturnBook_Success_Scenario2() {
        //Applying test pre-conditions
        String isbn = validBook.getIsbn();
        libraryService.addBook(validBookDto);
        libraryService.borrowBook(isbn);

        //Performing test operation
        BookDto bookDto = libraryService.returnBook(isbn);

        //Asserting test outcomes
        assertTrue(bookDto.isAvailable());
        assertEquals(isbn, bookDto.isbn());
    }

    @Test
    void testReturnBook_BookDoesNotExist() {
        //Applying test pre-conditions
        String isbn = validBook.getIsbn();

        //Performing test operation
        CustomException exception = assertThrows(CustomException.class, () -> libraryService.returnBook(isbn));

        //Asserting test outcomes
        assertEquals(formatMessage(AppConstants.BOOK_DOES_NOT_EXIST, isbn), exception.getMessage());
    }

}
