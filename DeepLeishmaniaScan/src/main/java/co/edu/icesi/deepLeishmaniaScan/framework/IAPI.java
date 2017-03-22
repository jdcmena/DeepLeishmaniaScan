package co.edu.icesi.deepLeishmaniaScan.framework;

import javax.swing.JTextArea;

import co.edu.icesi.deepLeishmaniaScan.logica.procesamiento.IClasificacion;
import co.edu.icesi.deepLeishmaniaScan.logica.procesamiento.IEntrenamiento;

public interface IAPI {
	
	public void entrenar(String modelo, JTextArea consola) throws Exception;
	
	public void clasificar(String modelo, JTextArea consola) throws Exception;
	
	public void setClassificationModule(IClasificacion clasificacion);
	
	public void setTrainingModule(IEntrenamiento entrenmaiento);

}
