package threaded.virtual;

import forkandjoin.TermFrequencyTask;
import utils.FileProperties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class TFiDFThreadedVirtual implements Runnable {

    private static final Lock lock = new ReentrantLock();
    private String filePath;
    AtomicReference<Map<AtomicLong, String>> document = new AtomicReference<>(new HashMap<>());
    private static final Logger LOGGER = Logger.getLogger(TermFrequencyTask.class.getName());

    public TFiDFThreadedVirtual(String filePath) {
        this.filePath = filePath;
    }

    public AtomicReference<Map<AtomicLong, String>> getDocument() {
        return document;
    }


    @Override
    public void run() {
        LOGGER.info("Tentando ler arqiuvo");
        try {
            lock.lock();
            document.set(FileProperties.readDocumentsAtomic(filePath));
        } catch (IOException e) {
            LOGGER.info("erro ao ler documento: " + e.getMessage());
        } finally {
            lock.unlock();
        }
        LOGGER.info("Thread Virtual para leitura de arquivo fechada");

    }
}
