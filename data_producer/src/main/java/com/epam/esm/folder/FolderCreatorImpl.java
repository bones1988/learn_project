package com.epam.esm.folder;

import java.io.File;

public class FolderCreatorImpl implements FolderCreator {
    public void createDirs(String path, int subfoldersCount) {
        File directory = new File(path);
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                throw new RuntimeException("Could not create catalog");
            }
        }
        int depth = subfoldersCount / 3;
        for (int i = 0; i < depth; i++) {
            File subfolder = new File(path + "\\" + i);
            if (!subfolder.exists()) {
                if (!subfolder.mkdir()) {
                    throw new RuntimeException("Could not create catalog");
                }
            }
            String subSubFolderPath = subfolder.toString();
            for (int k = 0; k < depth - 1; k++) {
                subSubFolderPath = subSubFolderPath + "\\" + k;
                File subSubFolder = new File(subSubFolderPath);
                if (!subSubFolder.exists()) {
                    if (!subSubFolder.mkdir()) {
                        throw new RuntimeException("Could not create catalog");
                    }
                }
            }
        }
    }
}
