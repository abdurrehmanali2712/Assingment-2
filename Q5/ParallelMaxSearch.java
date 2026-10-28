// Finds the peak vibration spike in its segment
public class ParallelMaxSearch extends SensorSearchEngine {
    private double maxValue;

    public ParallelMaxSearch(double[] sensorData, int startIdx, int endIdx) {
        super(sensorData, startIdx, endIdx);
    }

    @Override
    public void processGrid() {
        maxValue = sensorData[startIdx];
        for (int i = startIdx + 1; i < endIdx; i++) {
            if (sensorData[i] > maxValue) {
                maxValue = sensorData[i];
            }
        }
    }
// Check each value to find the maximum in this segment
    public double getMaxValue() {
        return maxValue;
    }
}
