package co.edu.icesi.deepLeishmaniaScan.framework;

public interface IAPI {
	
	public double[] entrenar(String modelo) throws Exception;
	
	public double clasificar(String modelo) throws Exception;

}
