package graphic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

public class Texture {
    // y, x, [r, g, b]
    public int[][][] pixels;
    private int height;
    private int width;
    private double modelMinx;
    private double modelMaxx;
    private double modelMiny;
    private double modelMaxy;

    public void readTexture(String path) {
        File file = new File(path);
        FileChannel fc = null;
        if(file.exists() && file.isFile()){
            try {
                @SuppressWarnings("resource")
				FileInputStream fs = new FileInputStream(file);
                fc = fs.getChannel();
                System.out.println(fc.size() + "-----fc.size()");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(file.length() + "-----file.length  B");
        System.out.println(file.length() * 1024 + "-----file.length  kb");
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();

        System.out.println("Width：" + width + "-----Height："+height);

        this.width = width;
        this.height = height;

        pixels = new int[height][width][3];
        int rgb = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rgb = bi.getRGB(x, y);
                pixels[y][x][0] = (rgb>>16)&255;
                pixels[y][x][1] = (rgb>>8)&255;
                pixels[y][x][2] = (rgb)&255;
            }
        }
    }
    
    public void modelScale(double minx, double maxx, double miny, double maxy) {
    	modelMinx = minx;
    	modelMaxx = maxx;
    	modelMiny = miny;
    	modelMaxy = maxy;
    }

    public int[] textureMapping(double x, double y) {
        int[] rgb = new int[3];
        int mapX = (int) (width * ((x - modelMinx) / (modelMaxx - modelMinx)));
        int mapY = (int) (height * ((x - modelMiny) / (modelMaxy - modelMiny)));
        if (mapY < 0) {
            mapY = 0;
        }
        else if (mapY >= height) {
            mapY = height - 1;
        }
        if (mapX < 0) {
            mapX = 0;
        }
        else if (mapX >= width) {
            mapX = width - 1;
        }
        rgb[0] = pixels[mapY][mapX][0];
        rgb[1] = pixels[mapY][mapX][1];
        rgb[2] = pixels[mapY][mapX][2];
        return rgb;
    }
}
