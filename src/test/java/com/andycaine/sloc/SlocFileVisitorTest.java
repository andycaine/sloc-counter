package com.andycaine.sloc;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SlocFileVisitorTest {

    private static final BasicFileAttributes IGNORE = null;

    private FileSlocCounter fileSlocCounter = mock(FileSlocCounter.class);

    @Test
    public void shouldSkipSubtreeOfDirThatMatchesAPathToExclude() throws Exception {
        Path pathToIgnore = mock(Path.class);
        SlocFileVisitor visitor = createSUT(pathMatcherFor(pathToIgnore));

        assertEquals(FileVisitResult.SKIP_SUBTREE, visitor.preVisitDirectory(pathToIgnore, IGNORE));
    }

    @Test
    public void shouldSumSlocOfAllVisitedFiles() throws Exception {
        SlocFileVisitor visitor = new SlocFileVisitor(fileSlocCounter, emptyList());

        visitor.visitFile(pathWithSloc(10), IGNORE);
        visitor.visitFile(pathWithSloc(5), IGNORE);

        assertEquals(15, visitor.getSloc());
    }

    @Test
    public void shouldNotAddSlocForPathsMatchingAPathToExclude() throws Exception {
        Path pathToIgnore = pathWithSloc(10);
        SlocFileVisitor visitor = createSUT(pathMatcherFor(pathToIgnore));

        visitor.visitFile(pathToIgnore, IGNORE);
        visitor.visitFile(pathWithSloc(5), IGNORE);

        assertEquals(5, visitor.getSloc());
    }

    @Test
    public void shouldAlwaysContinueAfterVisitingAFile() throws Exception {
        Path pathToIgnore = pathWithSloc(10);
        SlocFileVisitor visitor = createSUT(pathMatcherFor(pathToIgnore));

        assertEquals(FileVisitResult.CONTINUE, visitor.visitFile(pathToIgnore, IGNORE));
        assertEquals(FileVisitResult.CONTINUE, visitor.visitFile(pathWithSloc(5), IGNORE));
    }

    private PathMatcher pathMatcherFor(Path path) {
        PathMatcher pathMatcher = mock(PathMatcher.class);
        when(pathMatcher.matches(path)).thenReturn(true);
        return pathMatcher;
    }

    private Path pathWithSloc(int sloc) throws IOException {
        Path path = mock(Path.class);
        when(fileSlocCounter.sloc(path)).thenReturn(sloc);
        return path;
    }

    private SlocFileVisitor createSUT(PathMatcher... pathsToIgnore) {
        return new SlocFileVisitor(fileSlocCounter, asList(pathsToIgnore));
    }

}