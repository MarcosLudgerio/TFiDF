package forkandjoin;

import utils.FileProperties;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class TermFrequencyTaskTest {
    private static final String filePath = "D:\\repositorios-git\\projetos-diversos\\TFiDF\\datasets\\shrek-historia.txt";

    public static void main(String[] args) {
        try {
            Map<Integer, String> listOfLines = FileProperties.readDocuments(filePath);
            String[] valuesArray = listOfLines.values().toArray(new String[0]);
            System.out.println("Criamos um array com: " + valuesArray.length + " elementos");
            ForkJoinPool pool = new ForkJoinPool();
            TermFrequencyTask taskFrequencyTerm = new TermFrequencyTask(valuesArray, 0, listOfLines.size());
            List<Map<String, Integer>> totalTermFrequency = pool.invoke(taskFrequencyTerm);
            InverseDocumentFrequencyTask inverseDocumentFrequencyTask = new InverseDocumentFrequencyTask(totalTermFrequency, totalTermFrequency.size());
            Map<String, Double> inverseTermFrequency = pool.invoke(inverseDocumentFrequencyTask);
            TFiDFTask tFiDFTask = new TFiDFTask(totalTermFrequency, inverseTermFrequency);
            pool.invoke(tFiDFTask);
            List<Map<String, Double>> listTfiDf = pool.invoke(tFiDFTask);
            listTfiDf.forEach(a -> {
                a.forEach((s, aDouble) -> System.out.println(s + " a:" + aDouble));
            });
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

    }
}
