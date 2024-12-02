package main.tests;

import threaded.platform.TFiDFThread;
import threaded.virtual.TFiDFThreadedVirtual;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


public class ThrededTest {
    private static final String filePath = "D:\\repositorios-git\\projetos-diversos\\TFiDF\\datasets\\dataset1gb.txt";


    public void threadedTest() {
        TFiDFThreadedVirtual virtual = new TFiDFThreadedVirtual(filePath);
        virtual.run();
        Map<AtomicLong, String> documentVirtualThread = virtual.getDocument().get();
        TFiDFThread platform = new TFiDFThread(documentVirtualThread);
        platform.run();
        System.out.println("Finalizando threads");
    }
}
