package com.incubyte.assessment.service;

import com.incubyte.assessment.exception.CustomException;
import com.incubyte.assessment.model.Book;
import com.incubyte.assessment.model.BookDto;
import com.incubyte.assessment.repository.RepositoryType;
import com.incubyte.assessment.repository.library.LibraryRepository;
import com.incubyte.assessment.repository.library.LibraryRepositoryFactory;
import com.incubyte.assessment.util.AppConstants;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.incubyte.assessment.util.AppConstants.*;
import static com.incubyte.assessment.util.MessageFormatUtil.displayMessage;
import static com.incubyte.assessment.util.MessageFormatUtil.formatMessage;

/**
 * Implementation of the {@link LibraryService} interface for managing library operations.
 *
 * <p>This class provides methods for adding, borrowing, returning, and viewing available books
 * in the library system.</p>
 *
 * <p>This implementation demonstrates constructor-based dependency injection for repository selection,
 * either using the default in-memory repository or a specified type.</p>
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@link LibraryRepository}: For CRUD operations on books.</li>
 *   <li>{@link LibraryRepositoryFactory}: For creating and managing repository instances.</li>
 *   <li>{@link Book} and {@link BookDto}: Domain and data transfer objects for books.</li>
 *   <li>{@link AppConstants} and utility classes for reusable constants and formatting.</li>
 * </ul>
 */
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;

    //Constructor based dependency injection
    public LibraryServiceImpl() {
        this.libraryRepository = LibraryRepositoryFactory.getInstance().createRepository(RepositoryType.IN_MEMORY);
    }

    //Constructor based dependency injection
    public LibraryServiceImpl(RepositoryType repositoryType) {
        if (repositoryType == null) {
            throw new CustomException(formatMessage(REPOSITORY_TYPE_CANNOT_BE_NULL));
        }
        this.libraryRepository = LibraryRepositoryFactory.getInstance().createRepository(repositoryType);
    }

    private Book convertDtoToEntity(BookDto bookDto) {
        return new Book(bookDto.isbn(), bookDto.title(), bookDto.author(), bookDto.publicationYear());
    }

    private BookDto convertEntityToDto(Book book) {
        return new BookDto(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsAvailable());
    }

    private void validateIfBookAlreadyExists(String isbn) {
        if (libraryRepository.existsById(isbn))
            throw new CustomException(String.format(BOOK_ALREADY_EXISTS, isbn));
    }

    private void validateIfBookDoesNotExist(String isbn) {
        if (!libraryRepository.existsById(isbn)) {
            throw new CustomException(formatMessage(AppConstants.BOOK_DOES_NOT_EXIST, isbn));
        }
    }

    private void validateAddBookRequest(BookDto bookDto) {
        if (bookDto.isbn() == null || bookDto.isbn().isEmpty())
            throw new CustomException(INVALID_ISBN);

        if (bookDto.title() == null || bookDto.title().isEmpty())
            throw new CustomException(INVALID_TITLE);

        if (bookDto.author() == null || bookDto.author().isEmpty())
            throw new CustomException(INVALID_AUTHOR);

        if (bookDto.publicationYear() <= 0 || Calendar.getInstance().get(Calendar.YEAR) < bookDto.publicationYear())
            throw new CustomException(INVALID_PUBLICATION_YEAR);

        validateIfBookAlreadyExists(bookDto.isbn());
    }

    @Override
    public BookDto addBook(BookDto bookDto) {

        validateAddBookRequest(bookDto);

        Book book = convertDtoToEntity(bookDto);
        libraryRepository.add(book);

        displayMessage(BOOK_ADDED_SUCCESSFULLY, book.getIsbn());
        return convertEntityToDto(book);
    }

    private Book validateIsBookAvailable(String isbn) {
        validateIfBookDoesNotExist(isbn);

        Book book = libraryRepository.getById(isbn);
        if (!book.getIsAvailable()) {
            throw new CustomException(formatMessage(AppConstants.BOOK_NOT_AVAILABLE, isbn));
        }

        return book;
    }

    private Book performBorrowProcedureOnBook(String isbn) {
        Book book = validateIsBookAvailable(isbn);
        book.setIsAvailable(false);
        book.setLastBorrowedAt(LocalDateTime.now());

        libraryRepository.add(book);
        return book;
    }

    @Override
    public BookDto borrowBook(String isbn) {
        Book book = performBorrowProcedureOnBook(isbn);
        displayMessage(AppConstants.BOOK_BORROWED_SUCCESSFULLY, book.getIsbn());

        return convertEntityToDto(book);
    }

    private Book performReturnProcedureOnBook(String isbn) {
        validateIfBookDoesNotExist(isbn);

        Book book = libraryRepository.getById(isbn);
        if (book.getIsAvailable()) {
            return book;
        }

        book.setIsAvailable(true);
        book.setLastReturnedAt(LocalDateTime.now());

        libraryRepository.add(book);
        return book;
    }

    @Override
    public BookDto returnBook(String isbn) {
        Book book = performReturnProcedureOnBook(isbn);
        displayMessage(AppConstants.BOOK_RETURNED_SUCCESSFULLY, book.getIsbn());

        return convertEntityToDto(book);
    }

    private List<BookDto> fetchAvailableBookListUsingFilter(List<Book> bookList) {
        return new ArrayList<>(bookList).stream()
                .map(this::convertEntityToDto)
                .filter(BookDto::isAvailable)
                .toList();
    }

    @Override
    public List<BookDto> viewAvailableBooks() {
        List<Book> bookList = libraryRepository.getAll();

        if (bookList.isEmpty()) {
            displayMessage(NO_AVAILABLE_BOOKS);
            return new ArrayList<>();
        }

        displayMessage(FETCHED_AVAILABLE_BOOK_LIST_SUCCESSFULLY);
        return fetchAvailableBookListUsingFilter(bookList);
    }

}
