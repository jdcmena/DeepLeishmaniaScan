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
		while(iter.hasNext()){
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

}
