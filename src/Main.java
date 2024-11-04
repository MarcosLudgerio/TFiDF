import utils.InvokeTasksTest;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        // Gera uma lista de inteiros de exemplo
        List<Integer> numbers = IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toList());

        // Cria um pool de ForkJoin para gerenciar as threads
        ForkJoinPool pool = new ForkJoinPool();

        // Cria a tarefa inicial e executa-a
        InvokeTasksTest task = new InvokeTasksTest(numbers, 0, numbers.size());
        int totalSum = pool.invoke(task); // Executa a tarefa e retorna a soma total

        System.out.println("Soma total dos elementos na lista: " + totalSum);
    }
}

