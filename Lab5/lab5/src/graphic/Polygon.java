package graphic;

public class Polygon {
	// points number
	public int n;
	// points' indexes
	public int[] points;
	// show back_face or not
	public boolean back_face;
	// normal vector
	public double[] normal;
	// center point
	public double[] center = new double[3]; 
	
	public Polygon (int n) {
		this.n = n;
		points = new int[n];
	}
}
