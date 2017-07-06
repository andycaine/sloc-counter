package com.andycaine.sloc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class ModuleSlocCounter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final List<PathMatcher> pathsToExclude;

    public ModuleSlocCounter(List<String> pathsToExclude) {
        List<PathMatcher> globs = new ArrayList<>();

        for (String glob : pathsToExclude) {
            globs.add(FileSystems.getDefault().getPathMatcher("glob:" + glob));
        }
        this.pathsToExclude = globs;
    }

    public int sloc(Path path) throws IOException {
        SlocFileVisitor slocVisitor = new SlocFileVisitor(new FileSlocCounter());
        Files.walkFileTree(path, slocVisitor);

        return slocVisitor.sloc;
    }

    private class SlocFileVisitor extends SimpleFileVisitor<Path> {

        private final FileSlocCounter fileSlocCounter;

        private int sloc = 0;

        private SlocFileVisitor(FileSlocCounter fileSlocCounter) {
            this.fileSlocCounter = fileSlocCounter;
        }

        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
            if (shouldIgnore(path)) {
                return FileVisitResult.CONTINUE;
            }
            sloc += fileSlocCounter.sloc(path);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            if (shouldIgnore(dir)) {
                return FileVisitResult.SKIP_SUBTREE;
            }
            return super.preVisitDirectory(dir, attrs);
        }

        private boolean shouldIgnore(Path path) {
            for (PathMatcher exclude : pathsToExclude) {
                if (exclude.matches(path)) {
                    logger.debug("Ignoring path " + path.normalize());
                    return true;
                }
            }
            return false;
        }

    }
}
