package utils;

import forkandjoin.TermFrequencyTask;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TFiDF {
    private static final Logger LOGGER = Logger.getLogger(TermFrequencyTask.class.getName());

    public static List<Map<String, Integer>> calculateTermFrequency(List<String> documents) {
        List<Map<String, Integer>> termFrequencyList = new ArrayList<>();

        for (String doc : documents) {
            if (doc != null) {
                Map<String, Integer> termFrequency = new HashMap<>();
                String[] words = doc.split("\\s+");

                for (String word : words) {
                    termFrequency.put(word.toLowerCase(), termFrequency.getOrDefault(word, 0) + 1);
                }
                termFrequencyList.add(termFrequency);
            }
        }
        return termFrequencyList;
    }

    public static List<Map<String, AtomicLong>> calculateTermFrequencyAtomicLong(List<String> documents) {
        List<Map<String, AtomicLong>> termFrequencyList = new ArrayList<>();
        for (String doc : documents) {
            Map<String, AtomicLong> termFrequency = new HashMap<>();
            if (doc != null) {
                String[] words = doc.split("\\s+");
                for (String word : words) {
                    AtomicLong atomicLong = new AtomicLong();
                    atomicLong.incrementAndGet();
                    termFrequency.put(word, termFrequency.getOrDefault(word, atomicLong));
                }
            }
            termFrequencyList.add(termFrequency);
        }
        return termFrequencyList;
    }

    public static List<Map<String, AtomicLong>> calculateTermFrequencyAtomicLong(String[] documents) {
        List<String> documentsList = Arrays.asList(documents);
        return calculateTermFrequencyAtomicLong(documentsList);
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
