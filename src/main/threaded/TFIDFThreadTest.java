package main.threaded;

import main.threaded.platform.TFiDFThreadedPlatform;
import main.threaded.virtual.TFiDFThreadedVirtual;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class TFIDFThreadTest {
    private static final String filePath = "D:\\repositorios-git\\projetos-diversos\\TFiDF\\datasets\\dataset_100mb.txt";

    public static void main(String[] args) {

        TFiDFThreadedVirtual virtual = new TFiDFThreadedVirtual(filePath);
        virtual.run();
        Map<AtomicLong, String> documentVirtualThread = virtual.getDocument().get();
        TFiDFThreadedPlatform platform = new TFiDFThreadedPlatform(documentVirtualThread);
        platform.run();
        System.out.println("Finalizando threads");
    }
}
