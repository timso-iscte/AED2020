//turno L5097-1TP04
//Numero 93911
//Tomas Isidro Martins

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	final private double mean;
	final private double stddev;
	final private double confidenceLo;
	final private double confidenceHi;

	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0)
			throw new IllegalArgumentException(" n and trials must be higher than 0");
		double vecFraction[] = new double[trials]; // to store the thresholds
		int nTrial = trials;
		for (int i = 0; i != nTrial; i++) {
			Percolation percolation = new Percolation(n);
			while (!percolation.percolates()) {
				int a = StdRandom.uniform(n) + 1; // pq chama menos uma vez um method de StdRandom
				int b = StdRandom.uniform(n) + 1;
				percolation.open(a, b);
			}
			vecFraction[i] = (percolation.numberOfOpenSites() / (double) (n * n)); // stores the thresholds of trial i
																					// // out of nTrials
		}
		mean = StdStats.mean(vecFraction);
		stddev = StdStats.stddev(vecFraction);
		confidenceLo = (mean() - (1.96 / Math.sqrt(nTrial)));
		confidenceHi = (mean() + (1.96 / Math.sqrt(nTrial)));
	}

	// sample mean of percolation threshold
	public double mean() {
		return this.mean;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return this.stddev;
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return this.confidenceLo;
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return this.confidenceHi;
	}

	// test client
	public static void main(String[] args) {
		int n = StdIn.readAllInts()[0];
		int trials = StdIn.readAllInts()[1];
		PercolationStats stats = new PercolationStats(n, trials);
		System.out.println("mean= " + stats.mean());
		System.out.println("stddev= " + stats.stddev());
		System.out.println("95% confidence interval= " + stats.confidenceLo() + ", " + stats.confidenceHi());
	}
}
