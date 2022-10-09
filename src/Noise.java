import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Noise{

	private static int resx = 1920;
	private static int resy = 1080;
	private static double offsetQX = Math.random() * 2.0 - 1.0;
	private static double offsetQY = Math.random() * 2.0 - 1.0;
	private static double offsetRX = Math.random() * 2.0 - 1.0;
	private static double offsetRY = Math.random() * 2.0 - 1.0;


	public static void main(String[] args){
		double seed = Math.random();
		BufferedImage bi = new BufferedImage(resx, resy, BufferedImage.TYPE_INT_RGB);
		for(int x = 0; x < resx; x ++){
			for(int y = 0; y < resy; y ++){
				double noiseVal = domainDistortion((double) x / (double) resx, (double) y / (double) resy);
				bi.setRGB(x, y, new Color((float) noiseVal, (float) noiseVal, (float) noiseVal).getRGB());
			}
		}
		File f = new File("../output.png");
		try{
			ImageIO.write(bi, "png", f);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public static double fbm(double x, double y){
		int oct = 6;
		double amp = 0.5;
		double freq = 3.0;
		double t = 0.0;

		for(int i = 0; i < oct; i ++){
			double noise = SimplexNoise.noise(freq * x, freq * y);
			noise += 1.0;
			noise /= 2.0;
			t += amp * noise;
			amp *= 0.5;
			freq *= 2.0;
		} 
		return Math.min(1, Math.max(t, 0));
	}

	public static double domainDistortion(double x, double y){
		double x1 = 0.0;
		double y1 = 0.0;
		double x2 = 5.2;
		double y2 = 1.3;
		double x3 = 1.7;
		double y3 = 9.2;
		double x4 = 8.3;
		double y4 = 2.8;

		double amp = -0.7;

		double qx = fbm(x + x1, y + y1);
		double qy = fbm(x + x2, y + y2);
		double rx = fbm(x + amp * qx + x3, y + amp * qy + y3);
		double ry = fbm(x + amp * qx + x4, y + amp * qy + y4);
		return fbm(x + amp * rx, y + amp * ry);
	}
}