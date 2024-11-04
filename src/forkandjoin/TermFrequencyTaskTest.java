package forkandjoin;

import utils.FileProperties;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicLong;

public class TermFrequencyTaskTest {
    private static final String filePath = "D:\\repositorios-git\\projetos-diversos\\TFiDF\\datasets\\shrek-historia.txt";

    public static void main(String[] args) {
        try {
            Map<Integer, String> listOfLines = FileProperties.readDocuments(filePath);
            String[] valuesArray = listOfLines.values().toArray(new String[0]);
            System.out.println("Criamos um array com: " + valuesArray.length + " elementos");
            ForkJoinPool pool = new ForkJoinPool();
            TermFrequencyTask task = new TermFrequencyTask(valuesArray, 0, listOfLines.size());
            List<Map<String, AtomicLong>> totalTermFrequency = pool.invoke(task);


            totalTermFrequency.forEach(termFrequency -> {
               for (Map.Entry<String, AtomicLong> entry : termFrequency.entrySet()) {
                   System.out.println(entry.getKey() + ": " + entry.getValue().get());
               }
            });
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

    }
}
