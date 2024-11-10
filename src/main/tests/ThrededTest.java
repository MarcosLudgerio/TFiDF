package main.tests;

import org.openjdk.jmh.annotations.*;
import threaded.platform.TFiDFThreadedPlatform;
import threaded.virtual.TFiDFThreadedVirtual;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


public class ThrededTest {
    private static final String filePath = "D:\\repositorios-git\\projetos-diversos\\TFiDF\\datasets\\dataset1gb.txt";


    public void threadedTest() {
        TFiDFThreadedVirtual virtual = new TFiDFThreadedVirtual(filePath);
        virtual.run();
        Map<AtomicLong, String> documentVirtualThread = virtual.getDocument().get();
        TFiDFThreadedPlatform platform = new TFiDFThreadedPlatform(documentVirtualThread);
        platform.run();
        System.out.println("Finalizando threads");
    }
}
