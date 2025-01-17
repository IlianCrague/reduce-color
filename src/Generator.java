import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;
import java.io.File;


public class Generator {

	public static void main (String[] args) {
		int sizeX = 40;
		int sizeY = 40;
		if(args.length == 2) {
			sizeX = Integer.valueOf(args[0]);
			sizeY = Integer.valueOf(args[1]);
		}
		BufferedImage image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_BYTE_GRAY);
		Random r = new Random();
		for(int i = 0; i < sizeX; i++)
		for(int j = 0; j < sizeY; j++) {
			int color = r.nextInt(256);
			int rgb = (color << 16) + (color << 8) + color;
			image.setRGB(i, j, rgb);
		}

		File outputfile = new File("image.png");
		try {
			ImageIO.write(image, "png", outputfile);
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
}
