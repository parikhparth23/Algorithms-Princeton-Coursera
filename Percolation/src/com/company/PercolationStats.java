package com.company;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int N;
    private int T;
    private double [] threshold;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("N & T have to be greater than 0.");
        }

        this.N = N;
        this.T = T;

        threshold = new double[T];

        for(int i = 0; i < T; i++) {
            Percolation p = new Percolation(N);

            int openCells = 0;

            while(!p.percolates()) {
                openRandomCell(p);
                openCells++;
            }

            threshold[i] = (double) openCells / (N * N);
        }
    }

    public void openRandomCell(Percolation p) {
        boolean openNode = true;
        int row = 0;
        int column = 0;

        while (openNode) {
            row = StdRandom.uniform(1, this.N + 1);
            column = StdRandom.uniform(1, this.N + 1);

            openNode = p.isOpen(row, column);
        }
        p.open(row, column);
    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
        return StdStats.stddev(threshold);
    }

    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(this.T));
    }

    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(this.T));
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(N, T);

        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
    }
}
