package graphic;

public class Test {
    public static void main(String[] args) {
        Core c = new Core();
//        c.camera.initial(new double[] {10, 20, -20}, new double[] {0, 0, 0}, new double[] {0, 1, 0},
//                1, 20, 100, 0);
//        c.g.reset("bench.d.txt");
//        c.illu.initial(new double[] {1, 1, 1}, 1, 0.3, 0.5, 0.7, 1, 8);
//        c.constant_draw();

        c.camera.initial(new double[] {5, -5, 0}, new double[] {0, 0, 2}, new double[] {0, 0, 1},
                1, 2, 20, 1);
        c.g.reset("knight.d.txt");
        c.illu.initial(new double[] {1, 1, 1}, 1, 0.3, 0.5, 0.7, 1, 8);
        c.constant_draw();
        c.gouraud_draw();
        c.phong_draw();

        c.camera.initial(new double[] {5, 0, 0}, new double[] {0, 0, 0}, new double[] {0, 0, 1},
                0.75, 2, 20, 0);
        c.g.reset("better-ball.d.txt");
        c.illu.initial(new double[] {-1, 1, -1}, 1, 0.3, 0.5, 0.7, 1, 8);
        c.constant_draw();
        c.gouraud_draw();
        c.phong_draw();
    }
}
