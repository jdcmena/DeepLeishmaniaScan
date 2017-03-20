package co.edu.icesi.deepLeishmaniaScan.logica.procesamiento;

import javax.swing.JTextArea;

import co.edu.icesi.deepLeishmaniaScan.framework.IAPI;

public class Clasificacion implements IClasificacion{
	
	private IAPI api;

	public Clasificacion(IAPI aPI) {
		this.api = aPI;
	}

	@Override
	public double clasificar(String path, JTextArea consola) throws Exception{
		return api.clasificar(path, consola);
	}

}
