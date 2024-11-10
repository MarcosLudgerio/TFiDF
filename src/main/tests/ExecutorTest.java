package main.tests;

import threaded.platform.TFiDFThreadedPlatform;
import utils.FileProperties;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class ExecutorTest {
    private static final String filePath = "D:\\repositorios-git\\projetos-diversos\\TFiDF\\datasets\\dataset_100mb.txt";

    public void TestingExecutorFramework() {
        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            Map<AtomicLong, String> documents = FileProperties.readDocumentsAtomic(filePath);
            TFiDFThreadedPlatform platform = new TFiDFThreadedPlatform(documents);
            executorService.submit(platform::calculateDFAndIDF);
            executorService.submit(platform::showingResults);

        } catch (IOException e) {
            System.out.println("pode dar merda: " + e.getMessage());
        }
    }
}
