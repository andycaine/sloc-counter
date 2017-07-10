package com.andycaine.sloc;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleSlocCounter {

    private final List<PathMatcher> pathsToExclude;

    public ModuleSlocCounter(List<String> pathsToExclude) {
        List<PathMatcher> globs = new ArrayList<>();
        for (String glob : pathsToExclude) {
            globs.add(FileSystems.getDefault().getPathMatcher("glob:" + glob));
        }
        this.pathsToExclude = globs;
    }

    public int sloc(Path path) throws IOException {
        SlocFileVisitor slocVisitor = new SlocFileVisitor(new FileSlocCounter(), pathsToExclude);
        Files.walkFileTree(path, slocVisitor);

        return slocVisitor.getSloc();
    }

}
