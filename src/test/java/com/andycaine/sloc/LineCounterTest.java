package com.andycaine.sloc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LineCounterTest {

    @Test
    public void shouldCountLines() throws Exception {
        LineCounter counter = new LineCounter();
        assertEquals(3, counter.countLines(String.format("%nasdf%nasdf%n")));
    }
}