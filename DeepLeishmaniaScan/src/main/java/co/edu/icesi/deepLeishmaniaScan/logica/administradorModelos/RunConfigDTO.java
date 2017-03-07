package co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos;

/**
 * Clase para cargar y guardar informacion de cada modelo en formato JSON
 * @author JuanDavid
 *
 */
public class RunConfigDTO {
	/**
	 * Numero de generaciones
	 */
	private int generaciones;
	/**
	 * numero de imagenes por generacion
	 */
	private int imagenesPorGeneracion;
	/**
	 * tasa de aprendizaje
	 */
	private double tasaAprendizaje;
	/**
	 * momentum
	 */
	private double momentum;
	/**
	 * tecnica de optimizacion nesterov
	 */
	private boolean nesterov;
	/**
	 * nombre del modelo
	 */
	private int id;
	
	public RunConfigDTO(){
		
	}

	public int getGeneraciones() {
		return generaciones;
	}

	public void setGeneraciones(int generaciones) {
		this.generaciones = generaciones;
	}

	public int getImagenesPorGeneracion() {
		return imagenesPorGeneracion;
	}

	public void setImagenesPorGeneracion(int imagenesPorGeneracion) {
		this.imagenesPorGeneracion = imagenesPorGeneracion;
	}

	public double getTasaAprendizaje() {
		return tasaAprendizaje;
	}

	public void setTasaAprendizaje(double tasaAprendizaje) {
		this.tasaAprendizaje = tasaAprendizaje;
	}

	public boolean isNesterov() {
		return nesterov;
	}

	public void setNesterov(boolean nesterov) {
		this.nesterov = nesterov;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getMomentum() {
		return momentum;
	}

	public void setMomentum(double momentum) {
		this.momentum = momentum;
	}
	
	

}
