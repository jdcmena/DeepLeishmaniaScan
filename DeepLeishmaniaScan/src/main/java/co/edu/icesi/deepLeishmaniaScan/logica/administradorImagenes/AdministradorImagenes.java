package co.edu.icesi.deepLeishmaniaScan.logica.administradorImagenes;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdministradorImagenes implements IAdministradorImagenes {

	private static final Logger log = LoggerFactory.getLogger(AdministradorImagenes.class);

	private static final char OS = File.separatorChar;

	/**
	 * ruta del directorio del conjunto de datos
	 */
	public static final String RUTA_CONJUNTO_DE_DATOS = "." + OS + "src" + OS + "main" + OS + "resources" + OS
			+ "conjuntoDeDatos";
	/**
	 * ruta del directorio de imagenes con diagn�stico positivo
	 */
	public static final String RUTA_POSITIVOS = "." + OS + "src" + OS + "main" + OS + "resources" + OS + "positivos";
	/**
	 * ruta del directorio de imagenes con diagn�stico negativo
	 */
	public static final String RUTA_NEGATIVOS = "." + OS + "src" + OS + "main" + OS + "resources" + OS + "negativos";

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
	public void cargarNuevasImagenes(String path) throws Exception {
		String[] files = new File(path).list();
		for (String route : files) {
			File origin = new File(route);
			File destn = new File(RUTA_CONJUNTO_DE_DATOS + OS + route.hashCode());
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
