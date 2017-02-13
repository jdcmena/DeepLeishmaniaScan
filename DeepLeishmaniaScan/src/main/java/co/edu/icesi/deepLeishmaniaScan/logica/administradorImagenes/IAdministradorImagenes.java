package co.edu.icesi.deepLeishmaniaScan.logica.administradorImagenes;

public interface IAdministradorImagenes {

	/**
	 * obtiene la ruta de la carpeta de las imágenes con diagnóstico positivo
	 * @return dirección canónica del directorio de imagenes con diagnóstico positivo
	 */
	public String getRutaPositivos();
	/**
	 * obtiene la ruta de la carpeta de las imágenes con diagnóstico negativo
	 * @return dirección canónica del directorio de imagenes con diagnóstico negativo
	 */
	public String getRutaNegativos();
	/**
	 * obtiene la ruta de la carpeta que contiene todas las imagenes de entrenamiento
	 * @return dirección canónica del directorio del conjunto de datos
	 */
	public String getRutaConjuntoDeDatos();
	
	/**
	 * Carga imagenes de una carpeta
	 * @param path la dirección canónica del directorio que contiene las imagenes
	 */
	public void cargarNuevasImagenes(String path);
	
}
