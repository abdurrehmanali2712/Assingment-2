// Thread created by extending the Thread class directly
public class TelemetryThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            // Simple simulated health values
            int cpu = 40 + i * 5; // Generate simulated CPU and memory usage values
            int memory = 55 + i * 3;
            System.out.println("[Telemetry] Update " + i + " | CPU: " + cpu
                    + "% | Memory: " + memory + "% | Thread: " + getName());
            try {
                Thread.sleep(800); // pause 800 ms
            } catch (InterruptedException e) {
                System.out.println("TelemetryThread interrupted");
            }
        }
    }
}
