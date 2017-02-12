package co.edu.icesi.deepLeishmaniaScan.logica.procesamiento;

public interface IEntrenamiento {
	
	/**
	 * obtener las metricas de un modelo
	 * @param path url del modelo
	 * @return string[3]
	 * 0: precisión
	 * 1: sensibilidad
	 * 2: especificidad
	 */
	public String[] obtenerMetricas(String path);
	/**
	 * ejecuta el script de entrenamiento
	 * @param path ruta del modelo a entrenar
	 * @return TODO
	 */
	public double[] entrenar(String path);
	
}
