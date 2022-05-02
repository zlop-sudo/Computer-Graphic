package helper;

public class Compute {
	
	//any length
	public static double[] vector_minus (double[] v1, double[] v2) {
		int n = v1.length;
		double[] res = new double[n];
		for (int i = 0; i < n; i++) {
			res[i] = v1[i] - v2[i];
		}
		return res;
	}

	public static double[] vector_add (double[] v1, double[] v2) {
		int n = v1.length;
		double[] res = new double[n];
		for (int i = 0; i < n; i++) {
			res[i] = v1[i] + v2[i];
		}
		return res;
	}

	public static double[] vector_mult (double[] v, double k) {
		int n = v.length;
		double[] res = new double[n];
		for (int i = 0; i < n; i++) {
			res[i] = v[i] * k;
		}
		return res;
	}
	
	// any length
	public static double vector_dot(double[] v1, double[] v2) {
		int n = v1.length;
		double res = 0;
		for (int i = 0; i < n; i++) {
			res += v1[i] * v2[i];
		}
		return res;
	}
	
	//only vector with length of 3
	public static double[] vector_cross (double[] v1, double[] v2) {
		double[] res = new double[3];
		res[0] = v1[1] * v2[2] - v1[2] * v2[1];
		res[1] = v1[2] * v2[0] - v1[0] * v2[2];
		res[2] = v1[0] * v2[1] - v1[1] * v2[0];
		return res;
	}
	
	// any length
	public static double[] unit_vector (double[] v) {
		int n = v.length;
		double temp = 0;
		for (int i = 0; i < n; i++) {
			temp += v[i] * v[i];
		}
		double module = Math.sqrt(temp);
		for (int i = 0; i < n; i++) {
			v[i] /= module;
		}
		return v;
	}
	
	// any size
	public static double[][] matrix_dot (double[][] m1, double[][] m2) {
		int r1 = m1.length, c1 = m1[0].length;
		int c2 = m2[0].length;
		double[][] res = new double[r1][c2];
		for (int i = 0; i < r1; i++) {
			for (int j = 0; j < c2; j++) {
				for (int k = 0; k < c1; k++) {
					res[i][j] += m1[i][k] * m2[k][j];
				}
			}
		}
		return res;
	}
	
	public static double[] matrix_dot_vector (double[][] m, double[] v) {
		int r = m.length, c = m[0].length;
		double[] res = new double[r];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				res[i] += m[i][j] * v[j];
			}
		}
		return res;
	}
	
	// change vector to homogeneous coordinate
	public static double[] to_homo_coor (double[] v) {
		double[] res = new double[4];
		for (int i = 0; i < 3; i++) {
			res[i] = v[i];
		}
		res[3] = 1;
		return res;
	}
	
	// change homogeneous coordinate to 3 dimensional vector
	public static double[] to_3D_coor (double[] v) {
		double[] res = new double[3];
		for (int i = 0; i < 3; i++) {
			res[i] = v[i] / v[3];
		}
		return res;
	}
	
	// compute vector's vertical vector
	public static double[] getVertical(double[] v) {
		double[] res = new double[3];
		res[0] = 1;
		res[1] = 1;
		res[2] = - (v[0] + v[1]) / v[2];
		return res;
	}
}
