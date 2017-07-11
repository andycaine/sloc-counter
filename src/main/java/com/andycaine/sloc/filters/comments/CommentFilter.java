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

        while (StringUtils.isNotEmpty(code)) {

            Token token = findNextCommentOrQuoteStartToken(code);

            if (token == null) {
                result.append(code);
                return result.toString();
            }

            result.append(code.substring(0, token.getIndex()));
            code = code.substring(token.getIndex());

            if (token.getValue().equals("\"")) {
                result.append(code.substring(0, 1));
                code = code.substring(1);

                int nextIndex = code.indexOf("\"");
                result.append(code.substring(0, nextIndex + 1));
                code = code.substring(nextIndex + 1);
            } else if (token.getValue().equals(blockCommentStart)) {
                int nextIndex = code.indexOf(blockCommentEnd);
                code = code.substring(nextIndex + blockCommentEnd.length());
            } else if (lineCommentMarkers.contains(token.getValue())) {
                int nextIndex = code.indexOf("\n");
                if (nextIndex == -1) { // end of file
                    code = "";
                } else {
                    code = code.substring(nextIndex + 1);
                }
            }
        }
        return result.toString();
    }

    private Token findNextCommentOrQuoteStartToken(String code) {
        List<String> tokensToFind = new ArrayList<>();
        tokensToFind.addAll(lineCommentMarkers);
        tokensToFind.add(blockCommentStart);
        tokensToFind.add("\"");

        Token token = null;
        for (String tokenToFind : tokensToFind) {
            int index = code.indexOf(tokenToFind);
            if (index >= 0 && (token == null || index < token.getIndex())) {
                if (!(tokenToFind.equals("\"") && index > 0 && code.charAt(index - 1) == '\\')) {
                    token = new Token(tokenToFind, index);
                }
            }
        }

        return token;
    }

    private class Token {

        private final String value;
        private final int index;

        public Token(String value, int index) {
            this.value = value;
            this.index = index;
        }

        public String getValue() {
            return value;
        }

        public int getIndex() {
            return index;
        }
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
