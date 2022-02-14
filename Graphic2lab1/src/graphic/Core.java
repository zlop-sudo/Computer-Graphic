package graphic;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Core {
	public Camera camera = new Camera();
	public Geometry g = new Geometry();
	
	public Core() {
	}
	
	public void draw() {
		camera.compute_matrix();
		
		g.polygon_back(camera.remove_back, camera.n);
		g.project(camera.m_trans);
		
		double[][] draw_points = g.screen_points;
		Polygon[] polygons = g.polygons;
		System.out.printf("points_number: " + draw_points.length + " ");
		System.out.printf("polygon_number: " + polygons.length + "\n");
			
		double screen_size = 500;
		
		JFrame frame = new JFrame("Camera View");
		frame.setSize((int) screen_size, (int) screen_size);
		
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				
				for (Polygon polygon : polygons) {
					if (!polygon.back_face) {
						int[] xp = new int[polygon.n];
						int[] yp = new int[polygon.n];
						int[] points_index = polygon.points;
						for (int i = 0; i < polygon.n; i++) {
							xp[i] = (int) (screen_size -((draw_points[points_index[i]][0] + 1) * screen_size / 2));
							yp[i] = (int) (screen_size -((draw_points[points_index[i]][1] + 1) * screen_size / 2));
							System.out.printf("points" + i + ": x: " + xp[i] + " y: " + yp[i] + " ");
						}
						System.out.printf("\n" + "polygon_points_number: " + polygon.n + "\n");
						if (camera.remove_back) {
							g.setColor(Color.GREEN);
							g.fillPolygon(xp, yp, polygon.n);
						}
						g.setColor(Color.BLUE);
						g.drawPolygon(xp, yp, polygon.n);
					}
				}
			}
		};
		frame.setContentPane(panel);
		
		frame.setVisible(true);
	}
	
	// get the model's location range
	public String getModelLoc() {
		StringBuilder sb = new StringBuilder();
		sb.append("x: " + g.minx + " ~ " + g.maxx + "\n");
		sb.append("y: " + g.miny + " ~ " + g.maxy + "\n");
		sb.append("z: " + g.minz + " ~ " + g.maxz);
		return sb.toString();
	}
}
