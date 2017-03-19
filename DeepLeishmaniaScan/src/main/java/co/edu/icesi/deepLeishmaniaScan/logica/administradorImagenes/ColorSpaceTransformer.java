package co.edu.icesi.deepLeishmaniaScan.logica.administradorImagenes;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;

import org.apache.commons.imaging.ColorTools;

public class ColorSpaceTransformer {
	
	private static ColorTools converter = new ColorTools();
	private static ColorSpace YXY = ColorSpace.getInstance(ColorSpace.TYPE_Yxy);
	private static ColorSpace RGB = ColorSpace.getInstance(ColorSpace.TYPE_RGB);
	
	public static BufferedImage convertRGBtoYXY(BufferedImage image){
		return converter.convertToColorSpace(image,YXY);
	}

}
