package threaded.virtual;

import utils.FileProperties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TFiDFThreadedVirtual implements Runnable {

    private static final Lock lock = new ReentrantLock();
    private String filePath;
    AtomicReference<Map<AtomicLong, String>> document = new AtomicReference<>(new HashMap<>());

    public TFiDFThreadedVirtual(String filePath) {
        this.filePath = filePath;
    }

    public AtomicReference<Map<AtomicLong, String>> getDocument() {
        return document;
    }


    @Override
    public void run() {
        System.out.println("Tentando ler arqiuvo");
        try {
            lock.lock();
            document.set(FileProperties.readDocumentsAtomic(filePath));
        } catch (IOException e) {
            System.out.println("erro ao ler documento: " + e.getMessage());
        } finally {
            lock.unlock();
        }
        System.out.println("Thread Virtual para leitura de arquivo fechada");

    }
}
