package graphic;

import helper.Compute;

public class Camera {
	double[] c = new double[3];
	double[] pref = new double[3];
	double[] v_ = new double[3];
	double h;
	double d;
	double f;
	boolean remove_back = false;
	
	double[] n = new double[3];
	double[] u = new double[3];
	double[] v = new double[3];
	
	double[][] r = new double[4][4];
	double[][] t = new double[4][4];
	double[][] m_view = new double[4][4];
	double[][] m_pers = new double[4][4];
	
	// matrix M_trans = M_pers * M_view
	double[][] m_trans = new double[4][4];
	
	public Camera() {
	}

	public void initial(double[] c, double[] pref, double[] v_, double h, double d, double f, int remove_back) {
		this.c = c;
		this.pref = pref;
		this.v_ = v_;
		this.h = h;
		this.d = d;
		this.f = f;
		if (remove_back != 0) {
			this.remove_back = true;
		}
	}
	
	public void compute_matrix() {
		// camera coordinate
		n = Compute.unit_vector(Compute.vector_minus(pref, c));
		u = Compute.unit_vector(Compute.vector_cross(n, v_));
		v = Compute.vector_cross(u, n);
		
		// matrix R, T
		r[3][3] = 1;
		for (int i = 0; i < 3; i++) {
			r[0][i] = u[i];
			r[1][i] = v[i];
			r[2][i] = n[i];
		}
		
		for (int i = 0; i < 4; i++) {
			t[i][i] = 1;
		}
		t[0][3] = -c[0];
		t[1][3] = -c[1];
		t[2][3] = -c[2];
		
		// matrix M_view = R * T
		m_view = Compute.matrix_dot(r, t);
		
		// matrix M_pers
		m_pers[0][0] = d / h;
		m_pers[1][1] = d / h;
		m_pers[2][2] = f / (f - d);
		m_pers[2][3] = -d * f / (f - d);
		m_pers[3][2] = 1;
		
		// matrix M_trans = M_pers * M_view
		m_trans = Compute.matrix_dot(m_pers, m_view);
	}
}
