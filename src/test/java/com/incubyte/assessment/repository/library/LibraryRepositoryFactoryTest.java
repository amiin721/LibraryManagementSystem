package com.incubyte.assessment.repository.library;

import com.incubyte.assessment.exception.CustomException;
import com.incubyte.assessment.repository.RepositoryType;
import com.incubyte.assessment.util.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link LibraryRepositoryFactory} class.
 *
 * <p>This test class verifies the functionality and behavior of the
 * {@link LibraryRepositoryFactory}, including its singleton implementation,
 * repository creation logic, caching mechanism, and exception handling.</p>
 */
public class LibraryRepositoryFactoryTest {

    private LibraryRepositoryFactory repositoryFactory;

    @BeforeEach
    void setUp() {
        //Initializing variables and objects required for the test.

        repositoryFactory = LibraryRepositoryFactory.getInstance();
    }

    @Test
    void testSingletonInstance() {
        //Performing test operation
        LibraryRepositoryFactory anotherInstance = LibraryRepositoryFactory.getInstance();

        //Asserting test outcomes
        assertSame(repositoryFactory, anotherInstance, "LibraryRepositoryFactory should be a singleton");
    }

    @Test
    void testCreateRepository_WithInMemoryType() {
        //Performing test operation
        LibraryRepository repository = repositoryFactory.createRepository(RepositoryType.IN_MEMORY);

        //Asserting test outcomes
        assertNotNull(repository);
        assertInstanceOf(InMemoryLibraryRepository.class, repository, "Repository should be an instance of InMemoryLibraryRepository");
    }

    @Test
    void testCreateRepository_WithDatabaseType() {
        //Performing test operation
        LibraryRepository repository = repositoryFactory.createRepository(RepositoryType.DATABASE);

        //Asserting test outcomes
        assertNotNull(repository);
        assertInstanceOf(DatabaseLibraryRepository.class, repository, "Repository should be an instance of DatabaseLibraryRepository");
    }

    @Test
    void testCreateRepository_WithFileSystemType() {
        //Performing test operation
        LibraryRepository repository = repositoryFactory.createRepository(RepositoryType.FILESYSTEM);

        //Asserting test outcomes
        assertNotNull(repository);
        assertInstanceOf(FileSystemLibraryRepository.class, repository, "Repository should be an instance of FileSystemLibraryRepository");
    }

    @Test
    void testCreateRepository_WithInvalidType() {
        //Applying test pre-conditions
        RepositoryType invalidType = null;

        //Performing test operation
        CustomException exception = assertThrows(CustomException.class, () -> repositoryFactory.createRepository(invalidType));

        //Asserting test outcomes
        assertEquals(AppConstants.REPOSITORY_TYPE_CANNOT_BE_NULL,exception.getMessage());
    }

    @Test
    void testRepositoryCacheHit() {
        //Performing test operation
        LibraryRepository repository1 = repositoryFactory.createRepository(RepositoryType.IN_MEMORY);
        LibraryRepository repository2 = repositoryFactory.createRepository(RepositoryType.IN_MEMORY);

        //Asserting test outcomes
        assertSame(repository1, repository2, "Repository should be returned from the cache");
    }

    @Test
    void testClearCache() {
        //Applying test pre-conditions
        LibraryRepository repositoryBeforeClear = repositoryFactory.createRepository(RepositoryType.IN_MEMORY);
        repositoryFactory.clearCache();

        //Performing test operation
        LibraryRepository repositoryAfterClear = repositoryFactory.createRepository(RepositoryType.IN_MEMORY);

        //Asserting test outcomes
        assertNotSame(repositoryAfterClear, repositoryBeforeClear, "Repository should be a new instance after cache is cleared");
    }

    @Test
    void testCreateRepository_WithValidTypeAndCache() {
        //Performing test operation
        LibraryRepository firstRepository = repositoryFactory.createRepository(RepositoryType.DATABASE);
        LibraryRepository secondRepository = repositoryFactory.createRepository(RepositoryType.DATABASE);

        //Asserting test outcomes
        assertSame(firstRepository, secondRepository, "Same repository should be returned for the same type due to caching");
    }
}