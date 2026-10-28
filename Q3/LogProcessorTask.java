// Task that implements the Runnable interface
public class LogProcessorTask implements Runnable {
    @Override
    // Process five log metrics with a short delay between iterations
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("[LogProcessor] Log metric iteration " + i
                    + " | Thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000); // pause 1000 ms
            } catch (InterruptedException e) {
                System.out.println("LogProcessorTask interrupted");
            }
        }
    }
}
