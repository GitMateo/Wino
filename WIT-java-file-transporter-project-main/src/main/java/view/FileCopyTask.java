package view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileCopyTask implements Runnable {
    private File sourceFile;
    private File destinationDirectory;

    public FileCopyTask(File sourceFile, File destinationDirectory) {
        this.sourceFile = sourceFile;
        this.destinationDirectory = destinationDirectory;
    }

    @Override
    public void run() {
        try {
            File destinationFile = new File(destinationDirectory, sourceFile.getName());
            Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            incrementCopiedFilesCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int copiedFilesCount = 0;

    private static synchronized void incrementCopiedFilesCount() {
        copiedFilesCount++;
    }

    public static synchronized int getCopiedFilesCount() {
        return copiedFilesCount;
    }
}

