package com.andycaine.sloc.filters.comments;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class FileUtils {

    static String readFile(Path path) throws IOException {
        byte[] encoded = Files.readAllBytes(path);
        return new String(encoded);
    }

}
