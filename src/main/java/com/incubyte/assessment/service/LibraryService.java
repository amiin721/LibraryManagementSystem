package com.incubyte.assessment.service;

import com.incubyte.assessment.model.BookDto;

import java.util.List;

public interface LibraryService {
    BookDto addBook(BookDto book);

    BookDto borrowBook(String isbn);

    BookDto returnBook(String isbn);

    List<BookDto> viewAvailableBooks();

}
