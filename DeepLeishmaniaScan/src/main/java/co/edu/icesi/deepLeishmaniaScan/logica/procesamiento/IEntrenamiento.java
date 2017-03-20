package co.edu.icesi.deepLeishmaniaScan.logica.procesamiento;

import javax.swing.JTextArea;

public interface IEntrenamiento {
	
	/**
	 * obtener las metricas de un modelo
	 * @param path url del modelo
	 * @return string[3]
	 * 0: precisi�n
	 * 1: sensibilidad
	 * 2: especificidad
	 * @throws Exception 
	 */
	public String[] obtenerMetricas(String path) throws Exception;
	/**
	 * ejecuta el script de entrenamiento
	 * @param path ruta del modelo a entrenar
	 * @return TODO
	 * @throws Exception 
	 */
	public double[] entrenar(String path, JTextArea consola) throws Exception;
	
}
