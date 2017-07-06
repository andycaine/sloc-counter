package com.andycaine.sloc.filters.blanklines;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BlankLineFilterTest {

    private BlankLineFilter filter = new BlankLineFilter();

    @Test
    public void shouldRemoveBlankLines() throws Exception {
        String s = "\nline 1\nline 2\n\nline 3\r\n";

        assertEquals("line 1\nline 2\nline 3", filter.filter(s));
    }

}