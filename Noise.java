import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Noise{

	private static int resx = 1920;
	private static int resy = 1080;

	public static void main(String[] args){
		double seed = Math.random();
		BufferedImage bi = new BufferedImage(resx, resy, BufferedImage.TYPE_INT_RGB);
		for(int x = 0; x < resx; x ++){
			for(int y = 0; y < resy; y ++){
				double noiseVal = fbm((double) x / (double) resx * seed, (double) y / (double) resy * seed);
				bi.setRGB(x, y, new Color((float) noiseVal, (float) noiseVal, (float) noiseVal).getRGB());
			}
		}
		File f = new File("output.png");
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
}