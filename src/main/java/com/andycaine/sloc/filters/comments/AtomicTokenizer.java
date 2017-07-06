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
     * Tokenizes the given string into characters + tokensToRecognize when found.
     *
     * @param string    the string to tokenize
     * @return          a list of characters + tokensToRecognize
     */
    public List<String> tokenize(String string) {
        ArrayList<String> result = new ArrayList<>();

        while (!StringUtils.isEmpty(string)) {
            for (String token : tokensToRecognize) {
                if (string.startsWith(token)) {
                    result.add(token);
                    string = string.substring(token.length());
                }
            }
            if (!StringUtils.isEmpty(string)) {
                result.add(string.substring(0, 1));
                string = string.substring(1);
            }

        }

        return result;
    }


}
