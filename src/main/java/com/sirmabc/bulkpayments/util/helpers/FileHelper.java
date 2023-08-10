package com.sirmabc.bulkpayments.util.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileHelper {

    public static File moveFile(File file, String targetPath) throws IOException {
        Path sourcePath = Path.of(file.getPath());
        Path destinationPath = Path.of(targetPath, file.getName());

        return Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING).toFile();
    }

    // Gets all files that are directly inside the given directory
    // If the given directory contains other directories, their content won't be read
    public static File[] getFilesFromPath(String path, String fileExtension) {
        File dir = new File(path);
        File[] files;

        if (fileExtension != null && !fileExtension.isBlank()) {
            files = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(fileExtension.toLowerCase()));
        } else {
            files = dir.listFiles();
        }

        if (files == null) files = new File[0];

        return files;
    }
}
