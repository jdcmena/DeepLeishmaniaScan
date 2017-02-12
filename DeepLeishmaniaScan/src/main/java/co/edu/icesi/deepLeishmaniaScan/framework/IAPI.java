package co.edu.icesi.deepLeishmaniaScan.framework;

public interface IAPI {
	
	public double[] entrenar(String modelo);
	
	public double clasificar(String modelo);

	public void runCommand(String command) throws Exception;
}
