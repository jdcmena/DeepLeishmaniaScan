package co.edu.icesi.deepLeishmaniaScan.logica.administradorImagenes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;

/**
 * Converts every dataset image's colour channel to XYZ standard
 * 
 * @author JuanDavid
 *
 */
public class ColorSpaceTransformer {

	/**
	 * LAB ColourSpace
	 */

	private static final double M[][] = { 
			{ 0.4124564, 0.3575761, 0.1804375 }, 
			{ 0.2126729, 0.7151522, 0.0721750 },
			{ 0.0193339, 0.1191920, 0.9503041 }, };

	private static final double D65[] = { 95.0429, 100.0, 108.8900 };

	private static final int[] LAB_LOWER_BOUNDS = { 0, -127, -127 };

	private static final int[] LAB_UPPER_BOUNDS = { 100, 128, 128 };

	private static ColourSpace LAB = ColourSpace.CIE_Lab_Norm;

	public static double[] RGBtoLAB(double RGB[]) {
		return XYZtoLab(RGBtoXYZ(RGB));
	}

	public static int[] RGBtoLABNormalized(double RGB[]) {
		double[] lab = RGBtoLAB(RGB);
		int[] normal = new int[3];
		/*
///LAB
		for (int i = 0; i < 3; i++) {
			normal[i] = remap(lab[i], lab[i] < LAB_LOWER_BOUNDS[i] ? lab[i] : LAB_LOWER_BOUNDS[i],
					lab[i] > LAB_UPPER_BOUNDS[i] ? lab[i] : LAB_UPPER_BOUNDS[i], 0, 255);
		}
		
		///L
		for (int i = 0; i < 3; i++) {
			normal[i] = remap(lab[0], lab[0] < LAB_LOWER_BOUNDS[0] ? lab[0] : LAB_LOWER_BOUNDS[0],
					lab[0] > LAB_UPPER_BOUNDS[i] ? lab[0] : LAB_UPPER_BOUNDS[i], 0, 255);
		}
		
		///A
		for (int i = 0; i < 3; i++) {
			normal[i] = remap(lab[1], lab[1] < LAB_LOWER_BOUNDS[1] ? lab[1] : LAB_LOWER_BOUNDS[1],
					lab[1] > LAB_UPPER_BOUNDS[1] ? lab[1] : LAB_UPPER_BOUNDS[1], 0, 255);
		}
		*/
		///B
		for (int i = 0; i < 3; i++) {
			normal[i] = remap(lab[2], lab[2] < LAB_LOWER_BOUNDS[2] ? lab[2] : LAB_LOWER_BOUNDS[2],
					lab[2] > LAB_UPPER_BOUNDS[2] ? lab[2] : LAB_UPPER_BOUNDS[2], 0, 255);
		}

		return normal;
	}

	private static int remap(double value, double lower1, double upper1, double lowerObj, double upperObj) {
		return (int) Math.round(lowerObj + (value - lower1) * (upperObj - lowerObj) / (upper1 - lower1));
	}

	public static double[] RGBtoXYZ(double RGB[]) {

		double[] XYZ = new double[3];

		for (int c = 0; c < RGB.length; c++) {

			for (int j = 0; j < M[0].length; j++) {
				double result = 0;
				double temp = RGB[j] / 255;
				if (temp > 0.04045) {
					result += Math.pow(((temp + 0.055) / 1.055), 2.4);
				} else {
					result += temp / 12.92;
				}
				XYZ[c] += result * M[c][j] * 100;
			}

		}
		return XYZ;
	}

	public static double[] XYZtoLab(double XYZ[]) {
		return XYZtoLab(XYZ[0], XYZ[1], XYZ[2]);
	}

	public static double[] XYZtoLab(double X, double Y, double Z) {

		double x = X / D65[0];
		double y = Y / D65[1];
		double z = Z / D65[2];

		double fx = 0;
		double fy = 0;
		double fz = 0;

		if (x > 0.008856) {
			fx = Math.cbrt(x);
			// fx = Math.pow(x, 3.0);
		} else {
			fx = ((903.3 * x) + 16.0) / 116.0;
		}

		if (y > 0.008856) {
			fy = Math.cbrt(y);
			// fy = Math.pow(y, 3.0);
		} else {
			fy = ((903.3 * y) + 16.0) / 116.0;
		}
		if (z > 0.008856) {
			fz = Math.cbrt(z);
			// fz = Math.pow(z, 3.0);
		} else {
			fz = ((903.3 * z) + 16.0) / 116.0;
		}

		double L = (116.0 * fy) - 16.0;
		double a = 500.0 * (fx - fy);
		double b = 200.0 * (fy - fz);

		double[] resultado = new double[3];

		resultado[0] = L;
		resultado[1] = a;
		resultado[2] = b;

		return resultado;
	}

	public static void imageToLAB(File file) throws Exception {

		BufferedImage img = ImageIO.read(file);

		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {

				Color color = new Color(img.getRGB(i, j));
				double[] rgb = { color.getRed(), color.getBlue(), color.getGreen() };
				int[] result = RGBtoLABNormalized(rgb);
				img.setRGB(i, j, new Color(result[0], result[1], result[2]).getRGB());
			}
		}

		// MBFImage converted =
		// ColourSpace.convert(ImageUtilities.readMBF(file), LAB);
		// ImageUtilities.write(converted, file);
		ImageIO.write(img, "jpg", file);
	}
	// https://github.com/lessthanoptimal/BoofCV/blob/v0.23/examples/src/boofcv/examples/features/ExampleFitEllipse.java
}
