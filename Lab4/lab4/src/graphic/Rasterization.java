package graphic;

import java.util.*;

public class Rasterization {
	double[][] z_buffer;
	int[][][] color_buffer;
	double[][] screen_points;
	Polygon[] polygons;
	int size;
	// vertex vector
	double[][] vertex_vector;
	// world coordinate points
	double[][] w_points;
	
	public Rasterization() {
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
		color_buffer = new int[size][size][3];
		screen_points = new double[pers_points.length][3];
		
		// change the perspective points to screen coordinate
		for (int i = 0; i < pers_points.length; i++) {
			screen_points[i][0] = (size - (pers_points[i][0] + 1) * size / 2);
			screen_points[i][1] = (size - (pers_points[i][1] + 1) * size / 2);
			screen_points[i][2] = (pers_points[i][2] * size);
		}
	}
	
	// lab2 no shading
//	public void scan_Conversion() {
//		for (int i = 0; i < polygons.length; i++) {
//			// back_culling
//			if (polygons[i].back_face) {
//				continue;
//			}
//			List<List<double[]>> et = new LinkedList<>();
//			for (int j = 0; j < size; j++) {
//				et.add(new LinkedList<>());
//			}
//			LinkedList<double[]> aet = new LinkedList<>();
//			// get random color for this polygon
//			int[] color = random_Color();
//			
//			// initialize edge table
//			for (int j = 0; j < polygons[i].n; j++) {
//				// point1 and point2 are two points of one line of this polygon
//				double[] point1 = screen_points[polygons[i].points[j]];
//				double[] point2 = screen_points[polygons[i].points[(j + 1) % polygons[i].n]];
//				// skip lines that parallel to scan line
//				if (Math.abs(point1[1] - point2[1]) < 1) {
//					continue;
//				}
//				int ymin = (int) Math.min(point1[1], point2[1]);
//				// in order to get z value of each pixel, 
//				// this data structure need to store the z value in 4th place, z slope in 5th place
//				double[] line = new double[5];
//				// y max not counted
//				// x_min
//				// slope
//				if (point1[1] < point2[1]) {
//					line[0] = (int) point2[1];
//					line[1] = point1[0];
//				}
//				else {
//					line[0] = (int) point1[1];
//					line[1] = point2[0];
//				}
//				// slope
//				line[2] = (point2[1] - point1[1]) / (point2[0] - point1[0]);
//				// z value
//				if (point1[0] < point2[0]) {
//					line[3] = point1[2];
//				}
//				else {
//					line[3] = point2[2];
//				}
//				// z slope (according to y + 1)
//				line[4] = (point2[2] - point1[2]) / (point2[1] - point1[1]);
//				// add line to edge table
//				et.get(ymin).add(line);
//			}
//			
//			// loop for every y_min
//			for (int y = 0; y < size; y++) {
//				// update AET
//				if (aet.size() != 0) {
//					Iterator<double[]> itr = aet.iterator();
//					while (itr.hasNext()) {
//						double[] line = itr.next();
//						if ((int) line[0] == y) {
//							itr.remove();
//							continue;
//						}
//						// update x_min
//						line[1] = line[1] + 1 / line[2];
//						// update z
//						line[3] = line[3] + line[4];
//					}
//				}
//				// move lines from ET to AET
//				for (double[] line : et.get(y)) {
//					aet.add(line);
//				}
//				if (aet.size() == 0) {
//					continue;
//				}
//				// resort AET
//				Collections.sort(aet, new Comparator<double[]>() {
//					@Override
//					public int compare(double[] l1, double[] l2) {
//						return (int) ((l1[1] - l2[1]) * 100);
//					}
//				});
//				// update z-buffer and color-buffer
//				for (int j = 0; j < aet.size(); j+= 2) {
//					// because parallel lines not counted, so aet may have odd number of lines
//					if (j + 1 >= aet.size()) {
//						break;
//					}
//					double[] left = aet.get(j);
//					double[] right = aet.get(j + 1);
//					update_buffer(left, right, color, y);
//				}
//			}
//		}
//	}

