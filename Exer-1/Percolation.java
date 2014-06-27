public class Percolation {

	private int size;
	private WeightedQuickUnionUF uf;
	private boolean siteMatrix[][];
	private int MAX; // size * size
	/*
	 * Initialize all sites to be blocked.
	 * Repeat the following until the system percolates:
	 * Choose a site (row i, column j) uniformly at random among all blocked sites.
	 * Open the site (row i, column j). 
	 * The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold. 
	 */
	public Percolation(int N) // create N-by-N grid, with all sites blocked
	{
		uf = new WeightedQuickUnionUF(N*N+2);
		size = N;
		siteMatrix = new boolean[N][N];
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				siteMatrix[i][j] = false;
			}
		}
		MAX = N*N;
	}   
	
	private int xyTo1D(int x, int y)
	{
		// array index 0 should correspond to (1, 1)
		return ((x-1)*size) + (y-1);
	}
	
	// open site (row i, column j) if it is not already open
	public void open(int i, int j) 
	{
		int val = xyTo1D(i, j);
		int valN;
		int r = i - 1;
		int c = j - 1;
		
		siteMatrix[r][c] = true;
		// Check if newly opened site connected to neighbours
		if ((r-1) >= 0 && siteMatrix[r-1][c]) {
			valN = xyTo1D((i-1), j);
			uf.union(val, valN);
		}
		if ((r+1) < size && siteMatrix[r+1][c]) {
			valN = xyTo1D((i+1), j);
			uf.union(val, valN);
		}
		if ((c-1) >= 0 && siteMatrix[r][c-1]) {
			valN = xyTo1D(i, (j-1));
			uf.union(val, valN);
		}
		if ((c+1) < size && siteMatrix[r][c+1]) {
			valN = xyTo1D(i, (j+1));
			uf.union(val, valN);
		}
		// Top row block opened. Connect to virtual top - MAX
		if (val >= 0 && val < size) 
			uf.union(MAX, val);
		
		// Bottom row block opened. Connect to virtual bottom - MAX + 1
		if (val >= size*(size-1) && val < MAX) 
			uf.union(MAX+1, val);
	}         

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) 
	{
		// as (1, 1) should correspond to (0, 0)
		return siteMatrix[i-1][j-1]; 
	}

	// is site (row i, column j) full? Or whether it is connected to the virtual top?
	public boolean isFull(int i, int j) 
	{
		int val = xyTo1D(i, j);
		return uf.connected(MAX, val);
	}

	// Check whether system percolate?
	public boolean percolates() 
	{
		return uf.connected(MAX, MAX+1);
	}
	
	public double startOpening(int size)
	{
		int ctr = 0;
		// Percolation p = new Percolation(size);
		int x, y;
		while(!percolates()) {
			x = StdRandom.uniform(0, size) + 1;
			y = StdRandom.uniform(0, size) + 1;
			while(isOpen(x, y)) {
				x = StdRandom.uniform(0, size) + 1;
				y = StdRandom.uniform(0, size) + 1;
			}
			open(x, y);
			// StdOut.println("x : " + x + ", y : " + y);
			ctr++;
		}

		return (double)ctr/size;
	}
	
}
