package co.edu.icesi.deepLeishmaniaScan.logica.administradorImagenes;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdministradorImagenes implements IAdministradorImagenes {

	private static final Logger log = LoggerFactory.getLogger(AdministradorImagenes.class);

	private static final char OS = File.separatorChar;
	
	public static final String RUTA_BASE = "." + OS + "src" + OS + "main" + OS + "resources" + OS;

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

	public AdministradorImagenes() throws Exception {
		initFolers();
	}

	@Override
	public String getRutaPositivos() {
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
	public void cargarNuevasImagenes(String path) throws Exception { //TODO get all images from directory
		String parent = new File(path).getAbsolutePath();
		
		//FileUtils.iterateFiles(parent);
		for(String img: new File(parent).list()){
			File origin = new File(parent+img);
			log.info(origin.exists()?"file exists":"error");
			File destn = new File(RUTA_CONJUNTO_DE_DATOS + OS + img.hashCode());
			FileUtils.copyFile(origin, destn);
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

}
