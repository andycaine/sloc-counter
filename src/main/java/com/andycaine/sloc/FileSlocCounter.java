package com.andycaine.sloc;

import com.andycaine.sloc.filters.blanklines.BlankLineFilter;
import com.andycaine.sloc.filters.comments.CommentFilter;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FileSlocCounter {

    private final Map<String, CommentFilter> commentFilters = new HashMap<String, CommentFilter>() {{
        put("java", new CommentFilter("/*", "*/", "//"));
        put("php", new CommentFilter("/*", "*/", "//", "#"));
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
