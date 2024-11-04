package forkandjoin;

import utils.TFiDF;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class TermFrequencyTask extends RecursiveTask<List<Map<String, Integer>>> {
    private static final int THRESHOLD = 20; // Limite para divisão de tarefas
    private static String[] documents;
    private int start;
    private int end;

    public TermFrequencyTask(String[] files, int start, int end) {
        documents = files;
        this.start = start;
        this.end = end;
    }

    @Override
    protected List<Map<String, Integer>> compute() {
        int mid = documents.length / 2;
        if (mid >= THRESHOLD) {
            System.out.println("Chamado para o mid " + mid);
            String[] midLow = Arrays.copyOfRange(documents, start, mid);
            String[] midHigh = Arrays.copyOfRange(documents, mid, end);
            ForkJoinTask<List<Map<String, Integer>>> task = new TermFrequencyTask(midLow, start, mid);
            ForkJoinTask<List<Map<String, Integer>>> task1 = new TermFrequencyTask(midHigh, mid + 1, end);
            
            invokeAll(task1, task);
        }
        return  TFiDF.calculateTermFrequency(documents);
    }
}
