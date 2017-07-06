package com.andycaine.sloc.filters.comments;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommentFilterTest {

    private CommentFilter filter = new CommentFilter("/*", "*/", "//", "#");

    @Test
    public void shouldRemoveComments() throws Exception {
        String input =
                "// should be removed\r\n" +
                "should not be removed # blah blah\n" +
                "/* should be removed\n" +
                "should be removed\r\n" +
                "should be removed*/\n" +
                "should not /*be removed\n" +
                "should be removed*/\n" +
                "should not /*be removed\n" +
                "should be removed\n" +
                "should not be */removed\n";

        String filtered = filter.filter(input);

        String expected =
                "\r\n" +
                "should not be removed \n" +
                "\n" +
                "should not \n" +
                "should not removed\n";

        assertEquals(expected, filtered);
    }

    @Test
    public void shouldNotRemoveCommentsInQuotes() throws Exception {
        String input = "should \"// not remove\"";

        String filtered = filter.filter(input);

        assertEquals(input, filtered);
    }

}
