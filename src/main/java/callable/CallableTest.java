package callable;

import utils.TFiDF;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableTest {
    static String filePath = "D:\\repositorios-git\\TFiDF-kotlin\\TFiDF vk\\dataset\\dataset_test.txt";

    public static void main(String[] args) throws RuntimeException {

        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            // Leitura do arquivo e divisão em documentos
            List<String> documents = Files.readAllLines(Path.of(filePath));

            // Submissão de tarefas para cálculo concorrente
            Future<List<Map<String, Integer>>> tfTask = executorService.submit(() -> TFiDF.calculateTermFrequency(documents));
            Future<Map<String, Double>> idfTask = executorService.submit(() -> TFiDF.calculateIDF(tfTask.get(), tfTask.get().size()));
            Future<List<Map<String, Double>>> tfidfTask = executorService.submit(() -> TFiDF.calculateTFIDF(tfTask.get(), idfTask.get()));

            tfidfTask.get().forEach(doubleMap -> {
                doubleMap.forEach((s, aDouble) -> {
                    System.out.println(s + " " + aDouble);
                });
            });

        } catch (IOException | ExecutionException | InterruptedException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
