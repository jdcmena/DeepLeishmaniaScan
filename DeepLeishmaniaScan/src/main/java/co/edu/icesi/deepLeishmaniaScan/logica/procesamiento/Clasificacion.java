package co.edu.icesi.deepLeishmaniaScan.logica.procesamiento;

import co.edu.icesi.deepLeishmaniaScan.framework.IAPI;

public class Clasificacion implements IClasificacion{
	
	private IAPI api;

	public Clasificacion(IAPI aPI) {
		this.api = aPI;
	}

	@Override
	public double clasificar(String path) throws Exception{
		return api.clasificar(path);
	}

}
