package com.incubyte.assessment.service;

import com.incubyte.assessment.model.BookDto;

public interface LibraryService {
    BookDto addBook(BookDto book);

    BookDto borrowBook(String isbn);

    BookDto returnBook(String isbn);

}
