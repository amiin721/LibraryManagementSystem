package com.incubyte.assessment.service;

import com.incubyte.assessment.exception.CustomException;
import com.incubyte.assessment.model.Book;
import com.incubyte.assessment.model.BookDto;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.incubyte.assessment.util.AppConstants.*;

public class LibraryServiceImpl implements LibraryService {

    private final Map<String, Book> bookStorage = new HashMap<>();

    @Override
    public BookDto addBook(BookDto bookDto) {

        if (bookDto.isbn() == null || bookDto.isbn().isEmpty())
            throw new CustomException(INVALID_ISBN);

        if (bookDto.title() == null || bookDto.title().isEmpty())
            throw new CustomException(INVALID_TITLE);

        if (bookDto.author() == null || bookDto.author().isEmpty())
            throw new CustomException(INVALID_AUTHOR);

        if (bookDto.publicationYear() <= 0 || Calendar.getInstance().get(Calendar.YEAR) < bookDto.publicationYear())
            throw new CustomException(INVALID_PUBLICATION_YEAR);

        if(bookStorage.containsKey(bookDto.isbn()))
            throw new CustomException(String.format(BOOK_ALREADY_EXISTS, bookDto.isbn()));

        Book book = new Book(bookDto.isbn(), bookDto.title(), bookDto.author(), bookDto.publicationYear());
        bookStorage.put(bookDto.isbn(), book);

        BookDto response = new BookDto(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsAvailable());
        return response;
    }

}
