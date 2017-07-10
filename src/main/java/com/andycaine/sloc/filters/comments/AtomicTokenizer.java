package com.andycaine.sloc.filters.comments;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Tokenizes a string into characters, except for the given tokens
 * which are returned whole.
 */
class AtomicTokenizer {

    private final List<String> tokensToRecognize;

    AtomicTokenizer(List<String> tokensToRecognize) {
        this.tokensToRecognize = tokensToRecognize;
    }

    /**
     * Tokenizes the given string into characters + tokensToRecognize
     * when found.
     *
     * @param string    the string to tokenize
     * @return          a list of characters + tokensToRecognize
     */
    public List<String> tokenize(String string) {
        ArrayList<String> result = new ArrayList<>();

        while (!StringUtils.isEmpty(string)) {
            String token = nextToken(string);
            result.add(token);
            string = string.substring(token.length());
        }

        return result;
    }

    private String nextToken(String string) {
        for (String token : tokensToRecognize) {
            if (string.startsWith(token)) {
                return token;
            }
        }
        return string.substring(0, 1);
    }

}
