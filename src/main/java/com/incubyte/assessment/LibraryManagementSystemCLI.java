package com.incubyte.assessment;

import com.incubyte.assessment.exception.CustomException;
import com.incubyte.assessment.model.BookDto;
import com.incubyte.assessment.repository.RepositoryType;
import com.incubyte.assessment.repository.library.LibraryRepository;
import com.incubyte.assessment.repository.library.LibraryRepositoryFactory;
import com.incubyte.assessment.service.LibraryService;
import com.incubyte.assessment.service.LibraryServiceImpl;

import java.util.List;
import java.util.Scanner;

import static com.incubyte.assessment.util.AppConstants.DEFAULT_REPOSITORY_TYPE;

/**
 * Command-Line Interface (CLI) for the Library Management System.
 *
 * <p>This class provides an interface to interact with the library system
 * through the terminal. Users can perform various operations such as adding books,
 * borrowing books, returning books, and viewing available books by selecting options
 * from a menu.</p>
 *
 * <p>Features:</p>
 * <ul>
 *   <li>Displays a menu of operations for the user.</li>
 *   <li>Processes user inputs to perform the corresponding library actions.</li>
 *   <li>Integrates with the {@link LibraryService} for backend functionality.</li>
 * </ul>
 *
 * <p>This class demonstrates how to integrate a library system with a CLI-based
 * interface.</p>
 */
public class LibraryManagementSystemCLI {

    private static LibraryService libraryService;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        initializeLibraryService(DEFAULT_REPOSITORY_TYPE);

        while (true) {
            printMenu();
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1 -> addBook(scanner);
                case 2 -> borrowBook(scanner);
                case 3 -> returnBook(scanner);
                case 4 -> viewAvailableBooks();
                case 5 -> switchRepository(scanner);
                case 6 -> {
                    System.out.println("Exiting program. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    private static void initializeLibraryService(RepositoryType repositoryType) {
        LibraryRepository libraryRepository = LibraryRepositoryFactory.getInstance().createRepository(repositoryType);
        libraryService = new LibraryServiceImpl(libraryRepository);
        System.out.println("Library initialized with " + repositoryType.name() + " repository.");
    }

    private static void printMenu() {
        System.out.println("\nLibrary Management System");
        System.out.println("1. Add Book");
        System.out.println("2. Borrow Book");
        System.out.println("3. Return Book");
        System.out.println("4. View Available Books");
        System.out.println("5. Switch Repository");
        System.out.println("6. Exit Program\n");
    }

    private static void addBook(Scanner scanner) {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter Publication Year: ");
        int publicationYear = Integer.parseInt(scanner.nextLine());

        Boolean defaultIsAvailable = true;

        BookDto bookDto = new BookDto(isbn, title, author, publicationYear, defaultIsAvailable);
        try {
            libraryService.addBook(bookDto);
        } catch (CustomException e) {
            System.out.println("Exception Occurred : " + e.getMessage());
        }
    }

    private static void borrowBook(Scanner scanner) {
        System.out.print("Enter ISBN to borrow: ");
        String isbn = scanner.nextLine();

        try {
            BookDto borrowedBook = libraryService.borrowBook(isbn);
            printBookDetails(borrowedBook);
        } catch (CustomException e) {
            System.out.println("Exception Occurred : " + e.getMessage());
        }
    }

    private static void returnBook(Scanner scanner) {
        System.out.print("Enter ISBN to return: ");
        String isbn = scanner.nextLine();

        try {
            BookDto returnedBook = libraryService.returnBook(isbn);
            printBookDetails(returnedBook);
        } catch (CustomException e) {
            System.out.println("Exception Occurred : " + e.getMessage());
        }
    }

    private static void viewAvailableBooks() {
        List<BookDto> books = libraryService.viewAvailableBooks();
        System.out.println("Total Available Books : " + books.size());
        books.forEach(LibraryManagementSystemCLI::printBookDetails);
    }

    private static void switchRepository(Scanner scanner) {
        System.out.println("Available Repository Types:");

        for (RepositoryType type : RepositoryType.values()) {
            System.out.println("- " + type);
        }

        System.out.print("Enter repository type to switch to: ");
        String repositoryTypeInput = scanner.nextLine();

        try {
            RepositoryType repositoryType = RepositoryType.valueOf(repositoryTypeInput.toUpperCase());
            initializeLibraryService(repositoryType);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid repository type. Please try again.");
        }
    }

    private static void printBookDetails(BookDto bookDto) {

        if (bookDto == null) {
            System.out.println("No book details available.");
            return;
        }

        System.out.println("Book Details :");
        System.out.println("ISBN             : " + bookDto.isbn());
        System.out.println("Title            : " + bookDto.title());
        System.out.println("Author           : " + bookDto.author());
        System.out.println("Publication Year : " + bookDto.publicationYear());
        System.out.println();
    }
}
