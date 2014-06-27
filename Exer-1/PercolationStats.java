
public class PercolationStats {
	private double count[];
	private int repCount;
	
	public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
	{
		count = new double[T];
		repCount = T;
		for(int x=0; x<T; x++) {
			Percolation p = new Percolation(N);
			count[x] = p.startOpening(N);
		}
	}
	
	public double mean()                     // sample mean of percolation threshold
	{
		return StdStats.mean(count);
	}
	
	public double stddev()                   // sample standard deviation of percolation threshold
	{
		return StdStats.stddev(count);
	}
	
	public double confidenceLo()             // returns lower bound of the 95% confidence interval
	{
		double mu = mean();
		double sigma = stddev();
		
		return (mu - (1.96*sigma)/Math.sqrt(repCount)); 
	}
	
	public double confidenceHi()             // returns upper bound of the 95% confidence interval
	{
		double mu = mean();
		double sigma = stddev();
		
		return (mu + (1.96*sigma)/Math.sqrt(repCount)); 
	}
	
	public static void main(String[] args)   // test client, described below
	{
		int N = 200;
		int T = 100;
		PercolationStats p = new PercolationStats(N, T);
		
		StdOut.println("mean\t\t\t= " + p.mean());
		StdOut.println("stddev\t\t\t= " + p.stddev());
		StdOut.println("95% confidence interval\t= " + p.confidenceLo() + ", " + p.confidenceHi());
	}
}
