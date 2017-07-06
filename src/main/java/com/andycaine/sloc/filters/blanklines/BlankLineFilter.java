package com.andycaine.sloc.filters.blanklines;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlankLineFilter {

    public String filter(String string) throws IOException {
        List<String> lines = Lists.newArrayList(string.split("\n"));
        List<String> filtered = new ArrayList<>();
        for (String line : lines) {
            if (StringUtils.isNotEmpty(line.trim())) {
                filtered.add(line.trim());
            }
        }
        return StringUtils.join(filtered, "\n");
    }
}
