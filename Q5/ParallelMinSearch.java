// Finds the minimum sensor temperature in its segment
public class ParallelMinSearch extends SensorSearchEngine {
    private double minValue;

    public ParallelMinSearch(double[] sensorData, int startIdx, int endIdx) {
        super(sensorData, startIdx, endIdx);
    }

    @Override
    public void processGrid() {
        minValue = sensorData[startIdx];
        for (int i = startIdx + 1; i < endIdx; i++) {
            if (sensorData[i] < minValue) {
                minValue = sensorData[i];
            }
        }
    }

    public double getMinValue() {
        return minValue;
    }
}
