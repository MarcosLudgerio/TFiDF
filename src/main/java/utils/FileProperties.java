package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class FileProperties {
    private static int countDoc = 0;
    private static final AtomicLong counter = new AtomicLong(0);
    private static Map<AtomicLong, String> document = new HashMap<>();
    public static final Logger LOGGER = Logger.getLogger(FileProperties.class.getName());

    public static Map<Integer, String> readDocuments(String filePath) throws IOException {
        LOGGER.info("Reading documents from " + filePath);
        Map<Integer, String> document = new HashMap<>();
        var reader = Files.newBufferedReader(Path.of(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            countDoc++; // Variável de controle para gerar o ID da linha no documento
            if (!line.isEmpty()) {
                document.put(countDoc, line);
            }
        }
        reader.close();
        LOGGER.info("Reading finished");
        return document;
    }

    public static Map<AtomicLong, String> readDocumentsAtomic(String filePath) throws IOException {

        var reader = Files.newBufferedReader(Path.of(filePath));

        String line;
        while ((line = reader.readLine()) != null) {
            long count = counter.incrementAndGet();
            AtomicLong atomicLong = new AtomicLong(count);
            if (!line.isEmpty()) {
                document.put(atomicLong, line.trim().toLowerCase());
            }

        }
        reader.close();
        return document;
    }


}
