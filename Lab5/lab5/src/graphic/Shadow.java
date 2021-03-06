package graphic;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import helper.Compute;

public class Shadow {
	double[][] z_buffer;
	double[][] screen_points;
	Polygon[] polygons;
	int size;
	// projection matrix
	double[][] m;
	
	public Shadow() {
	}
	
	public void initial(double[][] pers_points, Polygon[] polygons, int size) {
		this.polygons = polygons;
		this.size = size;
		z_buffer = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				z_buffer[i][j] = Double.MAX_VALUE;
			}
		}
		screen_points = new double[pers_points.length][3];
		
		// change the perspective points to screen coordinate
		for (int i = 0; i < pers_points.length; i++) {
			screen_points[i][0] = (size - (pers_points[i][0] + 1) * size / 2);
			screen_points[i][1] = (size - (pers_points[i][1] + 1) * size / 2);
			screen_points[i][2] = (pers_points[i][2] * size);
		}
	}
	
	// z-buffer algorithm from the light source view
	public void scan_Conversion() {
		for (int i = 0; i < polygons.length; i++) {
			List<List<double[]>> et = new LinkedList<>();
			for (int j = 0; j < size; j++) {
				et.add(new LinkedList<>());
			}
			LinkedList<double[]> aet = new LinkedList<>();
			// get random color for this polygon
			
			// initialize edge table
			for (int j = 0; j < polygons[i].n; j++) {
				// point1 and point2 are two points of one line of this polygon
				double[] point1 = screen_points[polygons[i].points[j]];
				double[] point2 = screen_points[polygons[i].points[(j + 1) % polygons[i].n]];
				// skip lines that parallel to scan line
				if (Math.abs(point1[1] - point2[1]) < 1) {
					continue;
				}
				int ymin = (int) Math.min(point1[1], point2[1]);
				// in order to get z value of each pixel, 
				// this data structure need to store the z value in 4th place, z slope in 5th place
				double[] line = new double[5];
				// y max not counted
				// x_min
				// slope
				if (point1[1] < point2[1]) {
					line[0] = (int) point2[1];
					line[1] = point1[0];
				}
				else {
					line[0] = (int) point1[1];
					line[1] = point2[0];
				}
				// slope
				line[2] = (point2[1] - point1[1]) / (point2[0] - point1[0]);
				// z value
				if (point1[0] < point2[0]) {
					line[3] = point1[2];
				}
				else {
					line[3] = point2[2];
				}
				// z slope (according to y + 1)
				line[4] = (point2[2] - point1[2]) / (point2[1] - point1[1]);
				// add line to edge table
				if (ymin >= 0 && ymin < size) {
					et.get(ymin).add(line);
				}
			}
			
			// loop for every y_min
			for (int y = 0; y < size; y++) {
				// update AET
				if (aet.size() != 0) {
					Iterator<double[]> itr = aet.iterator();
					while (itr.hasNext()) {
						double[] line = itr.next();
						if ((int) line[0] == y) {
							itr.remove();
							continue;
						}
						// update x_min
						line[1] = line[1] + 1 / line[2];
						// update z
						line[3] = line[3] + line[4];
					}
				}
				// move lines from ET to AET
				for (double[] line : et.get(y)) {
					aet.add(line);
				}
				if (aet.size() == 0) {
					continue;
				}
				// resort AET
				Collections.sort(aet, new Comparator<double[]>() {
					@Override
					public int compare(double[] l1, double[] l2) {
						return (int) ((l1[1] - l2[1]) * 100);
					}
				});
				// update z-buffer and color-buffer
				for (int j = 0; j < aet.size(); j+= 2) {
					// because parallel lines not counted, so aet may have odd number of lines
					if (j + 1 >= aet.size()) {
						break;
					}
					double[] left = aet.get(j);
					double[] right = aet.get(j + 1);
					update_buffer(left, right, y);
				}
			}
		}
	}
	
	public void update_buffer(double[] left, double[] right, int y) {
		double left_x = left[1];
		double left_z = left[3];
		double right_x = right[1];
		double right_z = right[3];
		// slope of z related to change of x
		double k = (right_z - left_z) / (right_x - left_x);
		for (int i = (int) left_x + 1; i <= (int) right_x; i++) {
			if (i >= size) {
				break;
			}
			else if (i < 0) {
				continue;
			}
			double cur_z = left_z + ((double) i - left_x) * k;
			if (cur_z < z_buffer[i][y]) {
				z_buffer[i][y] = cur_z;
			}
		}
	}
	
	public boolean isShadow(double[] wordCoordinate) {
		// compute z
		double[] camera_coor = Compute.matrix_dot_vector(m, Compute.to_homo_coor(wordCoordinate));
		double[] screen_points = Compute.to_3D_coor(camera_coor);
		
		// screen rotate
		double[] screen_points_ = new double[3];
		screen_points_[0] = (size - (screen_points[0] + 1) * size / 2);
		screen_points_[1] = (size - (screen_points[1] + 1) * size / 2);
		screen_points_[2] = (screen_points[2] * size);
		
		if ((int) screen_points_[0] < 0 || (int) screen_points_[0] >= size) {
			return false;
		}
		
		if ((int) screen_points_[1] < 0 || (int) screen_points_[1] >= size) {
			return false;
		}
		
		if (screen_points_[2] > z_buffer[(int) screen_points_[0]][(int) screen_points_[1]]) {
			return true;
		}
		else {
			return false;
		}
		
	}
}
