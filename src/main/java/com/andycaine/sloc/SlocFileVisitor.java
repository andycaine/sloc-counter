package com.andycaine.sloc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

class SlocFileVisitor extends SimpleFileVisitor<Path> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final FileSlocCounter fileSlocCounter;

    private final List<PathMatcher> pathsToExclude;

    private int sloc = 0;

    SlocFileVisitor(FileSlocCounter fileSlocCounter, List<PathMatcher> pathsToExclude) {
        this.fileSlocCounter = fileSlocCounter;
        this.pathsToExclude = pathsToExclude;
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

    protected int getSloc() {
        return sloc;
    }
}
