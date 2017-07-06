package com.andycaine.sloc.filters.comments;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;


public class AtomicTokenizerTest {

    @Test
    public void shouldRecognizeTokens() throws Exception {
        AtomicTokenizer tokenizer = new AtomicTokenizer(asList("//", "/*", "*/", "'''"));
        assertThat(tokenizer.tokenize("a// */v/*''' s"),
                   contains("a", "//", " ", "*/", "v", "/*", "'''", " ", "s"));
    }


}