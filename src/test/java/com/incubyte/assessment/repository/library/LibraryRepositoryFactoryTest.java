package com.incubyte.assessment.repository.library;

import com.incubyte.assessment.exception.CustomException;
import com.incubyte.assessment.repository.RepositoryType;
import com.incubyte.assessment.util.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryRepositoryFactoryTest {

    private LibraryRepositoryFactory repositoryFactory;

    @BeforeEach
    void setUp() {
        repositoryFactory = LibraryRepositoryFactory.getInstance();
    }

    @Test
    void testSingletonInstance() {
        LibraryRepositoryFactory anotherInstance = LibraryRepositoryFactory.getInstance();
        assertSame(repositoryFactory, anotherInstance, "LibraryRepositoryFactory should be a singleton");
    }

    @Test
    void testCreateRepository_WithInMemoryType() {
        LibraryRepository repository = repositoryFactory.createRepository(RepositoryType.IN_MEMORY);

        assertNotNull(repository);
        assertInstanceOf(InMemoryLibraryRepository.class, repository, "Repository should be an instance of InMemoryLibraryRepository");
    }

    @Test
    void testCreateRepository_WithDatabaseType() {
        LibraryRepository repository = repositoryFactory.createRepository(RepositoryType.DATABASE);

        assertNotNull(repository);
        assertInstanceOf(DatabaseLibraryRepository.class, repository, "Repository should be an instance of DatabaseLibraryRepository");
    }

    @Test
    void testCreateRepository_WithFileSystemType() {
        LibraryRepository repository = repositoryFactory.createRepository(RepositoryType.FILESYSTEM);

        assertNotNull(repository);
        assertInstanceOf(FileSystemLibraryRepository.class, repository, "Repository should be an instance of FileSystemLibraryRepository");
    }

    @Test
    void testCreateRepository_WithInvalidType() {
        RepositoryType invalidType = null;

        CustomException exception = assertThrows(CustomException.class, () -> repositoryFactory.createRepository(invalidType));

        assertEquals(AppConstants.REPOSITORY_TYPE_CANNOT_BE_NULL,exception.getMessage());
    }

    @Test
    void testRepositoryCacheHit() {
        LibraryRepository repository1 = repositoryFactory.createRepository(RepositoryType.IN_MEMORY);
        LibraryRepository repository2 = repositoryFactory.createRepository(RepositoryType.IN_MEMORY);

        assertSame(repository1, repository2, "Repository should be returned from the cache");
    }

    @Test
    void testClearCache() {
        LibraryRepository repositoryBeforeClear = repositoryFactory.createRepository(RepositoryType.IN_MEMORY);

        repositoryFactory.clearCache();

        LibraryRepository repositoryAfterClear = repositoryFactory.createRepository(RepositoryType.IN_MEMORY);

        assertNotSame(repositoryAfterClear, repositoryBeforeClear, "Repository should be a new instance after cache is cleared");
    }

    @Test
    void testCreateRepository_WithValidTypeAndCache() {
        LibraryRepository firstRepository = repositoryFactory.createRepository(RepositoryType.DATABASE);
        LibraryRepository secondRepository = repositoryFactory.createRepository(RepositoryType.DATABASE);

        assertSame(firstRepository, secondRepository, "Same repository should be returned for the same type due to caching");
    }
}