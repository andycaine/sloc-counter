package com.andycaine.sloc.filters.comments;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * A filter that removes code comments.
 */
public class CommentFilter {

    private final String blockCommentStart;

    private final String blockCommentEnd;

    private final List<String> lineCommentMarkers;

    private CommentFilterState filterState = new NotInQuotesOrComments();

    public CommentFilter(String blockCommentStart, String blockCommentEnd, String... lineCommentMarkers) {
        this.blockCommentStart = blockCommentStart;
        this.blockCommentEnd = blockCommentEnd;
        this.lineCommentMarkers = asList(lineCommentMarkers);
    }

    /**
     * Removes code comments from the given string.
     *
     * @param code  the code to filter
     * @return      the filtered code
     */
    String filter(String code) {
        StringBuilder result = new StringBuilder();
        AtomicTokenizer tokenizer = new AtomicTokenizer(tokensToRecognize());

        for (String token : tokenizer.tokenize(code)) {
            filterState.handle(token, result, this);
        }

        return result.toString();
    }

    private List<String> tokensToRecognize() {
        List<String> result = new ArrayList<>();
        result.add(blockCommentEnd);
        result.add(blockCommentStart);
        result.addAll(lineCommentMarkers);
        result.add("\r\n");
        return result;
    }

    void setFilterState(CommentFilterState filterState) {
        this.filterState = filterState;
    }

    boolean isLineCommentStart(String token) {
        return lineCommentMarkers.contains(token);
    }

    boolean isBlockCommentStart(String token) {
        return blockCommentStart.equals(token);
    }

    boolean isBlockCommentEnd(String token) {
        return blockCommentEnd.equals(token);
    }

    /**
     * Removes code comments from the given file.
     *
     * @param file  the file to filter
     * @return      the filtered code
     */
    public String filter(File file) throws IOException {
        return filter(FileUtils.readFile(file.toPath()));
    }

}
