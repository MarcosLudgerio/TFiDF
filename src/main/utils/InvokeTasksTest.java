package main.utils;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class InvokeTasksTest extends RecursiveTask<Integer> {


    private static final int THRESHOLD = 10; // Limite para divisão de tarefas
    private List<Integer> numbers;
    private int start;
    private int end;

    public InvokeTasksTest(List<Integer> numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        // Se a parte é pequena o suficiente, realiza a soma sequencialmente
        if ((end - start) <= THRESHOLD) {
            return sumDirectly();
        } else {
            // Divide a tarefa em duas subtarefas
            int mid = (start + end) / 2;
            InvokeTasksTest leftTask = new InvokeTasksTest(numbers, start, mid);
            InvokeTasksTest rightTask = new InvokeTasksTest(numbers, mid, end);

            // Executa a subtarefa da esquerda em paralelo e processa a subtarefa da direita
            leftTask.fork();
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();

            // Junta os resultados das subtarefas
            return leftResult + rightResult;
        }
    }

    // Método para somar os elementos diretamente
    private int sumDirectly() {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers.get(i);
        }
        return sum;
    }
}


