package com.andycaine.sloc;

import org.junit.Test;

import java.nio.file.Paths;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class ModuleSlocCounterTest {

    @Test
    public void shouldCountTheSlocOfTheTestModule() throws Exception {
        ModuleSlocCounter counter = new ModuleSlocCounter(asList(
                "**/testmodule/ignore/**",
                "**FileToIgnore.java")
        );

        int sloc = counter.sloc(Paths.get("src/test/resources"));

        assertEquals(15, sloc);
    }
}
