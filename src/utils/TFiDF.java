package utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class TFiDF {
    public static List<Map<String, Integer>> calculateTermFrequency(List<String> documents) {
        System.out.println("iniciando a conta de TermFrequency");
        List<Map<String, Integer>> termFrequencyList = new ArrayList<>();
        for (String doc : documents) {
            Map<String, Integer> termFrequency = new HashMap<>();
            String[] words = doc.split("\\s+");

            for (String word : words) {
                termFrequency.put(word.toLowerCase(), termFrequency.getOrDefault(word, 0) + 1);
            }
            termFrequencyList.add(termFrequency);
        }
        System.out.println("Finalizando a conta de TermFrequency");
        return termFrequencyList;
    }

    public static List<Map<String, Integer>> calculateTermFrequency(String[] documents) {
        List<String> documentsList = Arrays.asList(documents);
        System.out.println("Devolvendo para arraylist: " + documentsList.size());
        return calculateTermFrequency(documentsList);
    }

    public static List<Map<String, AtomicLong>> calculateTermFrequencyAtomicLong(List<String> documents) {
        List<Map<String, AtomicLong>> termFrequencyList = new ArrayList<>();
        for (String doc : documents) {
            Map<String, AtomicLong> termFrequency = new HashMap<>();
            String[] words = doc.split("\\s+");
            for (String word : words) {
                AtomicLong atomicLong = new AtomicLong();
                atomicLong.addAndGet(1);
                termFrequency.put(word, termFrequency.getOrDefault(word, atomicLong));
            }
            termFrequencyList.add(termFrequency);
        }
        return termFrequencyList;
    }

    public static Map<String, Double> calculateIDF(List<Map<String, Integer>> termFrequencyList, int numDocuments) {
        Map<String, Double> idfValues = new HashMap<>();
        Set<String> allTerms = termFrequencyList.stream()
                .flatMap(map -> map.keySet().stream())
                .collect(Collectors.toSet());

        for (String term : allTerms) {
            int docCount = 0;
            for (Map<String, Integer> tf : termFrequencyList) {
                if (tf.containsKey(term)) {
                    docCount++;
                }
            }
            double idf = Math.log((double) numDocuments / (docCount + 1));
            idfValues.put(term, idf);
        }
        return idfValues;
    }

    public static Map<String, Double> calculateIDFAtomic(List<Map<String, AtomicLong>> termFrequencyList, int numDocuments) {
        Map<String, Double> idfValues = new HashMap<>();
        Set<String> allTerms = termFrequencyList.stream()
                .flatMap(map -> map.keySet().stream())
                .collect(Collectors.toSet());

        for (String term : allTerms) {
            int docCount = 1;
            for (Map<String, AtomicLong> tf : termFrequencyList) {
                if (tf.containsKey(term)) {
                    docCount++;
                }
            }
            double idf = Math.log((double) numDocuments / (docCount + 1));
            idfValues.put(term, idf);
        }
        return idfValues;
    }


    // Função para calcular o TF-IDF de cada termo em cada documento
    public static List<Map<String, Double>> calculateTFIDF(List<Map<String, Integer>> termFrequencyList, Map<String, Double> idfValues) {
        List<Map<String, Double>> tfidfList = new ArrayList<>();

        for (Map<String, Integer> tf : termFrequencyList) {
            Map<String, Double> tfidf = new HashMap<>();

            for (Map.Entry<String, Integer> entry : tf.entrySet()) {
                String term = entry.getKey();
                int termFreq = entry.getValue();
                double tfidfValue = termFreq * idfValues.get(term);
                tfidf.put(term, tfidfValue);
            }
            tfidfList.add(tfidf);
        }
        return tfidfList;
    }

    public static List<Map<String, Double>> calculateTFIDFAtomic(List<Map<String, AtomicLong>> termFrequencyList, Map<String, Double> idfValues) {
        List<Map<String, Double>> tfidfList = new ArrayList<>();

        for (Map<String, AtomicLong> tf : termFrequencyList) {
            Map<String, Double> tfidf = new HashMap<>();

            for (Map.Entry<String, AtomicLong> entry : tf.entrySet()) {
                String term = entry.getKey();
                AtomicLong termFreq = entry.getValue();
                double tfidfValue = termFreq.get() * idfValues.get(term);
                tfidf.put(term, tfidfValue);
            }
            tfidfList.add(tfidf);
        }
        return tfidfList;
    }
}
