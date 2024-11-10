package main.tests;

import org.openjdk.jmh.annotations.*;
import threaded.platform.TFiDFThreadedPlatform;
import threaded.virtual.TFiDFThreadedVirtual;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class ThrededTest {
    private static final String filePath = "D:\\repositorios-git\\projetos-diversos\\TFiDF\\datasets\\dataset_100mb.txt";

    @Benchmark
    public void threadedTest() {
        TFiDFThreadedVirtual virtual = new TFiDFThreadedVirtual(filePath);
        virtual.run();
        Map<AtomicLong, String> documentVirtualThread = virtual.getDocument().get();
        TFiDFThreadedPlatform platform = new TFiDFThreadedPlatform(documentVirtualThread);
        platform.run();
        System.out.println("Finalizando threads");
    }
}
