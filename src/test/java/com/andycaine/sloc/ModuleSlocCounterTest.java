package com.andycaine.sloc;

import org.junit.Test;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ModuleSlocCounterTest {

    @Test
    public void name() throws Exception {
        Path path = Paths.get("src", "test", "resources");
        System.out.println(path.toAbsolutePath());

        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**src/test**");
        assertTrue(matcher.matches(path));
    }

    @Test
    public void shouldCountTheSlocOfThisModule() throws Exception {
        ModuleSlocCounter counter = new ModuleSlocCounter(asList(
                "**.git",
                "**src/test",
                "**src/main/resources",
                "**.idea")
        );

        int sloc = counter.sloc(Paths.get("."));

        assertEquals(245, sloc);
    }
}
