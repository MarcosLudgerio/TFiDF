package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class FileProperties {
          /*
        O que eu tenho do documento?
            O tamanho dele em GB
            A quantidade de linhas
            A quantidade de palavras em cada linha
            O caminho path do arquivo
            A extenção .txt

          1. Processar todo o arquivo, linha a linha;
          2. Armazenar os dados em uma lista para cada linha
          3.

     */

    private static int countDoc = 0;
    private static final AtomicLong counter = new AtomicLong(0);
    private static Map<AtomicLong, String> document = new HashMap<>();
    public static Map<Integer, String> readDocuments(String filePath) throws IOException {
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
        return document;
    }

    public static Map<AtomicLong, String> readDocumentsAtomic(String filePath) throws IOException {
        System.out.println(filePath);
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
