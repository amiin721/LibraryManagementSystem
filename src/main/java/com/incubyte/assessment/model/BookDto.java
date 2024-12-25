package com.incubyte.assessment.model;

/**
 * Data Transfer Object (DTO) representing a Book in the library system.
 * This is a Java record that encapsulates the essential details of a book, such as its ISBN, title,
 * author, publication year, and availability status. It is used to transfer book data between layers
 * of the application or with external clients.
 *
 * @Usage:
 * This record is used to transfer book data between layers of the application (service to controller, controller to service)
 * and provides a simplified view of the book entity to external clients while keeping the implementation immutable.
 *
 * @Attributes:
 * <p>- isbn: The unique International Standard Book Number (ISBN) of the book.
 * <p>- title: The title of the book.
 * <p>- author: The author of the book.
 * <p>- publicationYear: The year in which the book was published.
 * <p>- isAvailable: A flag indicating whether the book is available for borrowing.
 *
 */
public record BookDto(String isbn, String title, String author, int publicationYear, Boolean isAvailable) {
}
