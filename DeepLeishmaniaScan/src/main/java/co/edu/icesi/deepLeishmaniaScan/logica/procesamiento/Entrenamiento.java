package co.edu.icesi.deepLeishmaniaScan.logica.procesamiento;

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
	public String[] obtenerMetricas(String path) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] entrenar(String path) throws Exception {
		// TODO Auto-generated method stub

		// scriptRunner("p");
		log.info("-----");
		return null;
	}

}
