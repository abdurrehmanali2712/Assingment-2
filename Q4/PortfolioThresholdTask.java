import java.util.concurrent.RecursiveAction;

// In-place adjustment: apply a 5% penalty to values above the risk cap
public class PortfolioThresholdTask extends RecursiveAction {
    private static final int THRESHOLD = 1000;
    private double[] assets;
    private int start;
    private int end;   // end is exclusive
    private double riskCap;

    public PortfolioThresholdTask(double[] assets, int start, int end, double riskCap) {
        this.assets = assets;
        this.start = start;
        this.end = end;
        this.riskCap = riskCap;
    }

    @Override
    protected void compute() {
        int length = end - start;

        if (length <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                if (assets[i] > riskCap) {
                    assets[i] = assets[i] * 1.05; // 5% volatility stress-test penalty
                }
            }
            return;
        }

        int mid = start + length / 2;
        PortfolioThresholdTask left = new PortfolioThresholdTask(assets, start, mid, riskCap);
        PortfolioThresholdTask right = new PortfolioThresholdTask(assets, mid, end, riskCap);

        invokeAll(left, right); // fork both and wait for both
    }
}
