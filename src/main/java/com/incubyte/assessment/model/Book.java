package com.incubyte.assessment.model;

import java.time.LocalDateTime;

/**
 *
 * Represents a Book entity in the library system.
 * This class contains the details of a book, such as its ISBN, title, author, publication year,
 * and the availability status. It also tracks the last time the book was borrowed and returned.
 * This entity is primarily used for interaction with the repository layer, where it is stored
 * and retrieved from a data source.
 *
 * @Usage:
 * This class serves as the data model that is passed between the service layer and repository layer in the library system.
 * The repository is responsible for saving, retrieving, and modifying instances of the Book class.
 *
 * @Attributes:
 * <p>- isbn: The unique International Standard Book Number (ISBN) of the book.
 * <p>- title: The title of the book.
 * <p>- author: The author of the book.
 * <p>- publicationYear: The year in which the book was published.
 * <p>- isAvailable: A flag indicating whether the book is available for borrowing or not.
 * <p>- lastBorrowedAt: The timestamp representing the last time the book was borrowed.
 * <p>- lastReturnedAt: The timestamp representing the last time the book was returned.
 * <p>
 */
public class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private final int publicationYear;
    private Boolean isAvailable;
    private LocalDateTime lastBorrowedAt;
    private LocalDateTime lastReturnedAt;

    public Book(String isbn, String title, String author, int publicationYear) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isAvailable = true;
    }

    // Getters
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public LocalDateTime getLastBorrowedAt() {
        return lastBorrowedAt;
    }

    public LocalDateTime getLastReturnedAt() {
        return lastReturnedAt;
    }

    //Setters
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setLastBorrowedAt(LocalDateTime lastBorrowedAt) {
        this.lastBorrowedAt = lastBorrowedAt;
    }

    public void setLastReturnedAt(LocalDateTime lastReturnedAt) {
        this.lastReturnedAt = lastReturnedAt;
    }
}
