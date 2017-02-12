package co.edu.icesi.deepLeishmaniaScan.logica.administradorImagenes;

public interface IAdministradorImagenes {

	/**
	 * obtiene la ruta de la carpeta de las imágenes con diagnóstico positivo
	 * @return
	 */
	public String getRutaPositivos();
	/**
	 * obtiene la ruta de la carpeta de las imágenes con diagnóstico negativo
	 * @return
	 */
	public String getRutaNegativos();
	/**
	 * obtiene la ruta de la carpeta que contiene todas las imagenes de entrenamiento
	 * @return
	 */
	public String getRutaConjuntoDeDatos();
	
}
