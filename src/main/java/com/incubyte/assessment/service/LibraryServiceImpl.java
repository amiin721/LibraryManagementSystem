package com.incubyte.assessment.service;

import com.incubyte.assessment.exception.CustomException;
import com.incubyte.assessment.model.Book;
import com.incubyte.assessment.model.BookDto;
import com.incubyte.assessment.util.AppConstants;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.incubyte.assessment.util.AppConstants.*;
import static com.incubyte.assessment.util.MessageFormatUtil.displayMessage;
import static com.incubyte.assessment.util.MessageFormatUtil.formatMessage;

public class LibraryServiceImpl implements LibraryService {

    private final Map<String, Book> bookStorage = new HashMap<>();

    private void addBookToLibrary(Book book) {
        bookStorage.put(book.getIsbn(), book);
    }

    private Book getBookFromLibrary(String isbn) {
        return bookStorage.get(isbn);
    }

    private Book convertDtoToEntity(BookDto bookDto) {
        return new Book(bookDto.isbn(), bookDto.title(), bookDto.author(), bookDto.publicationYear());
    }

    private BookDto convertEntityToDto(Book book) {
        return new BookDto(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsAvailable());
    }

    private void validateIfBookAlreadyExists(String isbn) {
        if (bookStorage.containsKey(isbn))
            throw new CustomException(String.format(BOOK_ALREADY_EXISTS, isbn));
    }

    private void validateIfBookDoesNotExist(String isbn) {
        if (!bookStorage.containsKey(isbn)) {
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
        addBookToLibrary(book);

        displayMessage(BOOK_ADDED_SUCCESSFULLY, book.getIsbn());
        return convertEntityToDto(book);
    }

    private Book validateIsBookAvailable(String isbn) {
        validateIfBookDoesNotExist(isbn);

        Book book = bookStorage.get(isbn);
        if (!book.getIsAvailable()) {
            throw new CustomException(formatMessage(AppConstants.BOOK_NOT_AVAILABLE, isbn));
        }

        return book;
    }

    private Book performBorrowProcedureOnBook(String isbn) {
        Book book = validateIsBookAvailable(isbn);
        book.setIsAvailable(false);
        book.setLastBorrowedAt(LocalDateTime.now());

        addBookToLibrary(book);
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

        Book book = getBookFromLibrary(isbn);
        if(book.getIsAvailable()) {
            return book;
        }

        book.setIsAvailable(true);
        book.setLastReturnedAt(LocalDateTime.now());

        addBookToLibrary(book);
        return book;
    }

    @Override
    public BookDto returnBook(String isbn) {
        Book book = performReturnProcedureOnBook(isbn);
        displayMessage(AppConstants.BOOK_RETURNED_SUCCESSFULLY, book.getIsbn());

        return convertEntityToDto(book);
    }

}
