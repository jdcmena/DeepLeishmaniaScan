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
		//setupKFoldCrossValidate();
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
			File destn = new File(RUTA_CONJUNTO_DE_DATOS + OS + origin.getName().hashCode());
			FileUtils.copyFile(origin, destn);
			newImages.add(destn);
		}
	}

	@Override
	public void nuevaClasificacion(String imgPath, boolean positivo) throws Exception {
		File newClassification = new File(imgPath);
		if (positivo) {
			FileUtils.copyFile(newClassification, new File(RUTA_POSITIVOS + OS + imgPath.hashCode()));
		} else {
			FileUtils.copyFile(newClassification, new File(RUTA_NEGATIVOS + OS + imgPath.hashCode()));
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

		/*
		 * ArrayList<String> classNames = new ArrayList<>(); for(File file :
		 * datasetClasses){ if(file.isDirectory()){
		 * 
		 * } }
		 * 
		 * String[] classNames =
		 */
		String class1Name = datasetClasses[0].getName();
		String class2Name = datasetClasses[1].getName();

		String class1Route = datasetClasses[0].getAbsolutePath();
		String class2Route = datasetClasses[1].getAbsolutePath();

		//tempList.addAll(Arrays.asList(temp));
		
		File[] imgClass1 = new File(class1Route).listFiles();
		File[] imgClass2 = new File(class2Route).listFiles();

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

		int total = imgClass1.length + imgClass2.length;
		if ((imgClass1.length * 2 != total || imgClass1.length < total * 0.40) || (imgClass2.length * 2 != total || imgClass2.length < total * 0.40)){
			throw new Exception("Asegurese que la cantidad de imagenes por cada clase sea igual (Ej: # de imagenes de clase1: 1000, # de imagenes de clase2: 1000");
		}

			System.out.println("total=" + total);

		for (int i = 1; i < K; i++) {
			double sub = K - i;
			double multiFold = sub / K;
			System.out.println("multifold=" + multiFold);
			int q4EClass = (int) ((total * multiFold) / 2);
			System.out.println("quantity 4 each class =" + q4EClass);
			int q4EClassTest = (int) ((total * (1 - multiFold)) / 2);
			System.out.println("quantity for each test class =" + q4EClassTest);
			int pivot = 0;

			for (int j = 0; j < q4EClass; j++) {
				System.out.println("class loop counter =" + j);
				/*
				 * FileUtils.copyFile(imgClass1[j], new File(RUTA_BASE + "fold"
				 * + i + OS + class1Name)); FileUtils.copyFile(imgClass2[j], new
				 * File(RUTA_BASE + "fold" + i + OS + class2Name)); pivot++;
				 */
			}

			for (int t = pivot; t < q4EClassTest; t++) {
				System.out.println("test class loop counter =" + t);
				/*
				 * FileUtils.copyFile(imgClass1[t], new File(RUTA_BASE +
				 * "fold-test" + i + OS + class1Name));
				 * FileUtils.copyFile(imgClass2[t], new File(RUTA_BASE +
				 * "fold-test" + i + OS + class2Name));
				 */
			}

		}

	}

}
