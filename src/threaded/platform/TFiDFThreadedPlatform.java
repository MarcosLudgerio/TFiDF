package threaded.platform;

import utils.TFiDF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TFiDFThreadedPlatform implements Runnable {
    private static final Lock lock = new ReentrantLock();
    private static List<String> documents = new ArrayList<>();
    private static final AtomicReference<List<Map<String, AtomicLong>>> termFrequency = new AtomicReference<>(new ArrayList<>());
    private static final AtomicReference<Map<String, Double>> idfResult = new AtomicReference<>(new HashMap<>());
    private static List<Map<String, Double>> resultado = new ArrayList<>();

    public TFiDFThreadedPlatform(Map<AtomicLong, String> document) {
        TFiDFThreadedPlatform.documents = document.values().stream().toList();
    }

    public List<Map<String, AtomicLong>> calculateTermFrequency() {
        lock.lock();
        termFrequency.set(TFiDF.calculateTermFrequencyAtomicLong(documents));
        lock.unlock();
        return termFrequency.get();
    }

    public Map<String, Double> calculateDF(int countDocuments) {
        lock.lock();
        idfResult.set(TFiDF.calculateIDFAtomic(termFrequency.get(), countDocuments));
        lock.unlock();
        return idfResult.get();
    }

    public void calculateDFAndIDF() {
        lock.lock();
        System.out.println("Calculando document frequency");
        List<Map<String, AtomicLong>> documentFrequency = this.calculateTermFrequency();
        System.out.println("Calculando doubleMap");
        Map<String, Double> doubleMap = this.calculateDF(documentFrequency.size());
        lock.unlock();
        System.out.println("clculando resultado");
        resultado = TFiDF.calculateTFIDFAtomic(documentFrequency, doubleMap);
        System.out.println(resultado.size());
    }

    public void showingResults() {
        for (Map<String, Double> result : resultado) {
            result.keySet().forEach(s -> System.out.println(s + ": " + result.get(s)));
        }
    }

    @Override
    public void run() {
        this.calculateDFAndIDF();
    }
}


