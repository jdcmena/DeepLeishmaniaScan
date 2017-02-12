package co.edu.icesi.deepLeishmaniaScan.logica.procesamiento;

public interface IClasificacion {
	
	/**
	 * clasificar una imagen
	 * @param path ruta del modelo a usar
	 * @return porcentaje de certeza de diagnůstico positivo
	 */
	public double clasificar(String path);

}
