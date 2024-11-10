package main.tests;

import utils.FileProperties;
import utils.TFiDF;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SerialTest {
    private static final String filePath = "D:\\repositorios-git\\projetos-diversos\\TFiDF\\datasets\\dataset_100mb.txt";


    public void testSerial() {
        try {
            Map<Integer, String> listOfLines = FileProperties.readDocuments(filePath);
            List<Map<String, Integer>> countTerm = TFiDF.calculateTermFrequency(listOfLines.values().stream().toList());

            Map<String, Double> frequencyTerm = TFiDF.calculateIDF(countTerm, listOfLines.size());
            TFiDF.calculateTFIDF(countTerm, frequencyTerm);

        } catch (IOException e) {
            System.out.println("Erro ao ler o documento: " + e.getMessage());
        }
    }
}
