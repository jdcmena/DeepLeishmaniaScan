package co.edu.icesi.deepLeishmaniaScan.logica.administradorImagenes;

public interface IAdministradorImagenes {

	/**
	 * obtiene la ruta de la carpeta de las im�genes con diagn�stico positivo
	 * @return direcci�n can�nica del directorio de imagenes con diagn�stico positivo
	 */
	public String getRutaPositivos();
	/**
	 * obtiene la ruta de la carpeta de las im�genes con diagn�stico negativo
	 * @return direcci�n can�nica del directorio de imagenes con diagn�stico negativo
	 */
	public String getRutaNegativos();
	/**
	 * obtiene la ruta de la carpeta que contiene todas las imagenes de entrenamiento
	 * @return direcci�n can�nica del directorio del conjunto de datos
	 */
	public String getRutaConjuntoDeDatos();
	
	/**
	 * Carga imagenes de una carpeta
	 * @param path la direcci�n can�nica del directorio que contiene las imagenes
	 */
	public void cargarNuevasImagenes(String path) throws Exception;
	
	/**
	 * Agrega una imagen clasificada al directorio de imagenes con diagnostico
	 *  positivos o en el directorio de imagenes con diagnostico negativo dependiendo del caso.
	 * @param imgPath
	 * @param positivo
	 */
	public void nuevaClasificacion(String imgPath, boolean positivo) throws Exception;
	
	public double getSensibility() throws Exception;
	
	public double getSpecificity() throws Exception;
}
