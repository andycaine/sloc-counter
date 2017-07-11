package com.andycaine.sloc.filters.comments;

import org.apache.commons.lang3.StringUtils;

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
        List<String> importantTokens = new ArrayList<>();
        importantTokens.addAll(lineCommentMarkers);
        importantTokens.add(blockCommentStart);
        importantTokens.add("\"");

        while (StringUtils.isNotEmpty(code)) {

            String nextImportantToken = null;
            int index = code.length();
            for (String importantToken : importantTokens) {
                int i = code.indexOf(importantToken);
                if (i >= 0 && i < index) {
                    index = i;
                    nextImportantToken = importantToken;
                }
            }

            if (nextImportantToken == null) {
                result.append(code);
                return result.toString();
            }

            result.append(code.substring(0, index));
            code = code.substring(index);

            if (nextImportantToken.equals("\"")) {
                result.append(code.substring(0, 1));
                code = code.substring(1);

                int nextIndex = code.indexOf("\"");
                result.append(code.substring(0, nextIndex + 1));
                code = code.substring(nextIndex + 1);
            } else if (nextImportantToken.equals(blockCommentStart)) {
                int nextIndex = code.indexOf(blockCommentEnd);
                code = code.substring(nextIndex + blockCommentEnd.length());
            } else if (lineCommentMarkers.contains(nextImportantToken)) {
                int nextIndex = code.indexOf("\n");
                code = code.substring(nextIndex + 1);
            }
        }
        return result.toString();
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
