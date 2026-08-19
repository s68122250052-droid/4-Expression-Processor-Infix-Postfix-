package group4;

import group4.algorithms.AlgorithmA;
import group4.algorithms.AlgorithmB;
import group4.models.EvaluationResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ExperimentRunner {
    private static final int[] SIZES = {100, 1000, 10000, 50000};
    private static final int ROUNDS = 5;

    public static void main(String[] args) throws IOException {
        new File("results").mkdirs();
        run("results/experiment_results.csv");
    }

    public static void run(String outputCsvPath) throws IOException {
        try (PrintWriter csv = new PrintWriter(new FileWriter(outputCsvPath))) {
            csv.println("n,algorithm,avg_time_ns,avg_push,avg_pop,avg_comparisons");

            for (int n : SIZES) {
                long[] ta = new long[ROUNDS], pa = new long[ROUNDS], qa = new long[ROUNDS], ca = new long[ROUNDS];
                long[] tb = new long[ROUNDS], pb = new long[ROUNDS], qb = new long[ROUNDS], cb = new long[ROUNDS];

                for (int r = 0; r < ROUNDS; r++) {
                    String expr = ExpressionGenerator.generate(n, 1000L * n + r);

                    EvaluationResult a = AlgorithmA.evaluate(expr);
                    ta[r] = a.getExecutionTimeNs();
                    pa[r] = a.getPushCount();
                    qa[r] = a.getPopCount();
                    ca[r] = a.getComparisonCount();

                    EvaluationResult b = AlgorithmB.evaluate(expr);
                    tb[r] = b.getExecutionTimeNs();
                    pb[r] = b.getPushCount();
                    qb[r] = b.getPopCount();
                    cb[r] = b.getComparisonCount();
                }

                write(csv, n, "A", avg(ta), avg(pa), avg(qa), avg(ca));
                write(csv, n, "B", avg(tb), avg(pb), avg(qb), avg(cb));
            }
        }
        System.out.println("Saved results to: " + outputCsvPath);
    }

    private static void write(PrintWriter csv, int n, String alg,
                              double t, double p, double q, double c) {
        csv.printf("%d,%s,%.0f,%.1f,%.1f,%.1f%n", n, alg, t, p, q, c);
    }

    private static double avg(long[] v) {
        long sum = 0;
        for (long x : v) sum += x;
        return (double) sum / v.length;
    }
}
