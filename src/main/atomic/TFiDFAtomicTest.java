package main.atomic;

import main.utils.FileProperties;
import main.utils.TFiDF;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class TFiDFAtomicTest {
    private static final String filePath = "D:\\repositorios-git\\projetos-diversos\\TFiDF\\datasets\\dataset_100mb.txt";

    public static void main(String[] args) {

        try {
            Map<AtomicLong, String> listOfLines = FileProperties.readDocumentsAtomic(filePath);
            List<Map<String, AtomicLong>> countTerm = TFiDF.calculateTermFrequencyAtomicLong(listOfLines.values().stream().toList());
            Map<String, Double> frequencyTerm = TFiDF.calculateIDFAtomic(countTerm, listOfLines.size());
            List<Map<String, Double>> listTfAndDf = TFiDF.calculateTFIDFAtomic(countTerm, frequencyTerm);
            for (Map<String, Double> tfAndDf : listTfAndDf) {
                tfAndDf.forEach((key, value) -> System.out.println(key + ":" + value));
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o documento: " + e.getMessage());
        }
    }
}
