package co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos;

public class Modelo {

	private int id;
	private String nombre;
	private String pesos;
	private String arquitectura;
	private String runConfig;
	private String metricas;

	/**
	 * 
	 * @param nombre
	 * @param pesos
	 * @param arquitectura
	 */
	public Modelo(String nombre, String pesos, String arquitectura) {
		// TODO - implement Modelo.Modelo
		throw new UnsupportedOperationException();
	}

	public String getRutaArchivoPesos() {
		// TODO - implement Modelo.getRutaArchivoPesos
		throw new UnsupportedOperationException();
	}

	public String getRutaArchivoArquitectura() {
		// TODO - implement Modelo.getRutaArchivoArquitectura
		throw new UnsupportedOperationException();
	}

	public String getNombre() {
		// TODO - implement Modelo.getNombre
		throw new UnsupportedOperationException();
	}

	public String getID() {
		// TODO - implement Modelo.getID
		throw new UnsupportedOperationException();
	}

	public String getRutaRunConfig() {
		// TODO - implement Modelo.getRutaRunConfig
		throw new UnsupportedOperationException();
	}

	public String[] getMetricas() {
		// TODO - implement Modelo.getMetricas
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return "Modelo [id=" + id + ", nombre=" + nombre + "]";
	}
	

}