package com.incubyte.assessment.repository.library;

import com.incubyte.assessment.exception.CustomException;
import com.incubyte.assessment.model.Book;
import com.incubyte.assessment.repository.BaseRepositoryFactory;
import com.incubyte.assessment.repository.RepositoryType;

import java.util.EnumMap;

import static com.incubyte.assessment.util.AppConstants.REPOSITORY_TYPE_CANNOT_BE_NULL;
import static com.incubyte.assessment.util.MessageFormatUtil.formatMessage;

/**
 * Factory class for creating and managing library repository instances.
 * Implements the singleton pattern to ensure a single instance of the factory is used.
 */
public class LibraryRepositoryFactory implements BaseRepositoryFactory<Book> {

    //Eager initialization of the Singleton Instance
    private static final LibraryRepositoryFactory SINGLETON_INSTANCE = new LibraryRepositoryFactory();

    private final EnumMap<RepositoryType, LibraryRepository> cache = new EnumMap<>(RepositoryType.class);

    //Keeping constructor private to avoid class instantiation from outside this class.
    private LibraryRepositoryFactory() {
    }

    /**
     * Retrieves the singleton instance of the factory.
     *
     * @return The singleton instance of LibraryRepositoryFactory.
     */
    public static LibraryRepositoryFactory getInstance() {
        return SINGLETON_INSTANCE;
    }

    /**
     * Creates or retrieves a cached repository instance based on the specified repository type.
     *
     * @param type The type of repository to create.
     * @return A LibraryRepository instance of the specified type.
     * @throws CustomException If the specified repository type is invalid.
     */
    @Override
    public LibraryRepository createRepository(RepositoryType type) {
        if (type == null) {
            throw new CustomException(formatMessage(REPOSITORY_TYPE_CANNOT_BE_NULL));
        }

        if (cache.containsKey(type)) {
            return cache.get(type);
        }

        LibraryRepository repository = switch (type) {
            case IN_MEMORY -> new InMemoryLibraryRepository();
            case DATABASE -> new DatabaseLibraryRepository();
            case FILESYSTEM -> new FileSystemLibraryRepository();
        };

        cache.put(type, repository);
        return repository;
    }

    /**
     * Clears the repository cache for fresh repository instances.
     */
    public void clearCache() {
        cache.clear();
    }

}
