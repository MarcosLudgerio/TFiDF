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
            TermFrequencyTask task = new TermFrequencyTask(valuesArray, 0, listOfLines.size());
            List<Map<String, Integer>> totalTermFrequency = pool.invoke(task);
            System.out.println(totalTermFrequency.size());
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

    }
}
