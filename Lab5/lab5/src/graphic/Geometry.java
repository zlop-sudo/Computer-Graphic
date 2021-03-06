package graphic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import helper.Compute;

public class Geometry {
	// number of points
	int point_n;
	// number of polygons
	int polygon_n;
	// world coordinate points
	double[][] points;
	// polygons
	Polygon[] polygons;
	// screen points
	double[][] screen_points;
	// vertex vector
	double[][] vertex_vector;
	
	// record the points location to get the model's location range
	double maxx = Double.MIN_VALUE, minx = Double.MAX_VALUE;
	double maxy = Double.MIN_VALUE, miny = Double.MAX_VALUE;
	double maxz = Double.MIN_VALUE, minz = Double.MAX_VALUE;
	
	String pathname = "./geometry.d.txt";
	
	double groundz;
	
	public Geometry() {
	}
	
	public void initial() {
		maxx = Double.MIN_VALUE;
		minx = Double.MAX_VALUE;
		maxy = Double.MIN_VALUE;
		miny = Double.MAX_VALUE;
		maxz = Double.MIN_VALUE;
		minz = Double.MAX_VALUE;
		try {
			File data = new File(pathname);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(data));
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			
			String split = "[" + " " + "	" + "]";
			
			line = br.readLine();
			String[] number = line.split(split);
			int k = 0;
			while (!is_num(number[k])) {
				k++;
			}
			point_n = Integer.parseInt(number[k++]);
			while (!is_num(number[k])) {
				k++;
			}
			polygon_n = Integer.parseInt(number[k]);
			points = new double[point_n][3];
			polygons = new Polygon[polygon_n];
			screen_points = new double[point_n][3];
			vertex_vector = new double[point_n][3];
			
			for (int i = 0; i < point_n; i++) {
				line = br.readLine();
				String[] values = line.split(split);
				k = 0;
				while (!is_num(values[k])) {
					k++;
				}
				points[i][0] = Double.parseDouble(values[k++]);
				while (!is_num(values[k])) {
					k++;
				}
				points[i][1] = Double.parseDouble(values[k++]);
				while (!is_num(values[k])) {
					k++;
				}
				points[i][2] = Double.parseDouble(values[k]);
				for (int j = 0; j < 3; j++) {
					updateLoc(points[i][j], j);
				}
			}
			
			for (int i = 0; i < polygon_n; i++) {
				line = br.readLine();
				String[] values = line.split(split);
				k = 0;
				while (!is_num(values[k])) {
					k++;
				}
				int n = Integer.parseInt(values[k++]);
				polygons[i] = new Polygon(n);
				for (int j = 0; j < n; j++) {
					while (!is_num(values[k])) {
						k++;
					}
					polygons[i].points[j] = Integer.parseInt(values[k++]) - 1;
				}
			}
			
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// for read txt
	public boolean is_num(String s) {
		if (s.length() > 0) {
			if ((s.charAt(0) >= '0' && s.charAt(0) <= '9') || s.charAt(0) == '-') {
				return true;
			}
		}
		return false;
	}
	
	// update the location range
	public void updateLoc(double a, int b) {
		if (b == 0) {
			if (a > maxx) {
				maxx = a;
			}
			if (a < minx) {
				minx = a;
			}
		}
		else if (b == 1) {
			if (a > maxy) {
				maxy = a;
			}
			if (a < miny) {
				miny = a;
			}
		}
		else if (b == 2) {
			if (a > maxz) {
				maxz = a;
			}
			if (a < minz) {
				minz = a;
			}
		}
	}
	
	// read a new file
	public void reset(String pathname) {
		this.pathname = pathname;
		initial();
	}
	
	// compute every polygon's back-face culling
	public void polygon_back (boolean remove_back, double[] n) {
		if (remove_back) {
			for (Polygon polygon : polygons) {
				int p1 = polygon.points[0];
				int p2 = polygon.points[1];
				int p3 = polygon.points[2];
				double[] l1 = Compute.vector_minus(points[p1], points[p2]);
				double[] l2 = Compute.vector_minus(points[p2], points[p3]);
				double[] np = Compute.vector_cross(l1, l2);
				double res = Compute.vector_dot(np, n);
				if (res > 0) {
					polygon.back_face = false;
				}
				else {
					polygon.back_face = true;
				}
			}
		}
	}
	
	// compute every polygon's center point
	public void polygon_center () {
		for (Polygon polygon : polygons) {
			int p1 = polygon.points[0];
			int p2 = polygon.points[1];
			int p3 = polygon.points[2];
			double[] l1 = Compute.vector_minus(points[p1], points[p2]);
			double[] l2 = Compute.vector_minus(points[p2], points[p3]);
			double[] np = Compute.vector_cross(l1, l2);
			// compute every polygon's normal vector
			polygon.normal = Compute.unit_vector(np);

			int n = polygon.n;
			double x = 0;
			double y = 0;
			double z = 0;
			for (int i = 0; i < n; i++) {
				x += points[polygon.points[i]][0];
				y += points[polygon.points[i]][1];
				z += points[polygon.points[i]][2];
			}
			polygon.center[0] = x / n;
			polygon.center[1] = y / n;
			polygon.center[2] = z / n;
		}
	}

	// compute vertex vector
	public void vertex_Vector() {
		HashMap<Integer, List<Integer>> vector_Adjacent = new HashMap<Integer, List<Integer>>();
		for (int i = 0; i < point_n; i++) {
			ArrayList<Integer> ad_polygons = new ArrayList<Integer>();
			vector_Adjacent.put(i, ad_polygons);
		}

		// record each vertex's adjacent polygons
		for (int i = 0; i < polygon_n; i++) {
			for (int point : polygons[i].points) {
				vector_Adjacent.get(point).add(i);
			}
		}

		// compute each vertex's normal
		for (int i = 0; i < point_n; i++) {
			double[] normal = new double[3];
			for (int j = 0; j < 3; j++) {
				for (Integer k : vector_Adjacent.get(i)) {
					normal[j] += polygons[k].normal[j];
				}
			}
			vertex_vector[i] = Compute.unit_vector(normal);
		}
	}
	
	// change the world coordinate to camera coordinate
	public void project (double[][] m) {
		for (int i = 0; i < point_n; i++) {
			double[] camera_coor = Compute.matrix_dot_vector(m, Compute.to_homo_coor(points[i]));
			screen_points[i] = Compute.to_3D_coor(camera_coor);
		}
	}
	
	// create ground for shadow
	public void addGround() {
		double groundz = minz - (maxz - minz) / 2;
		this.groundz = groundz;
		
		double centerx = maxx / 2.0 + minx / 2.0;
		double centery = maxy / 2.0 + miny / 2.0;
		double xrange = maxx - minx;
		double yrange = maxy - miny;
		
		double groundScale = 1;
		
		double xbegin = centerx - xrange * groundScale;
		double xend = centerx + xrange * groundScale;
		double ybegin = centery - yrange * groundScale;
		double yend = centery + yrange * groundScale;
		
		// ground edge's point
		int pnumber = 2;
		
		int newpoint_n = point_n + pnumber * pnumber;
		int newpolygon_n = polygon_n + (pnumber - 1) * (pnumber - 1);
		
		double[][] newpoints = new double[newpoint_n][3];
		Polygon[] newpolygons = new Polygon[newpolygon_n];
		double[][] newscreen_points = new double[newpoint_n][3];
		double[][] newvertex_vector = new double[newpoint_n][3];
		
		for (int i = 0; i < point_n; i++) {
			for (int j = 0; j < 3; j++) {
				newpoints[i][j] = points[i][j];
			}
		}
		
		for (int i = 0; i < polygon_n; i++) {
			newpolygons[i] = polygons[i];
		}
		
		// add ground points
		int p_index = point_n;
		double xstep = (xend - xbegin) / (pnumber - 1);
		double ystep = (yend - ybegin) / (pnumber - 1);
		
		for (double x = xbegin; x <= xend; x += xstep) {
			for (double y = ybegin; y <= yend; y += ystep) {
				newpoints[p_index][0] = x;
				newpoints[p_index][1] = y;
				newpoints[p_index][2] = groundz;
				p_index++;
			}
		}
		
		// add ground polygons
		int po_index = polygon_n;
		
		for (int yline = 0; yline < pnumber - 1; yline++) {
			for (int xline = 0; xline < pnumber - 1; xline++) {
				newpolygons[po_index] = new Polygon(4);
				newpolygons[po_index].points[0] = point_n + yline * pnumber + xline;
				newpolygons[po_index].points[1] = point_n + yline * pnumber + xline + 1;
				newpolygons[po_index].points[3] = point_n + (yline + 1) * pnumber + xline;
				newpolygons[po_index].points[2] = point_n + (yline + 1) * pnumber + xline + 1;
				po_index++;
			}
		}
		
		point_n = newpoint_n;
		polygon_n = newpolygon_n;
		points = newpoints;
		polygons = newpolygons;
		screen_points = newscreen_points;
		vertex_vector = newvertex_vector;
		
		this.polygon_center();
		this.vertex_Vector();
	}
}