	// constant shading
	public void constant (Illumination illu) {
		// get random color for this object
		int[] color = random_Color();
		
		for (int i = 0; i < polygons.length; i++) {
			// back_culling
			if (polygons[i].back_face) {
				continue;
			}
			List<List<double[]>> et = new LinkedList<>();
			for (int j = 0; j < size; j++) {
				et.add(new LinkedList<>());
			}
			LinkedList<double[]> aet = new LinkedList<>();

			// compute each polygon's shading color
			int[] polygon_Color = illu.shading(polygons[i].normal, polygons[i].center, color);
			
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
				et.get(ymin).add(line);
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
					update_buffer(left, right, polygon_Color, y);
				}
			}
		}
	}

	// gouraud shading
	public void gouraud (Illumination illu) {
		// get random color for this object
		int[] color = random_Color();

		for (int i = 0; i < polygons.length; i++) {
			// back_culling
			if (polygons[i].back_face) {
				continue;
			}
			List<List<double[]>> et = new LinkedList<>();
			for (int j = 0; j < size; j++) {
				et.add(new LinkedList<>());
			}
			LinkedList<double[]> aet = new LinkedList<>();

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
				// the 6th to 8th is the RGB value in x_min,
				// and 9th is the slope for the change of RGB according to the change of y
				double[] line = new double[11];

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

				// index of two points
				int index1, index2;
				if (point1[1] < point2[1]) {
					index1 = polygons[i].points[j];
					index2 = polygons[i].points[(j + 1) % polygons[i].n];
				}
				else {
					index1 = polygons[i].points[(j + 1) % polygons[i].n];
					index2 = polygons[i].points[j];
				}
				// shading of two vertexes
				int[] color1 = illu.shading(vertex_vector[index1], w_points[index1], color);
				int[] color2 = illu.shading(vertex_vector[index2], w_points[index2], color);
				// shading of x_min
				line[5] = (double) color1[0];
				line[6] = (double) color1[1];
				line[7] = (double) color1[2];
				// slope of color (according to change of y)
				double temp = screen_points[index2][1] - screen_points[index1][1];
				line[8] = (color2[0] - color1[0]) / temp;
				line[9] = (color2[1] - color1[1]) / temp;
				line[10] = (color2[2] - color1[2]) / temp;

				// add line to edge table
				et.get(ymin).add(line);
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
						// update color
						line[5] += line[8];
						line[6] += line[9];
						line[7] += line[10];
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

	// phong shading
	public void phong (Illumination illu) {
		// get random color for this object
		int[] color = random_Color();

		for (int i = 0; i < polygons.length; i++) {
			// back_culling
			if (polygons[i].back_face) {
				continue;
			}
			List<List<double[]>> et = new LinkedList<>();
			for (int j = 0; j < size; j++) {
				et.add(new LinkedList<>());
			}
			LinkedList<double[]> aet = new LinkedList<>();

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
				// the 6th to 8th is the vertex normal in x_min,
				// and 9th is the slope for the change of vector to the change of y
				double[] line = new double[17];

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

				// index of two points
				int index1, index2;
				if (point1[1] < point2[1]) {
					index1 = polygons[i].points[j];
					index2 = polygons[i].points[(j + 1) % polygons[i].n];
				}
				else {
					index1 = polygons[i].points[(j + 1) % polygons[i].n];
					index2 = polygons[i].points[j];
				}
				// vertex of x_min
				line[5] = vertex_vector[index1][0];
				line[6] = vertex_vector[index1][1];
				line[7] = vertex_vector[index1][2];
				// 3D coordinate of x_min
				line[11] = w_points[index1][0];
				line[12] = w_points[index1][1];
				line[13] = w_points[index1][2];
				// slope of color (according to change of y)
				double temp = screen_points[index2][1] - screen_points[index1][1];
				line[8] = (vertex_vector[index2][0] - line[5]) / temp;
				line[9] = (vertex_vector[index2][1] - line[6]) / temp;
				line[10] = (vertex_vector[index2][2] - line[7]) / temp;
				line[14] = (w_points[index2][0] - line[11]) / temp;
				line[15] = (w_points[index2][1] - line[12]) / temp;
				line[16] = (w_points[index2][2] - line[13]) / temp;

				// add line to edge table
				et.get(ymin).add(line);
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
						// update vector
						line[5] += line[8];
						line[6] += line[9];
						line[7] += line[10];
						// update coordinate
						line[11] += line[14];
						line[12] += line[15];
						line[13] += line[16];
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
					update_buffer(left, right, y, color, illu);
				}
			}
		}
	}

	// textureMapping - phong shading
	public void texture(Illumination illu, Texture t) {

		for (int i = 0; i < polygons.length; i++) {
			// back_culling
			if (polygons[i].back_face) {
				continue;
			}
			List<List<double[]>> et = new LinkedList<>();
			for (int j = 0; j < size; j++) {
				et.add(new LinkedList<>());
			}
			LinkedList<double[]> aet = new LinkedList<>();

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
				// the 6th to 8th is the vertex normal in x_min,
				// and 9th is the slope for the change of vector to the change of y
				double[] line = new double[17];

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

				// index of two points
				int index1, index2;
				if (point1[1] < point2[1]) {
					index1 = polygons[i].points[j];
					index2 = polygons[i].points[(j + 1) % polygons[i].n];
				}
				else {
					index1 = polygons[i].points[(j + 1) % polygons[i].n];
					index2 = polygons[i].points[j];
				}
				// vertex of x_min
				line[5] = vertex_vector[index1][0];
				line[6] = vertex_vector[index1][1];
				line[7] = vertex_vector[index1][2];
				// 3D coordinate of x_min
				line[11] = w_points[index1][0];
				line[12] = w_points[index1][1];
				line[13] = w_points[index1][2];
				// slope of color (according to change of y)
				double temp = screen_points[index2][1] - screen_points[index1][1];
				line[8] = (vertex_vector[index2][0] - line[5]) / temp;
				line[9] = (vertex_vector[index2][1] - line[6]) / temp;
				line[10] = (vertex_vector[index2][2] - line[7]) / temp;
				line[14] = (w_points[index2][0] - line[11]) / temp;
				line[15] = (w_points[index2][1] - line[12]) / temp;
				line[16] = (w_points[index2][2] - line[13]) / temp;

				// add line to edge table
				et.get(ymin).add(line);
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
						// update vector
						line[5] += line[8];
						line[6] += line[9];
						line[7] += line[10];
						// update coordinate
						line[11] += line[14];
						line[12] += line[15];
						line[13] += line[16];
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
					update_buffer(left, right, y, illu, t);
				}
			}
		}
	}

	// get random RGB
	public int[] random_Color() {
		int[] res = new int[3];
		Random rand = new Random();
		res[0] = rand.nextInt(256);
		res[1] = rand.nextInt(256);
		res[2] = rand.nextInt(256);
		return res;
	}
	
	// update z-buffer and color-buffer using one odd and one even points
	public void update_buffer(double[] left, double[] right, int[] color, int y) {
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
				color_buffer[i][y][0] = color[0];
				color_buffer[i][y][1] = color[1];
				color_buffer[i][y][2] = color[2];
			}
		}
	}

	// update z-buffer and color-buffer using one odd and one even points (version for gouraud shading)
	public void update_buffer(double[] left, double[] right, int y) {
		double left_x = left[1];
		double left_z = left[3];
		double right_x = right[1];
		double right_z = right[3];
		// slope of z related to change of x
		double k = (right_z - left_z) / (right_x - left_x);
		// slope of color related to change of x
		double kr = (right[5] - left[5]) / (right_x - left_x);
		double kg = (right[6] - left[6]) / (right_x - left_x);
		double kb = (right[7] - left[7]) / (right_x - left_x);
		for (int i = (int) left_x + 1; i <= (int) right_x; i++) {
			if (i >= size) {
				break;
			}
			else if (i < 0) {
				continue;
			}
			double cur_z = left_z + ((double) i - left_x) * k;
			double cur_r = left[5] + ((double) i - left_x) * kr;
			double cur_g = left[6] + ((double) i - left_x) * kg;
			double cur_b = left[7] + ((double) i - left_x) * kb;
			if (cur_z < z_buffer[i][y]) {
				z_buffer[i][y] = cur_z;
				color_buffer[i][y][0] = (int) cur_r;
				color_buffer[i][y][1] = (int) cur_g;
				color_buffer[i][y][2] = (int) cur_b;
			}
		}
	}

	// update z-buffer and color-buffer using one odd and one even points (version for phong shading)
	public void update_buffer(double[] left, double[] right, int y, int[] color, Illumination illu) {
		double left_x = left[1];
		double left_z = left[3];
		double right_x = right[1];
		double right_z = right[3];
		// slope of z related to change of x
		double k = (right_z - left_z) / (right_x - left_x);
		// slope of vector related to change of x
		double kx = (right[5] - left[5]) / (right_x - left_x);
		double ky = (right[6] - left[6]) / (right_x - left_x);
		double kz = (right[7] - left[7]) / (right_x - left_x);
		// slope of  coordinate
		double k_x = (right[11] - left[11]) / (right_x - left_x);
		double k_y = (right[12] - left[12]) / (right_x - left_x);
		double k_z = (right[13] - left[13]) / (right_x - left_x);
		for (int i = (int) left_x + 1; i <= (int) right_x; i++) {
			if (i >= size) {
				break;
			}
			else if (i < 0) {
				continue;
			}
			double cur_z = left_z + ((double) i - left_x) * k;
			double[] pixel_vector = new double[3];
			pixel_vector[0] = left[5] + ((double) i - left_x) * kx;
			pixel_vector[1] = left[6] + ((double) i - left_x) * ky;
			pixel_vector[2] = left[7] + ((double) i - left_x) * kz;
			double[] pixel_position = new double[3];
			pixel_position[0] = left[11] + ((double) i - left_x) * k_x;
			pixel_position[1] = left[12] + ((double) i - left_x) * k_y;
			pixel_position[2] = left[13] + ((double) i - left_x) * k_z;
			// compute one pixel's color
			int[] pixel_color = illu.shading(pixel_vector, pixel_position, color);
			if (cur_z < z_buffer[i][y]) {
				z_buffer[i][y] = cur_z;
				color_buffer[i][y] = pixel_color;
			}
		}
	}

	// update z-buffer and color-buffer using one odd and one even points (version for textureMapping)
	public void update_buffer(double[] left, double[] right, int y, Illumination illu, Texture t) {
		double left_x = left[1];
		double left_z = left[3];
		double right_x = right[1];
		double right_z = right[3];
		// slope of z related to change of x
		double k = (right_z - left_z) / (right_x - left_x);
		// slope of vector related to change of x
		double kx = (right[5] - left[5]) / (right_x - left_x);
		double ky = (right[6] - left[6]) / (right_x - left_x);
		double kz = (right[7] - left[7]) / (right_x - left_x);
		// slope of  coordinate
		double k_x = (right[11] - left[11]) / (right_x - left_x);
		double k_y = (right[12] - left[12]) / (right_x - left_x);
		double k_z = (right[13] - left[13]) / (right_x - left_x);
		for (int i = (int) left_x + 1; i <= (int) right_x; i++) {
			if (i >= size) {
				break;
			}
			else if (i < 0) {
				continue;
			}
			double cur_z = left_z + ((double) i - left_x) * k;
			double[] pixel_vector = new double[3];
			pixel_vector[0] = left[5] + ((double) i - left_x) * kx;
			pixel_vector[1] = left[6] + ((double) i - left_x) * ky;
			pixel_vector[2] = left[7] + ((double) i - left_x) * kz;
			double[] pixel_position = new double[3];
			pixel_position[0] = left[11] + ((double) i - left_x) * k_x;
			pixel_position[1] = left[12] + ((double) i - left_x) * k_y;
			pixel_position[2] = left[13] + ((double) i - left_x) * k_z;
			int[] color = t.textureMapping(pixel_position[0], pixel_position[1]);
			// compute one pixel's color
			int[] pixel_color = illu.shading(pixel_vector, pixel_position, color);
			if (cur_z < z_buffer[i][y]) {
				z_buffer[i][y] = cur_z;
				color_buffer[i][y] = pixel_color;
			}
		}
	}
}
