public class CacheLocalityBenchmark {
    static final int N = 2000;
    static double[][] matrix = new double[N][N];

    // Row-major: inner loop moves along a row (consecutive memory)
    static double traverseRowMajor() {
        double sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    // Column-major: inner loop jumps to a different row each step
    static double traverseColumnMajor() {
        double sum = 0;
        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        // Fill the matrix
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = (i + j) % 10;
            }
        }

        // Warm-up so the JVM compiles both methods before timing
        traverseRowMajor();
        traverseColumnMajor();

        int runs = 10;
        double totalRow = 0;
        double totalCol = 0;
        double sumRow = 0;
        double sumCol = 0;

        System.out.println("Matrix size: " + N + " x " + N);
        System.out.println("Run | Row-Major (ms) | Column-Major (ms)");

        for (int r = 1; r <= runs; r++) {
            long t1 = System.nanoTime();
            sumRow = traverseRowMajor();
            long rowTime = System.nanoTime() - t1;

            long t2 = System.nanoTime();
            sumCol = traverseColumnMajor();
            long colTime = System.nanoTime() - t2;

            double rowMs = rowTime / 1e6;
            double colMs = colTime / 1e6;
            totalRow += rowMs;
            totalCol += colMs;
            System.out.printf("%3d | %14.3f | %17.3f%n", r, rowMs, colMs);
        }

        double avgRow = totalRow / runs;
        double avgCol = totalCol / runs;

        System.out.println("\nSum check (row = col): " + sumRow + " = " + sumCol);
        System.out.printf("Average Row-Major time    : %.3f ms%n", avgRow);
        System.out.printf("Average Column-Major time : %.3f ms%n", avgCol);
        System.out.printf("Speedup (Column / Row)    : %.2fx%n", avgCol / avgRow);
    }
}
