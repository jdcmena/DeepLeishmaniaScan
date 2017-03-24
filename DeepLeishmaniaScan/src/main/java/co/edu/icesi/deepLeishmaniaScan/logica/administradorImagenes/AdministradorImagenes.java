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
		// setupKFoldCrossValidate();
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

		File[] imgClass1 = new File(class1Route).listFiles();
		File[] imgClass2 = new File(class2Route).listFiles();

		int qImgClass1 = (int) (imgClass1.length * ((K-1) / K));
		int qImgClass2 = (int) (imgClass2.length * ((K-1) / K));
		
		int qImgClass1Test = (int) (imgClass1.length * (1-((K-1) / K)));
		int qImgClass2Test = (int) (imgClass2.length * (1-((K-1) / K)));

		ArrayList<ArrayList<File>> testFiles = new ArrayList<>();
		//First test folds
		int pivot = 0;
		for (int i = 1; i <= K; i++) {
			ArrayList<File> temp = new ArrayList<>();
			
			for (int j = pivot; j < qImgClass1Test*i; j++) {
				temp.add(imgClass1[j]);
			}
			for (int j = pivot; j < qImgClass2Test*i; j++) {
				temp.add(imgClass2[j]);
			}
			pivot = qImgClass2Test*i;
			temp.trimToSize();
			
			testFiles.add(temp);
		}
		
		ArrayList<ArrayList<File>> training = new ArrayList<>();
		

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

		double multiFold = (K - 1) / K;

		// int q4EClass1 = (int) (qImgClass1 * multiFold);
		// int q4EClass2 = (int) (qImgClass2 * multiFold);
		//
		// int q4EClassTest1 = (int) (qImgClass1 * (1 - multiFold));
		// int q4EClassTest2 = (int) (qImgClass2 * (1 - multiFold));

	}

}
