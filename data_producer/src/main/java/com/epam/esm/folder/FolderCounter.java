package com.epam.esm.folder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FolderCounter {
    List<Path> countFolders(String path) throws IOException;
}
