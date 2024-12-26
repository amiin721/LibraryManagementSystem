package com.incubyte.assessment.service;

import com.incubyte.assessment.exception.CustomException;
import com.incubyte.assessment.model.Book;
import com.incubyte.assessment.model.BookDto;
import com.incubyte.assessment.repository.library.LibraryRepositoryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static com.incubyte.assessment.util.AppConstants.BOOK_DOES_NOT_EXIST;
import static com.incubyte.assessment.util.AppConstants.BOOK_NOT_AVAILABLE;
import static com.incubyte.assessment.util.MessageFormatUtil.formatMessage;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link LibraryServiceImpl#borrowBook(String)} method.
 * This class contains unit tests for borrowing a book from the library system.
 * <p>It tests various scenarios such as:
 * <p>- Successfully borrowing a valid book.
 * <p>- Handling errors when trying to borrow a book which is not available or which does not exist.
 */
public class LibraryServiceBorrowBookTest {

    @InjectMocks
    private LibraryServiceImpl libraryService;

    private Book validBook;
    private BookDto validBookDto;

    @BeforeEach
    void setUp() {
        libraryService = new LibraryServiceImpl();
        LibraryRepositoryFactory.getInstance().clearCache();

        validBookDto = new BookDto("12345", "Test Book", "Author Name", 2020, true);

        validBook = new Book("12345", "Test Book", "Author Name", 2020);
    }

    @Test
    void testBorrowBook_Success() {
        String isbn = validBook.getIsbn();

        libraryService.addBook(validBookDto);

        BookDto borrowedBookDto = libraryService.borrowBook(isbn);

        assertNotNull(borrowedBookDto);
        assertEquals(isbn, borrowedBookDto.isbn());
        assertFalse(borrowedBookDto.isAvailable());
    }

    @Test
    void testBorrowBook_BookNotAvailable() {
        String isbn = validBook.getIsbn();

        libraryService.addBook(validBookDto);
        libraryService.borrowBook(isbn);

        CustomException exception = assertThrows(CustomException.class, () -> libraryService.borrowBook(isbn));
        assertEquals(formatMessage(BOOK_NOT_AVAILABLE, isbn), exception.getMessage());
    }

    @Test
    void testBorrowBook_BookDoesNotExist() {
        String isbn = validBook.getIsbn();

        CustomException exception = assertThrows(CustomException.class, () -> libraryService.borrowBook(isbn));
        assertEquals(formatMessage(BOOK_DOES_NOT_EXIST, isbn), exception.getMessage());
    }

}
