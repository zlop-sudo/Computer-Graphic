package graphic;

import helper.Compute;

// This class compute the illumination for each point in world coordinate using their normals and position
public class Illumination {
	public double[] l;
	public double i_light;
	public double i_ambient;
	public double ka;
	public double kd;
	public double ks;
	public double n;
	public double[] viewer;
	
	public void initial (double[] l, double i_light, double i_ambient, double ka, double kd, double ks, double n) {
		this.l = Compute.unit_vector(l);
		this.i_light = i_light;
		this.i_ambient = i_ambient;
		this.ka = ka;
		this.kd = kd;
		this.ks = ks;
		this.n = n;
	}
	
	// Phong illumination model
	public double illumination(double[] N, double[] V) {
		double I = 0;
		// ambient
		I += ka * i_ambient;

		// diffuse
		double diffuse = kd * (Compute.vector_dot(N, l)) * i_light;
		if (diffuse >= 0) {
			I += diffuse;
		}

		// specular
		double cos2 = Compute.vector_dot(l, N) * 2;
		if (cos2 <= 0) {
			return I;
		}
		double[] R = Compute.vector_minus(Compute.vector_mult(N, cos2), l);
		double specular = ks * Math.pow(Compute.vector_dot(V, R), n) * i_light;
		if (specular >= 0) {
			I += specular;
		}

		return I;
	}
	
	// using the result I to compute new RGB
	public int[] shading(double[] N, double[] point, int[] color) {
		int[] res = new int[3];
		double[] V = Compute.unit_vector(Compute.vector_minus(viewer, point));
		double I = illumination(N, V);
		for (int i = 0; i < 3 ; i++) {
			if (color[i] * I >= 256) {
				res[i] = 255;
			} else {
				res[i] = (int) (color[i] * I);
			}
		}
		//System.out.println(res[0] + ", " + res[1] + ", " + res[2]);
		return res;
	}
}
