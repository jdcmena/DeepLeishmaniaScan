package co.edu.icesi.deepLeishmaniaScan.logica.administradorImagenes;

import java.io.File;
import java.util.List;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;

/**
 * Converts every dataset image's colour channel to XYZ standard
 * @author JuanDavid
 *
 */
public class ColorSpaceTransformer {
	
	/**
	 * XYZ ColourSpace
	 */
	private static ColourSpace XYZ = ColourSpace.CIE_XYZ;
	
	private static ColourSpace LAB = ColourSpace.CIE_Lab;
	
	/**
	 * Realiza la conversion de las imagenes que estan en la lista
	 * @param newImages List<Files> lista de imagenes
	 * @throws Exception
	 */
	public static void datasetToXYZ(List<File> newImages) throws Exception{
		
		//File[] directories = new File(AdministradorImagenes.RUTA_CONJUNTO_DE_DATOS).listFiles(File::isDirectory);
		
		for (File file : newImages) {
			
			//File[] imageList = file.listFiles();
			
			//for (File img : imageList) {
				MBFImage converted = ColourSpace.convert(ImageUtilities.readMBF(file), LAB);
				ImageUtilities.write(converted, new File(file.getAbsolutePath()));
			//}
		}
		newImages.clear();
	}
	
	public static void imageToXYZ(File file) throws Exception{
		MBFImage converted = ColourSpace.convert(ImageUtilities.readMBF(file), XYZ);
		ImageUtilities.write(converted, file);
	}
	public static void imageToLAB(File file) throws Exception{
		MBFImage converted = ColourSpace.convert(ImageUtilities.readMBF(file), LAB);
		ImageUtilities.write(converted, file);
	}
//https://github.com/lessthanoptimal/BoofCV/blob/v0.23/examples/src/boofcv/examples/features/ExampleFitEllipse.java
}
