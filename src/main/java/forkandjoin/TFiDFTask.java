package forkandjoin;

import utils.TFiDF;

import java.io.Serial;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

public class TFiDFTask extends RecursiveTask<List<Map<String, Double>>> {

    @Serial
    private static final long serialVersionUID = 1L;

    private static List<Map<String, Integer>> documentFrequency;
    private static Map<String, Double> inverseDocumentFrequency;


    public TFiDFTask(List<Map<String, Integer>> documentFrequency, Map<String, Double> inverseDocumentFrequency) {
        TFiDFTask.documentFrequency = documentFrequency;
        TFiDFTask.inverseDocumentFrequency = inverseDocumentFrequency;
    }

    @Override
    protected List<Map<String, Double>> compute() {
        return TFiDF.calculateTFIDF(documentFrequency, inverseDocumentFrequency);
    }
}
