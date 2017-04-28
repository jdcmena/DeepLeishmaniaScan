package co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos;

public class Modelo {

	private int id;
	private String nombre;
	private String runConfig;
	private String direccionDirectorio;
	/**
	 * 0: accuracy
	 * 1: sensibilidad
	 * 2: especificidad
	 */
	private String[] metricas;

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
		metricas = new String[3];
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
		return metricas;
	}

	public void setRunConfigRoute(String runConfig) {
		this.runConfig = runConfig;
	}
	
	public void setAccuracy(double accuracy){
		this.metricas[0] = accuracy+"%";
	}
	
	public void setSensibility(double sens){
		this.metricas[1] = sens+"%";
	}
	
	public void setSpecificity(double specificity){
		this.metricas[2] = specificity+"%";
	}

	@Override
	public String toString() {
		return "Modelo [id=" + id + ", nombre=" + nombre + "]";
	}

}