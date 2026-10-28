// Abstract parent class for sensor grid searches
public abstract class SensorSearchEngine implements Runnable {
    protected double[] sensorData;
    protected int startIdx;
    protected int endIdx;   // exclusive

    public SensorSearchEngine(double[] sensorData, int startIdx, int endIdx) {
        this.sensorData = sensorData;
        this.startIdx = startIdx;
        this.endIdx = endIdx;
    }

    // Each subclass decides how to process its part of the grid
    public abstract void processGrid();

    // Lets a Thread run the search
    @Override
    public void run() {
        processGrid();
    }
}
