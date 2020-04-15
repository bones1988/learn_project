package com.epam.esm.file;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public interface FileParser extends Runnable {
    void setPath(String path);

    void setErrorFolder(String errorFolder);

    void setCountDownLatch(CountDownLatch countDownLatch);

    Path renameFile(Path path);

    void setBusyFiles(List<Path> busyFiles);
}
