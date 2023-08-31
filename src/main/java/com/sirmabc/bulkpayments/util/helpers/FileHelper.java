package com.sirmabc.bulkpayments.util.helpers;

import com.sirmabc.bulkpayments.util.enums.InOut;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileHelper {

    private static LocalDateTime prevSavedMsgDateTime;

    static {
        prevSavedMsgDateTime = LocalDateTime.now();
    }

    // Moves the given file to the desired directory
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

    // Read all content from the given file
    public static String readFile(File file) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    // Generates a unique file name
    public synchronized static String generateUniqueFileName(InOut inOut, String msgDefIdr) {
        String shortMessageType = msgDefIdr.substring(msgDefIdr.indexOf('.') + 1, msgDefIdr.indexOf('.', msgDefIdr.indexOf('.') + 1));
        LocalDateTime currentMsgDateTime = LocalDateTime.now();

        while (currentMsgDateTime.equals(prevSavedMsgDateTime)) currentMsgDateTime = LocalDateTime.now();
        prevSavedMsgDateTime = currentMsgDateTime;

        return inOut.value
                + "_"
                + shortMessageType
                + "_"
                + currentMsgDateTime.format(DateTimeFormatter.ofPattern("yyMMddHHmmss"))
                + "_"
                + currentMsgDateTime.getNano();
    }
}
