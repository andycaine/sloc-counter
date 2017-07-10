package com.andycaine.sloc;

import com.andycaine.sloc.filters.blanklines.BlankLineFilter;
import com.andycaine.sloc.filters.comments.CommentFilter;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FileSlocCounter {

    private static final CommentFilter PHP_COMMENT_FILTER = new CommentFilter("/*", "*/", "//", "#");

    private static final CommentFilter JAVA_COMMENT_FILTER = new CommentFilter("/*", "*/", "//");

    private final Map<String, CommentFilter> commentFilters = new HashMap<String, CommentFilter>() {{
        put("java", JAVA_COMMENT_FILTER);
        put("php", PHP_COMMENT_FILTER);
        put("inc", PHP_COMMENT_FILTER);
    }};

    private final BlankLineFilter blankLineFilter = new BlankLineFilter();

    private final LineCounter lineCounter = new LineCounter();

    public int sloc(Path path) throws IOException {
        CommentFilter filter = commentFilters.get(FilenameUtils.getExtension(path.toFile().getName()));
        if (filter == null) {
            return 0;
        }

        return lineCounter.countLines(blankLineFilter.filter(filter.filter(path.toFile())));
    }

}
