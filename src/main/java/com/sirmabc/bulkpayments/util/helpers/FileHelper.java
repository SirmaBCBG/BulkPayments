package com.sirmabc.bulkpayments.util.helpers;

import com.sirmabc.bulkpayments.util.enums.InOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;

public class FileHelper {

    private static final Logger logger = LoggerFactory.getLogger(FileHelper.class);

    private static LocalDateTime prevSavedMsgDateTime;

    static {
        prevSavedMsgDateTime = LocalDateTime.now();
    }

    // Moves the given file to the desired directory
    public static File moveFile(File file, String targetPath) throws IOException {
        Path sourcePath = Path.of(file.getPath());
        Path destinationPath = Path.of(targetPath, file.getName());

        logger.info("moveFile(): Moving file from " + sourcePath + " to " + destinationPath);

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
        if (files.length > 1) Arrays.sort(files, Comparator.comparingLong(File::lastModified));

        return files;
    }

    // Read all content from the given file
    public static String readFile(File file) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    // Generates a unique file name
    public synchronized static String generateUniqueFileName(InOut inOut, String msgDefIdr, String messageId) {
        logger.info("generateUniqueFileName(): Generating file name for message " + messageId);

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
