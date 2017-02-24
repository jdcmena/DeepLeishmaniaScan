package co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos;

public class Modelo {

	private int id;
	private String nombre;
	private String pesos;
	private String arquitectura;
	private String runConfig;
	private String direccionDirectorio;
	private String metricas;

	/**
	 * Constructor con ruta de archivo de configuracion
	 * 
	 * @param nombre
	 * @param runConfig
	 *            ruta del archivo json de configuracion
	 */
	public Modelo(String nombre, String runConfig, String direccionDirectorio) {
		id = nombre.getBytes().hashCode() + runConfig.hashCode();
		this.direccionDirectorio = direccionDirectorio;
		this.nombre = nombre;
		this.runConfig = runConfig;
	}

	/**
	 * Constructor sin ruta de archivo de configuracion
	 * 
	 * @param nombre
	 */
	public Modelo(String nombre) {
		id = nombre.getBytes().hashCode();
		this.nombre = nombre;
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
		return nombre;
	}

	public int getID() {
		return id;
	}

	public String getRunConfigPath() {
		return runConfig;
	}

	public String getRutaDirectorioModelo() {
		return direccionDirectorio;
	}

	public void setRutaDirectorioModelo(String direccionDirectorio) {
		this.direccionDirectorio = direccionDirectorio;
	}

	public String[] getMetricas() {
		// TODO - implement Modelo.getMetricas
		throw new UnsupportedOperationException();
	}

	public void setRunConfigRoute(String runConfig) {
		this.runConfig = runConfig;
	}

	@Override
	public String toString() {
		return "Modelo [id=" + id + ", nombre=" + nombre + "]";
	}

}