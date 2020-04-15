package com.epam.esm.file;

import java.io.IOException;

public interface FileCounter {
    void setPath(String path);

    long countErrorFiles() throws IOException;
}
