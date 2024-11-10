package main.tests;

import forkandjoin.InverseDocumentFrequencyTask;
import forkandjoin.TFiDFTask;
import forkandjoin.TermFrequencyTask;
import org.openjdk.jmh.annotations.*;
import utils.FileProperties;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
public class ForkAndJoinTest {
    private static final String filePath = "D:\\repositorios-git\\projetos-diversos\\TFiDF\\datasets\\dataset1gb.txt";
    public static final Logger LOGGER = Logger.getLogger(ForkAndJoinTest.class.getName());

    @Benchmark
    public void TestingForkAndJoinFramework() {
        Map<Integer, String> listOfLines = null;
        try {
            listOfLines = FileProperties.readDocuments(filePath);
        } catch (IOException e) {
            LOGGER.info("Erro ao ler arquivo: " + e.getMessage());
        }
        assert listOfLines != null;
        String[] valuesArray = listOfLines.values().toArray(new String[0]);
        LOGGER.info("Criamos um array com: " + valuesArray.length + " elementos");
        ForkJoinPool pool = new ForkJoinPool();
        TermFrequencyTask taskFrequencyTerm = new TermFrequencyTask(valuesArray, 0, listOfLines.size());
        pool.invoke(taskFrequencyTerm);

        List<Map<String, Integer>> totalTermFrequency = pool.invoke(taskFrequencyTerm);

        InverseDocumentFrequencyTask inverseDocumentFrequencyTask = new InverseDocumentFrequencyTask(totalTermFrequency, totalTermFrequency.size());

        Map<String, Double> inverseTermFrequency = pool.invoke(inverseDocumentFrequencyTask);

        TFiDFTask tFiDFTask = new TFiDFTask(totalTermFrequency, inverseTermFrequency);
        LOGGER.info("Term frequency task completed");
        List<Map<String, Double>> listTfiDf = pool.invoke(tFiDFTask);
    }
}
