package main.forkandjoin;

import main.utils.TFiDF;

import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

public class InverseDocumentFrequencyTask extends RecursiveTask<Map<String, Double>> {

    private static List<Map<String, Integer>> inverseTermFrequencyList;
    private static int numDocuments;

    public InverseDocumentFrequencyTask(List<Map<String, Integer>> inverseTermFrequencyList, int numDocuments){
        InverseDocumentFrequencyTask.inverseTermFrequencyList = inverseTermFrequencyList;
        InverseDocumentFrequencyTask.numDocuments = numDocuments;
    }

    @Override
    protected Map<String, Double> compute() {
        return TFiDF.calculateIDF(inverseTermFrequencyList, numDocuments);
    }
}
