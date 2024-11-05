package serial;

import utils.FileProperties;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import utils.TFiDF;

public class TFiDFTestSerial {
    private static final String filePath = "D:\\repositorios-git\\projetos-diversos\\TFiDF\\datasets\\shrek-historia.txt";

    public static void main(String[] args) {
        try {
            Map<Integer, String> listOfLines = FileProperties.readDocuments(filePath);
            List<Map<String, Integer>> countTerm = TFiDF.calculateTermFrequency(listOfLines.values().stream().toList());

            Map<String, Double> frequencyTerm = TFiDF.calculateIDF(countTerm, listOfLines.size());
            List<Map<String, Double>> listTfAndDf = TFiDF.calculateTFIDF(countTerm, frequencyTerm);
            for (Map<String, Double> tfAndDf : listTfAndDf) {
                tfAndDf.forEach((key, value) -> System.out.println(key + ":" + value));
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o documento: " + e.getMessage());
        }
    }
}
