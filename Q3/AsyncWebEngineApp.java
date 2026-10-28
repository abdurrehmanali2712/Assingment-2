public class AsyncWebEngineApp {
    public static void main(String[] args) {
        // Default JVM thread name
        System.out.println("Main thread name: " + Thread.currentThread().getName());

        TelemetryThread telemetry = new TelemetryThread();
        Thread logThread = new Thread(new LogProcessorTask());

        // Custom names
        telemetry.setName("Telemetry-Worker");
        logThread.setName("Log-Worker");

        // Start both threads
        telemetry.start();
        logThread.start();

        // Log execution states
        System.out.println(telemetry.getName() + " alive: " + telemetry.isAlive());
        System.out.println(logThread.getName() + " alive: " + logThread.isAlive());

        // Wait for both threads to finish
        try {
            telemetry.join();
            logThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main interrupted");
        }

        System.out.println(telemetry.getName() + " alive: " + telemetry.isAlive());
        System.out.println(logThread.getName() + " alive: " + logThread.isAlive());
        System.out.println("All threads finished.");
    }
}
