package com.incubyte.assessment.util;

/**
 * Utility class that holds constant values used throughout the library system.
 * This class provides commonly used message templates and error messages to ensure consistency
 * across the application. It avoids hardcoding values throughout the codebase, making it easier to
 * manage and update messages.
 */
public class AppConstants {

    public static final String BOOK_MESSAGE_PREFIX = "Book with ISBN %s";

    //Common Error Constants
    public final static String MESSAGE_FORMAT_ARGUMENT_COUNT_MISMATCH = "Mismatch between placeholders and arguments. Expected %s arguments but got %s";

    //Library Management Error Constants
    public final static String BOOK_ALREADY_EXISTS = BOOK_MESSAGE_PREFIX + " already exists in the library.";
    public final static String INVALID_ISBN = "ISBN cannot be null or empty.";
    public final static String INVALID_TITLE = "Title cannot be null or empty.";
    public final static String INVALID_AUTHOR = "Author cannot be null or empty.";
    public final static String INVALID_PUBLICATION_YEAR = "Invalid publication year.";

}
