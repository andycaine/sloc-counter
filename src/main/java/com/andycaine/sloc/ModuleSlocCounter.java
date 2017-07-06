package com.andycaine.sloc;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class ModuleSlocCounter {

    private final List<Path> pathsToExclude;

    public ModuleSlocCounter(List<Path> pathsToExclude) {
        this.pathsToExclude = pathsToExclude;
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
            for (Path exclude : pathsToExclude) {
                if (path.normalize().equals(exclude.normalize())) {
                    return true;
                }
            }
            return false;
        }


    }

}
