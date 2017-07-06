package com.andycaine.sloc;

import org.junit.Test;

import java.nio.file.Paths;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class ModuleSlocCounterTest {

    @Test
    public void shouldCountTheSlocOfThisModule() throws Exception {
        ModuleSlocCounter counter = new ModuleSlocCounter(asList(
                Paths.get(".git"),
                Paths.get("target"),
                Paths.get("src", "test"),
                Paths.get("src", "main", "resources"),
                Paths.get(".idea"))
        );

        int sloc = counter.sloc(Paths.get("."));

        assertEquals(242, sloc);
    }
}
