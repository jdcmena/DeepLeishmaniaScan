package co.edu.icesi.deepLeishmaniaScan.logica.administradorImagenes;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdministradorImagenes implements IAdministradorImagenes {

	private static final Logger log = LoggerFactory.getLogger(AdministradorImagenes.class);

	private static final char OS = File.separatorChar;

	public static final String RUTA_BASE = "." + OS;

	public static final double K = 5;
	/**
	 * ruta del directorio del conjunto de datos
	 */
	public static final String RUTA_CONJUNTO_DE_DATOS = RUTA_BASE + "conjuntoDeDatos";
	/**
	 * ruta del directorio de imagenes con diagn�stico positivo
	 */
	public static final String RUTA_POSITIVOS = RUTA_BASE + "positivos";
	/**
	 * ruta del directorio de imagenes con diagn�stico negativo
	 */
	public static final String RUTA_NEGATIVOS = RUTA_BASE + "negativos";

	private List<File> newImages;

	public AdministradorImagenes() throws Exception {
		initFolers();
		newImages = new ArrayList<>();
		
		//then we resize images and copy to their respective folders (leishmania and non-leishmania)
		setupKFoldCrossValidate();
	}

	@Override
	public String getRutaPositivos() {
		org.apache.commons.imaging.color.ColorConversions.convertRGBtoXYZ(1);
		return RUTA_POSITIVOS;
	}

	@Override
	public String getRutaNegativos() {
		return RUTA_NEGATIVOS;
	}

	@Override
	public String getRutaConjuntoDeDatos() {
		return RUTA_CONJUNTO_DE_DATOS;
	}

	@Override
	public void cargarNuevasImagenes(String path) throws Exception {
		File parent = new File(path);
		Iterator<File> iter = FileUtils.iterateFiles(parent, null, true);
		while (iter.hasNext()) {
			File origin = new File(iter.next().getAbsolutePath());
			File destn = new File(RUTA_CONJUNTO_DE_DATOS);
			FileUtils.copyFile(origin, destn);
			newImages.add(destn);
		}
	}

	@Override
	public void nuevaClasificacion(String imgPath, boolean positivo) throws Exception {
		File newClassification = new File(imgPath);
		if (positivo) {
			FileUtils.copyFile(newClassification, new File(RUTA_POSITIVOS));
		} else {
			FileUtils.copyFile(newClassification, new File(RUTA_NEGATIVOS));
		}
	}

	/**
	 * Verificar la existencia de los directorios de las imagenes
	 * 
	 * @throws Exception
	 */
	private void initFolers() throws Exception {
		File dataset = new File(RUTA_CONJUNTO_DE_DATOS);
		File positive = new File(RUTA_POSITIVOS);
		File negative = new File(RUTA_NEGATIVOS);
		if (!dataset.exists()) {
			dataset.mkdirs();
			dataset.createNewFile();
		}
		if (!positive.exists()) {
			positive.mkdirs();
			positive.createNewFile();
		}
		if (!negative.exists()) {
			negative.mkdirs();
			negative.createNewFile();
		}
	}

	@Override
	public double getSensibility() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSpecificity() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	private void setupKFoldCrossValidate() throws Exception {

		File[] datasetClasses = new File(RUTA_CONJUNTO_DE_DATOS).listFiles();

		String class1Name = datasetClasses[0].getName();
		String class2Name = datasetClasses[1].getName();

		String class1Route = datasetClasses[0].getAbsolutePath();
		String class2Route = datasetClasses[1].getAbsolutePath();

		for (int i = 1; i <= K; i++) {
			File dir1 = new File(RUTA_BASE + "fold" + i + OS + class1Name);
			File dir2 = new File(RUTA_BASE + "fold" + i + OS + class2Name);
			File dirTest1 = new File(RUTA_BASE + "fold-test" + i + OS + class1Name);
			File dirTest2 = new File(RUTA_BASE + "fold-test" + i + OS + class2Name);
			if (!(dir1.exists() && dir2.exists() && dirTest1.exists() && dirTest2.exists())) {
				dir1.mkdirs();
				dir2.mkdirs();
				dirTest1.mkdirs();
				dirTest2.mkdirs();
				dir1.createNewFile();
				dir2.createNewFile();
				dirTest1.createNewFile();
				dirTest2.createNewFile();
			} else {
				break;
			}

		}

		File[] imgClass1 = new File(class1Route).listFiles();
		File[] imgClass2 = new File(class2Route).listFiles();

		int qImgClass1 = (int) (imgClass1.length * ((K - 1) / K));
		int qImgClass2 = (int) (imgClass2.length * ((K - 1) / K));

		int qImgClass1Test = (int) (imgClass1.length * (1 - ((K - 1) / K)));
		int qImgClass2Test = (int) (imgClass2.length * (1 - ((K - 1) / K)));

		File[] f1c1 = divideByInteger(1, 5, imgClass1);
		File[] f1c2 = divideByInteger(1, 5, imgClass2);

		File[] f2c1 = divideByInteger(2, 5, imgClass1);
		File[] f2c2 = divideByInteger(2, 5, imgClass2);

		File[] f3c1 = divideByInteger(3, 5, imgClass1);
		File[] f3c2 = divideByInteger(3, 5, imgClass2);

		File[] f4c1 = divideByInteger(4, 5, imgClass1);
		File[] f4c2 = divideByInteger(4, 5, imgClass2);

		File[] f5c1 = divideByInteger(5, 5, imgClass1);
		File[] f5c2 = divideByInteger(5, 5, imgClass2);

		ArrayList<File[]> fold1 = new ArrayList<>();
		fold1.add(f2c1);
		fold1.add(f3c1);
		fold1.add(f4c1);
		fold1.add(f5c1);
		fold1.add(f2c2);
		fold1.add(f3c2);
		fold1.add(f4c2);
		fold1.add(f5c2);

		ArrayList<File[]> fold2 = new ArrayList<>();
		fold2.add(f1c1);
		fold2.add(f3c1);
		fold2.add(f4c1);
		fold2.add(f5c1);
		fold2.add(f1c2);
		fold2.add(f3c2);
		fold2.add(f4c2);
		fold2.add(f5c2);

		ArrayList<File[]> fold3 = new ArrayList<>();
		fold3.add(f1c1);
		fold3.add(f2c1);
		fold3.add(f4c1);
		fold3.add(f5c1);
		fold3.add(f1c2);
		fold3.add(f2c2);
		fold3.add(f4c2);
		fold3.add(f5c2);

		ArrayList<File[]> fold4 = new ArrayList<>();
		fold4.add(f1c1);
		fold4.add(f2c1);
		fold4.add(f3c1);
		fold4.add(f5c1);
		fold4.add(f1c2);
		fold4.add(f2c2);
		fold4.add(f3c2);
		fold4.add(f5c2);

		ArrayList<File[]> fold5 = new ArrayList<>();
		fold5.add(f1c1);
		fold5.add(f2c1);
		fold5.add(f3c1);
		fold5.add(f4c1);
		fold5.add(f1c2);
		fold5.add(f2c2);
		fold5.add(f3c2);
		fold5.add(f4c2);

		fold1.trimToSize();
		fold2.trimToSize();
		fold3.trimToSize();
		fold4.trimToSize();
		fold5.trimToSize();

		int counter = 4;
		for (int i = 0; i < fold1.size(); i++) {
			File[] temp = fold1.get(i);
			if (counter != 0) {
				for (File file : temp) {
					FileUtils.copyFile(file, new File(RUTA_BASE + "fold" + 1 + OS + class1Name + OS + file.getName()));
					counter--;
				}
			} else {

				for (File file : temp) {
					FileUtils.copyFile(file, new File(RUTA_BASE + "fold" + 1 + OS + class2Name + OS + file.getName()));
				}
			}
		}

		for (File file : f1c1) {
			FileUtils.copyFile(file, new File(RUTA_BASE + "fold-test" + 1 + OS + class1Name + OS + file.getName()));
		}
		for (File file : f1c2) {
			FileUtils.copyFile(file, new File(RUTA_BASE + "fold-test" + 1 + OS + class2Name + OS + file.getName()));
		}

		counter = 4;

		for (int i = 0; i < fold2.size(); i++) {
			File[] temp = fold2.get(i);
			if (counter != 0) {
				for (File file : temp) {
					FileUtils.copyFile(file, new File(RUTA_BASE + "fold" + 2 + OS + class1Name + OS + file.getName()));
					counter--;
				}
			} else {

				for (File file : temp) {
					FileUtils.copyFile(file, new File(RUTA_BASE + "fold" + 2 + OS + class2Name + OS + file.getName()));
				}
			}
		}

		for (File file : f2c1) {
			FileUtils.copyFile(file, new File(RUTA_BASE + "fold-test" + 2 + OS + class1Name + OS + file.getName()));
		}
		for (File file : f2c2) {
			FileUtils.copyFile(file, new File(RUTA_BASE + "fold-test" + 2 + OS + class2Name + OS + file.getName()));
		}

		counter = 4;

		for (int i = 0; i < fold3.size(); i++) {
			File[] temp = fold3.get(i);
			if (counter != 0) {
				for (File file : temp) {
					FileUtils.copyFile(file, new File(RUTA_BASE + "fold" + 3 + OS + class1Name + OS + file.getName()));
					counter--;
				}
			} else {

				for (File file : temp) {
					FileUtils.copyFile(file, new File(RUTA_BASE + "fold" + 3 + OS + class2Name + OS + file.getName()));
				}
			}
		}

		for (File file : f3c1) {
			FileUtils.copyFile(file, new File(RUTA_BASE + "fold-test" + 3 + OS + class1Name + OS + file.getName()));
		}
		for (File file : f3c2) {
			FileUtils.copyFile(file, new File(RUTA_BASE + "fold-test" + 3 + OS + class2Name + OS + file.getName()));
		}

		counter = 4;

		for (int i = 0; i < fold4.size(); i++) {
			File[] temp = fold4.get(i);
			if (counter != 0) {
				for (File file : temp) {
					FileUtils.copyFile(file, new File(RUTA_BASE + "fold" + 4 + OS + class1Name + OS + file.getName()));
					counter--;
				}
			} else {

				for (File file : temp) {
					FileUtils.copyFile(file, new File(RUTA_BASE + "fold" + 4 + OS + class2Name + OS + file.getName()));
				}
			}
		}

		for (File file : f4c1) {
			FileUtils.copyFile(file, new File(RUTA_BASE + "fold-test" + 4 + OS + class1Name + OS + file.getName()));
		}
		for (File file : f4c2) {
			FileUtils.copyFile(file, new File(RUTA_BASE + "fold-test" + 4 + OS + class2Name + OS + file.getName()));
		}

		counter = 4;

		for (int i = 0; i < fold5.size(); i++) {
			File[] temp = fold5.get(i);
			if (counter != 0) {
				for (File file : temp) {
					FileUtils.copyFile(file, new File(RUTA_BASE + "fold" + 5 + OS + class1Name + OS + file.getName()));
					counter--;
				}
			} else {

				for (File file : temp) {
					FileUtils.copyFile(file, new File(RUTA_BASE + "fold" + 5 + OS + class2Name + OS + file.getName()));
				}
			}
		}

		for (File file : f5c1) {
			FileUtils.copyFile(file, new File(RUTA_BASE + "fold-test" + 5 + OS + class1Name + OS + file.getName()));
		}
		for (File file : f5c2) {
			FileUtils.copyFile(file, new File(RUTA_BASE + "fold-test" + 5 + OS + class2Name + OS + file.getName()));
		}

	}

	private File[] divideByInteger(int part, int div, File[] array) {
		int division = array.length / div;

		File[] ret = new File[division];
		int index = 0;
		for (int i = division * (part - 1); i < division * part; i++) {
			ret[index] = array[i];
			index++;
		}
		return ret;
	}
}
