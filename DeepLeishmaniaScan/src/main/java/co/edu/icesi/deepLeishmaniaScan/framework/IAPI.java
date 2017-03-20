package co.edu.icesi.deepLeishmaniaScan.framework;

import javax.swing.JTextArea;

public interface IAPI {
	
	public double[] entrenar(String modelo, JTextArea consola) throws Exception;
	
	public double clasificar(String modelo, JTextArea consola) throws Exception;

}
