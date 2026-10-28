import java.util.Random;

public class SensorGridMonitorApp {
    public static void main(String[] args) throws InterruptedException {
        int size = 50000;
        int numThreads = 4;

        // Generate sensor telemetry values between 0 and 100
        double[] sensorData = new double[size];
        Random rand = new Random(7);
        for (int i = 0; i < size; i++) {
            sensorData[i] = rand.nextDouble() * 100;
        }

        // Split the array into 4 equal parts
        int chunk = size / numThreads;
        ParallelMinSearch[] minTasks = new ParallelMinSearch[numThreads];
        ParallelMaxSearch[] maxTasks = new ParallelMaxSearch[numThreads];
        Thread[] workers = new Thread[numThreads];

        long startTime = System.nanoTime();

        for (int t = 0; t < numThreads; t++) {
            int start = t * chunk;
            int end = (t == numThreads - 1) ? size : start + chunk;
            minTasks[t] = new ParallelMinSearch(sensorData, start, end);
            maxTasks[t] = new ParallelMaxSearch(sensorData, start, end);

            final ParallelMinSearch minTask = minTasks[t];
            final ParallelMaxSearch maxTask = maxTasks[t];

            // Each worker thread runs the min and max search on its own part
            workers[t] = new Thread(() -> { // Start the worker to process its assigned sensor data
                minTask.processGrid();
                maxTask.processGrid();
            }, "Worker-" + (t + 1));

            workers[t].start();
        }

        for (int t = 0; t < numThreads; t++) {
            workers[t].join();
        }
        long parallelTime = System.nanoTime() - startTime; // threads only, no printing

        // Combine the partial results
        double globalMin = minTasks[0].getMinValue();
        double globalMax = maxTasks[0].getMaxValue();
        for (int t = 0; t < numThreads; t++) {
            System.out.printf("Worker-%d (indexes %d-%d) -> local min: %.4f | local max: %.4f%n",
                    t + 1, t * chunk, (t == numThreads - 1 ? size : (t + 1) * chunk) - 1,
                    minTasks[t].getMinValue(), maxTasks[t].getMaxValue());
            if (minTasks[t].getMinValue() < globalMin) globalMin = minTasks[t].getMinValue();
            if (maxTasks[t].getMaxValue() > globalMax) globalMax = maxTasks[t].getMaxValue();
        }

        // Serial search to check the answer
        long serialStart = System.nanoTime();
        double serialMin = sensorData[0];
        double serialMax = sensorData[0];
        for (int i = 1; i < size; i++) {
            if (sensorData[i] < serialMin) serialMin = sensorData[i];
            if (sensorData[i] > serialMax) serialMax = sensorData[i];
        }
        long serialTime = System.nanoTime() - serialStart;

        System.out.println("\n=== Global Critical Thresholds ===");
        System.out.printf("Global minimum temperature : %.4f%n", globalMin);
        System.out.printf("Global peak vibration      : %.4f%n", globalMax);
        System.out.printf("Parallel time: %.3f ms%n", parallelTime / 1e6);
        System.out.printf("Serial time  : %.3f ms%n", serialTime / 1e6);
        System.out.println("Matches serial result: "
                + (globalMin == serialMin && globalMax == serialMax));
    }
}
