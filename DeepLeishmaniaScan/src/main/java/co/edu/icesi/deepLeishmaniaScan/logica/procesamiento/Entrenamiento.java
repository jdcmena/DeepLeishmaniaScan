package co.edu.icesi.deepLeishmaniaScan.logica.procesamiento;

import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.deepLeishmaniaScan.framework.IAPI;

public class Entrenamiento implements IEntrenamiento {

	private static final Logger log = LoggerFactory.getLogger(Entrenamiento.class);

	private IAPI api;

	public Entrenamiento(IAPI aPI) {
		this.api = aPI;
	}

	@Override
	public String[] obtenerMetricasGuardadas(String path) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void entrenar(String path, JTextArea consola) throws Exception {
		api.entrenar(path, consola);
	}

	@Override
	public double[] getMetricasEntrenamiento() {
		return api.getMetricasEntrenamiento();
	}

}
