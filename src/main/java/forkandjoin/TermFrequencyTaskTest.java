package forkandjoin;

import utils.FileProperties;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

public class TermFrequencyTaskTest {
    private static final String filePath = "D:\\repositorios-git\\TFiDF-kotlin\\TFiDF vk\\dataset\\dataset_test.txt";
    public static final Logger LOGGER = Logger.getLogger(TermFrequencyTask.class.getName());

    public static void main(String[] args) {
        try {
            Map<Integer, String> listOfLines = FileProperties.readDocuments(filePath);
            String[] valuesArray = listOfLines.values().toArray(new String[0]);
            LOGGER.info("Criamos um array com: " + valuesArray.length + " elementos");
            ForkJoinPool pool = new ForkJoinPool();
            TermFrequencyTask taskFrequencyTerm = new TermFrequencyTask(valuesArray, 0, listOfLines.size());
            pool.invoke(taskFrequencyTerm);

//            List<Map<String, Integer>> totalTermFrequency = pool.invoke(taskFrequencyTerm);
//
//            InverseDocumentFrequencyTask inverseDocumentFrequencyTask = new InverseDocumentFrequencyTask(totalTermFrequency, totalTermFrequency.size());
//
//            Map<String, Double> inverseTermFrequency = pool.invoke(inverseDocumentFrequencyTask);
//
//            TFiDFTask tFiDFTask = new TFiDFTask(totalTermFrequency, inverseTermFrequency);
//            LOGGER.info("Term frequency task completed");
//            List<Map<String, Double>> listTfiDf = pool.invoke(tFiDFTask);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

    }
}
