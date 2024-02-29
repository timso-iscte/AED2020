//turno L5097-1TP04
//Numero 93911
//Tomas Isidro Martins

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	final private WeightedQuickUnionUF vec; // wquf to see if system percolates
	final private WeightedQuickUnionUF vecFull; // wquf to see if sites are full. this one doesnt connect to the bottom
												// site, so it doesnt backwash

	final private int nGrid; // size of the side of the grid
	private boolean[] grid;
	private int countOpenSites;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("must be higher than 0");
		vec = new WeightedQuickUnionUF((n * n) + 2); // +2 to create two additional invisible sites, one on top, one on
														// the bottom. Im using this one to see if the system percolates
		vecFull = new WeightedQuickUnionUF((n * n) + 1); // +1 to create just the top invisible site. Im using this one
															// to see if the sites are full.
		nGrid = n;
		grid = new boolean[(n * n) + 2]; // +2 to account for the invisible sites
		// start with the top site open
		grid[0] = true;
		// start with the bottom site open
		grid[(nGrid * nGrid) + 1] = true;
	}

	// opens a site, being the top left corner the (1, 1) position
	public void open(int a, int b) { // a being row and b being column
		if (a <= 0 || b <= 0 || a > nGrid || b > nGrid)
			throw new IllegalArgumentException("Out Of Bounds");
		if (!isOpen(a, b)) {
			int c = ((a - 1) * nGrid) + (b);
			grid[c] = true;
			// if open, connects to right
			if (b + 1 <= nGrid && grid[c + 1] == true) {
				vec.union(c, c + 1);
				vecFull.union(c, c + 1);
			}

			// if open, connects to left
			if (b - 1 > 0 && grid[c - 1] == true) {
				vec.union(c, c - 1);
				vecFull.union(c, c - 1);
			}

			// if open, connects to upper
			if (a != 1 && a - 1 >= 0 && grid[c - nGrid] == true) {
				vec.union(c, c - nGrid);
				vecFull.union(c, c - nGrid);
			}

			// if open, connects to bottom
			if (a != nGrid && a + 1 <= nGrid && grid[c + nGrid] == true) {
				vec.union(c, c + nGrid);
				vecFull.union(c, c + nGrid);
			}

			// if on the first row, connect to 0 (top invisible site)
			if (a == 1) {
				vec.union(c, 0);
				vecFull.union(c, 0);
			}

			// if on the last row, connect to n * n + 1 (bottom invisible site)
			if (a == nGrid)
				vec.union(c, ((nGrid * nGrid) + 1));
			countOpenSites++;
		}
	}

	// is the site (row, col) open?
	public boolean isOpen(int a, int b) {
		if (a <= 0 || b <= 0 || a > nGrid || b > nGrid)
			throw new IllegalArgumentException("Out Of Bounds");
		return grid[((a - 1) * nGrid) + (b)];
	}

	// is the site (row, col) full?
	public boolean isFull(int a, int b) {
		if (a <= 0 || b <= 0 || a > nGrid || b > nGrid)
			throw new IllegalArgumentException("Out Of Bounds");

		return vecFull.find(((a - 1) * nGrid) + (b)) == vecFull.find(0);
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return countOpenSites;
	}

	// does the system percolate?
	public boolean percolates() {
		return (vec.find(0) == vec.find((nGrid * nGrid) + 1));
	}

}
