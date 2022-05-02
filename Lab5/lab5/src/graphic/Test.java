package graphic;

public class Test {

	public static void main(String[] args) {
		Core c = new Core();
		
//		c.camera.initial(new double[] {5, 5, 5}, new double[] {0, 0, 0}, new double[] {0, 0, 1},
//		          0.75, 2, 20, 0);
//		c.g.reset("better-ball.d.txt");
//		c.g.addGround();
//		c.t.readTexture("Texture1.jpeg");
//		c.illu.initial(new double[] {-1, 1, -1}, 1, 0.3, 0.5, 0.6, 0.8, 8);
//		c.shadow();
		
		c.camera.initial(new double[] {5, 5, 5}, new double[] {0, 0, 0}, new double[] {0, 0, 1},
		          0.75, 2, 20, 0);
		c.g.reset("bench.d.txt");
		c.g.addGround();
		c.t.readTexture("Texture1.jpeg");
		c.illu.initial(new double[] {-1, 1, -1}, 1, 0.3, 0.5, 0.6, 0.8, 8);
		c.shadow();
	}
}
