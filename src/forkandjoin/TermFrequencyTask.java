package forkandjoin;

import utils.TFiDF;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicLong;

public class TermFrequencyTask extends RecursiveTask<List<Map<String, AtomicLong>>> {
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
    protected List<Map<String, AtomicLong>> compute() {
        int mid = documents.length / 2;
        if (end - start <= 10) {
            System.out.println(Arrays.toString(documents));
            return TFiDF.calculateTermFrequencyAtomicLong(Arrays.stream(documents).toList());
        }
        String[] leftHalf = Arrays.copyOfRange(documents, start, mid);
        String[] rightHalf = Arrays.copyOfRange(documents, mid + 1, end);
        ForkJoinTask<List<Map<String, AtomicLong>>> taskLeft = new TermFrequencyTask(leftHalf, start, mid);

        ForkJoinTask<List<Map<String, AtomicLong>>> taskRight = new TermFrequencyTask(rightHalf, mid + 1, documents.length - 1);
        taskLeft.fork();
        taskRight.fork();
        List<Map<String, AtomicLong>> leftResult = taskLeft.join();
        List<Map<String, AtomicLong>> rightResult = taskRight.join();
        List<Map<String, AtomicLong>> allResult = new ArrayList<>();
        allResult.addAll(leftResult);
        allResult.addAll(rightResult);

        return allResult;
    }
}
