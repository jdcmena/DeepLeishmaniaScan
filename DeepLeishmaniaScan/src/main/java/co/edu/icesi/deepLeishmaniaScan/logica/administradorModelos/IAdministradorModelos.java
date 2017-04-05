package co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos;

import java.io.IOException;
import java.util.List;

public interface IAdministradorModelos {
	
	/**
	 * 
	 * @param nombre
	 */
	public Modelo cargarModelo(String nombre);
	
	/**
	 * 
	 * @param id
	 * @throws IOException 
	 */
	public Modelo getModeloPorId(String id) throws IOException;
	
	/**
	 * 
	 * @param model
	 * @param nEpoch
	 * @param nImgPerEpoch
	 * @param lR
	 * @param mR
	 * @param dLr
	 * @param nesterov
	 */
	public void setParametrosModelo(Modelo model, int nEpoch, int nImgPerEpoch, double lR, double mR, boolean nesterov);
	
	/**
	 * devuelve la lista de modelos cargados en memoria
	 * @return
	 */
	public List<Modelo> getListaModelos();
	
	public void guardarModelo(Modelo model, int gen, int imgXG, double tasaA,double momentum, boolean selected) throws Exception;
	/**
	 * 
	 * @param gen numero de generaciones
	 * @param imgXG numero de imagenes por generacion
	 * @param tasaA tasa de aprendizaje
	 * @param tasaD tasa de decadencia de la tasa de aprendizaje
	 * @param selected uso de la tecnica de optimizacion nesterov
	 * @param name nombre de modelo
	 * @throws Exception
	 */
	public void crearModelo(int gen, int imgXG, double tasaA, double momentum, boolean selected, String name) throws Exception;
	
	public void setMetrics(Modelo model, double accuracy, double sensibility, double specificity) throws Exception;

	public void setMetrics(Modelo model) throws Exception;
}
