// Prepare inventory data before running the operations
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class InventoryCatalogManager {
    private static final int NUM_THREADS = 10;
    private static final int OPS_PER_THREAD = 50000;  // 50,000 operations by each thread
    private static final int NUM_PRODUCTS = 1000;

    // Fill the catalog with products (productId -> stockCount)
    private static void fillCatalog(Map<String, Integer> catalog) {
        for (int i = 0; i < NUM_PRODUCTS; i++) {
            catalog.put("P" + i, 100);
        }
    }

    // Runs the workers on the given map and prints the results
    private static void runBenchmark(String name, Map<String, Integer> catalog) {
        fillCatalog(catalog);
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);

        long start = System.nanoTime();
        for (int t = 0; t < NUM_THREADS; t++) {
            pool.execute(() -> {
                Random rand = new Random();
                for (int i = 0; i < OPS_PER_THREAD; i++) {
                    String id = "P" + rand.nextInt(NUM_PRODUCTS);
                    if (i % 2 == 0) {
                        catalog.get(id);                 // stock lookup
                    } else {
                        catalog.put(id, rand.nextInt(200)); // stock update
                    }
                }
            });
        }
        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println("Benchmark interrupted");
        }
        long elapsed = System.nanoTime() - start;

        long totalOps = (long) NUM_THREADS * OPS_PER_THREAD;
        double millis = elapsed / 1e6;
        double throughput = totalOps / (elapsed / 1e9);

        System.out.println("--- " + name + " ---");
        System.out.println("Total operations : " + totalOps);
        System.out.printf("Execution time   : %.2f ms%n", millis);
        System.out.printf("Throughput       : %.0f operations/second%n%n", throughput);
    }

    public static void benchmarkSynchronizedMap() {
        Map<String, Integer> catalog = Collections.synchronizedMap(new HashMap<>());
        // Measure performance using a synchronized map
        runBenchmark("Collections.synchronizedMap", catalog);
    }
// Visit adjacent elements in each row
    public static void benchmarkConcurrentMap() {
        Map<String, Integer> catalog = new ConcurrentHashMap<>();
        runBenchmark("ConcurrentHashMap", catalog);
    }

    public static void main(String[] args) {
        System.out.println("Threads: " + NUM_THREADS + " | Operations per thread: " + OPS_PER_THREAD + "\n");
        benchmarkSynchronizedMap();
        benchmarkConcurrentMap();
    }
}
