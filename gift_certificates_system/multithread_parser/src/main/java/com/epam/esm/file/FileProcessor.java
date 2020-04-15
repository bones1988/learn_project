package com.epam.esm.file;

import java.io.IOException;
import java.nio.file.Path;

public interface FileProcessor {
    boolean processFile(Path path, String startPath) throws IOException;
}
