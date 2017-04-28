package co.edu.icesi.deepLeishmaniaScan.logica.procesamiento;

import javax.swing.JTextArea;

public interface IClasificacion {
	
	/**
	 * clasificar una imagen
	 * @param path ruta del modelo a usar
	 * @return porcentaje de certeza de diagn�stico positivo
	 * @throws Exception 
	 */
	public void clasificar(String path, JTextArea consola) throws Exception;

}
