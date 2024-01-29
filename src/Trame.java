import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;

public class Trame {
	public int width;
	public int height;
	public int matrix[];
	public int patterns[][];

	public Trame (int w, int h, int m[]) {
		width = w;
		height = h;
		matrix = m;
		patterns = genPatterns();
	}

	public Trame(int w, int h) {
		width = w;
		height = h;

	}

	int [][]genPatterns() {
		int ret[][] = new int[width * height][width * height];

		int w = 0xFFFFFF;
		int b = 0;
		for(int i = 0; i < width * height; i++)
			for(int j = 0; j < width * height; j++) {
				ret[i][j] = matrix[j] <= i ? w : b;
			}

		return ret;
	}

	BufferedImage traming(BufferedImage src) {
		ColorModel cm = src.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = src.copyData(null);
		BufferedImage image =  new BufferedImage(cm, raster, isAlphaPremultiplied, null);

		int t_x = image.getWidth() / width;
		int t_y = image.getHeight() / height;

		for(int i = 0; i < t_x ; i++)
			for(int j = 0; j < t_y; j++) {
				int tot = 0;
				for(int x = 0; x < width; x++)
					for(int y = 0; y < height; y++) {
						tot += image.getRGB(width * i + x, height * j + y) & 0xFF;
					}
				image.setRGB(width * i, height * j, width, height, patterns[tot / (height * width * width * height )], 0, width);
			}


		return image;
	}

	public static void main(String[] args) {
		int m[]=
			{
				15, 4, 8, 12,
				11, 0, 1, 5,
				7, 2, 3, 9,
				14, 10, 6, 13
			};

		Trame t = new Trame(4, 4, m);

		String path = "images/image5.png";
		File input_file = new File(path);
		try {
			BufferedImage image = ImageIO.read(input_file);
			BufferedImage n = t.traming(image);

			File reduced = new File("output/gen-trame.png");
			ImageIO.write(n, "png", reduced);
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
}