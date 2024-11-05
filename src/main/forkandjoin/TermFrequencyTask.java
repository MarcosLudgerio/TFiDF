package main.forkandjoin;

import main.utils.TFiDF;

import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class TermFrequencyTask extends RecursiveTask<List<Map<String, Integer>>> {
    private static final int THRESHOLD = 10; // Limite para divisão de tarefas

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
        if (end - start <= THRESHOLD) {
            return TFiDF.calculateTermFrequency(Arrays.stream(documents).toList());
        }
        String[] leftHalf = Arrays.copyOfRange(documents, start, mid);
        String[] rightHalf = Arrays.copyOfRange(documents, mid + 1, end);
        return getAllResult(leftHalf, mid, rightHalf);
    }

    private List<Map<String, Integer>> getAllResult(String[] leftHalf, int mid, String[] rightHalf) {
        ForkJoinTask<List<Map<String, Integer>>> taskLeft = new TermFrequencyTask(leftHalf, start, mid);

        ForkJoinTask<List<Map<String, Integer>>> taskRight = new TermFrequencyTask(rightHalf, mid + 1, documents.length - 1);
        taskLeft.fork();
        taskRight.fork();
        List<Map<String, Integer>> leftResult = taskLeft.join();
        List<Map<String, Integer>> rightResult = taskRight.join();
        List<Map<String, Integer>> allResult = new ArrayList<>();
        allResult.addAll(leftResult);
        allResult.addAll(rightResult);

        return allResult;
    }
}
