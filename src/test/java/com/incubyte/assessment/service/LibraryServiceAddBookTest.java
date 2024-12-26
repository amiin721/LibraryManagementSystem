package com.incubyte.assessment.service;

import com.incubyte.assessment.exception.CustomException;
import com.incubyte.assessment.model.BookDto;
import com.incubyte.assessment.repository.library.LibraryRepository;
import com.incubyte.assessment.repository.library.LibraryRepositoryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static com.incubyte.assessment.util.AppConstants.*;
import static com.incubyte.assessment.util.MessageFormatUtil.formatMessage;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link LibraryServiceImpl#addBook(BookDto)} method.
 * This class contains unit tests for adding a book to the library system.
 * <p>It tests various scenarios such as:
 * <p>- Successfully adding a valid book.
 * <p>- Handling errors when trying to add a book with an existing ISBN.
 * <p>- Validating the input data for book properties like ISBN, title, author, and publication year.
 */
public class LibraryServiceAddBookTest {

    @InjectMocks
    private LibraryServiceImpl libraryService;

    LibraryRepository libraryRepository;

    private BookDto validBookDto;

    @BeforeEach
    void setUp() {
        libraryRepository = LibraryRepositoryFactory.getInstance().createRepository(DEFAULT_REPOSITORY_TYPE);
        libraryService = new LibraryServiceImpl(libraryRepository);
        LibraryRepositoryFactory.getInstance().clearCache();

        validBookDto = new BookDto("12345", "Test Book", "Author Name", 2020, true);
    }

    @Test
    void testAddBook_ValidBook_SuccessScenario() {
        BookDto bookDto = libraryService.addBook(validBookDto);

        assertNotNull(bookDto);
        assertEquals(validBookDto.isbn(), bookDto.isbn());
        assertEquals(validBookDto.title(), bookDto.title());
        assertEquals(validBookDto.author(), bookDto.author());
        assertEquals(validBookDto.publicationYear(), bookDto.publicationYear());
        assertTrue(validBookDto.isAvailable());
    }

    @Test
    void testAddBook_InvalidIsbn_Null() {
        BookDto invalidBookDto = new BookDto(null, "Test Book", "Author Name", 2020, true);

        CustomException exception = assertThrows(CustomException.class, () -> libraryService.addBook(invalidBookDto));

        assertEquals(formatMessage(INVALID_ISBN), exception.getMessage());
    }

    @Test
    void testAddBook_InvalidIsbn_Empty() {
        BookDto invalidBookDto = new BookDto("", "Test Book", "Author Name", 2020, true);

        CustomException exception = assertThrows(CustomException.class, () -> libraryService.addBook(invalidBookDto));

        assertEquals(formatMessage(INVALID_ISBN), exception.getMessage());
    }

    @Test
    void testAddBook_InvalidTitle_Null() {
        BookDto invalidBookDto = new BookDto("12345", null, "Author Name", 2020, true);

        CustomException exception = assertThrows(CustomException.class, () -> libraryService.addBook(invalidBookDto));

        assertEquals(formatMessage(INVALID_TITLE), exception.getMessage());
    }

    @Test
    void testAddBook_InvalidTitle_Empty() {
        BookDto invalidBookDto = new BookDto("12345", "", "Author Name", 2020, true);

        CustomException exception = assertThrows(CustomException.class, () -> libraryService.addBook(invalidBookDto));

        assertEquals(formatMessage(INVALID_TITLE), exception.getMessage());
    }

    @Test
    void testAddBook_InvalidAuthor_Null() {
        BookDto invalidBookDto = new BookDto("12345", "Test Book", null, 2020, true);

        CustomException exception = assertThrows(CustomException.class, () -> libraryService.addBook(invalidBookDto));

        assertEquals(formatMessage(INVALID_AUTHOR), exception.getMessage());
    }

    @Test
    void testAddBook_InvalidAuthor_Empty() {
        BookDto invalidBookDto = new BookDto("12345", "Test Book", "", 2020, true);

        CustomException exception = assertThrows(CustomException.class, () -> libraryService.addBook(invalidBookDto));

        assertEquals(formatMessage(INVALID_AUTHOR), exception.getMessage());
    }

    @Test
    void testAddBook_InvalidPublicationYear_Future() {
        BookDto invalidBookDto = new BookDto("12345", "Test Book", "Author Name", 9999, true);

        CustomException exception = assertThrows(CustomException.class, () -> libraryService.addBook(invalidBookDto));

        assertEquals(formatMessage(INVALID_PUBLICATION_YEAR), exception.getMessage());
    }

    @Test
    void testAddBook_InvalidPublicationYear_Zero() {
        BookDto invalidBookDto = new BookDto("12345", "Test Book", "Author Name", 0, true);

        CustomException exception = assertThrows(CustomException.class, () -> libraryService.addBook(invalidBookDto));

        assertEquals(formatMessage(INVALID_PUBLICATION_YEAR), exception.getMessage());
    }

    @Test
    void testAddBook_BookAlreadyExists_FailureScenario() {
        libraryService.addBook(validBookDto);

        CustomException exception = assertThrows(CustomException.class, () -> libraryService.addBook(validBookDto));

        assertEquals(formatMessage(BOOK_ALREADY_EXISTS, validBookDto.isbn()), exception.getMessage());
    }
}
