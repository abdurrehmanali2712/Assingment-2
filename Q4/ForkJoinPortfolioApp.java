import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPortfolioApp {
    public static void main(String[] args) {
        int size = 10000;
        double riskCap = 800.0;

        // Generate synthetic asset values (fixed seed so results repeat)
        double[] assets = new double[size];
        Random rand = new Random(42);
        for (int i = 0; i < size; i++) {
            assets[i] = 100 + rand.nextDouble() * 900; // values between 100 and 1000
        }
        double[] serialCopy = assets.clone(); // copy for serial computation

        ForkJoinPool pool = new ForkJoinPool();
        System.out.println("=== Fork/Join Portfolio Assessment ===");
        System.out.println("Assets: " + size + " | Cores available: " + pool.getParallelism());

        // Warm-up run so the JVM compiles the code before timing
        pool.invoke(new ParallelRiskCalculator(assets, 0, size));

        // ---------- Task 1: total risk score ----------
        long t1 = System.nanoTime();
        double serialTotal = 0;
        for (int i = 0; i < size; i++) {
            serialTotal += serialCopy[i];
        }
        long serialTime = System.nanoTime() - t1;

        long t2 = System.nanoTime();
        double parallelTotal = pool.invoke(new ParallelRiskCalculator(assets, 0, size));
        long parallelTime = System.nanoTime() - t2;

        System.out.println("\n--- Total Risk Score (RecursiveTask) ---");
        System.out.printf("Serial total   : %.2f  (%.3f ms)%n", serialTotal, serialTime / 1e6);
        System.out.printf("Parallel total : %.2f  (%.3f ms)%n", parallelTotal, parallelTime / 1e6);
        System.out.printf("Speedup (serial / parallel): %.2fx%n", (double) serialTime / parallelTime);

        // ---------- Task 2: stress test in place (RecursiveAction) ----------
        long t3 = System.nanoTime();
        for (int i = 0; i < size; i++) {
            if (serialCopy[i] > riskCap) {
                serialCopy[i] = serialCopy[i] * 1.05;
            }
        }
        long serialStress = System.nanoTime() - t3;

        long t4 = System.nanoTime();
        pool.invoke(new PortfolioThresholdTask(assets, 0, size, riskCap));
        long parallelStress = System.nanoTime() - t4;

        double stressedTotal = pool.invoke(new ParallelRiskCalculator(assets, 0, size));

        System.out.println("\n--- Stress Test: +5% on values above " + riskCap + " (RecursiveAction) ---");
        System.out.printf("Serial stress time   : %.3f ms%n", serialStress / 1e6);
        System.out.printf("Parallel stress time : %.3f ms%n", parallelStress / 1e6);
        System.out.printf("Total risk after stress test: %.2f%n", stressedTotal);

        // Check that parallel and serial give the same array
        boolean same = true;
        for (int i = 0; i < size; i++) {
            if (assets[i] != serialCopy[i]) {
                same = false;
                break;
            }
        }
        System.out.println("Parallel result matches serial result: " + same);

        pool.shutdown();
    }
}
