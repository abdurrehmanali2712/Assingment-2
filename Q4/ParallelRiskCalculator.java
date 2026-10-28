import java.util.concurrent.RecursiveTask;

// Calculates the sum of risk scores using Fork/Join
public class ParallelRiskCalculator extends RecursiveTask<Double> {
    private static final int THRESHOLD = 1000;
    private double[] assets;
    private int start;
    private int end;   // end is exclusive

    public ParallelRiskCalculator(double[] assets, int start, int end) {
        this.assets = assets;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Double compute() {
        
        int length = end - start;

        // Small enough: compute directly
        if (length <= THRESHOLD) {
            double sum = 0;
            for (int i = start; i < end; i++) {
                sum += assets[i];
            }
            return sum;
        }

        // Otherwise split into two sub-tasks
        int mid = start + length / 2; // Divide the current array section into two smaller tasks
        ParallelRiskCalculator left = new ParallelRiskCalculator(assets, start, mid);
        ParallelRiskCalculator right = new ParallelRiskCalculator(assets, mid, end);

        left.fork();                       // run left in another thread
        double rightResult = right.compute(); // compute right in this thread
        double leftResult = left.join();   // wait for left result

        return leftResult + rightResult;
    }
}
