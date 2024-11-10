package main.tests;

import org.openjdk.jmh.annotations.*;
import utils.FileProperties;
import utils.TFiDF;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


public class AtomicTest {
    private static final String filePath = "D:\\repositorios-git\\projetos-diversos\\TFiDF\\datasets\\dataset1gb.txt";


    public void testingAtomicVariables() {
        try {
            Map<AtomicLong, String> listOfLines = FileProperties.readDocumentsAtomic(filePath);
            List<Map<String, AtomicLong>> countTerm = TFiDF.calculateTermFrequencyAtomicLong(listOfLines.values().stream().toList());
            Map<String, Double> frequencyTerm = TFiDF.calculateIDFAtomic(countTerm, listOfLines.size());
            TFiDF.calculateTFIDFAtomic(countTerm, frequencyTerm);
        } catch (IOException e) {
            System.out.println("Erro ao ler o documento: " + e.getMessage());
        }
    }
}
