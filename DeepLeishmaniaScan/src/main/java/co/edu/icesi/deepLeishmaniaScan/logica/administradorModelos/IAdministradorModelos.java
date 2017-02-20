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
	public void setParametrosModelo(Modelo model, int nEpoch, int nImgPerEpoch, double lR, double mR, double dLr, boolean nesterov);
	
	public List<Modelo> getListaModelos();
	
	/**
	 * 
	 * @param runConfigParams has:
	 * [0]: epoch number: int
	 * [1]: images per epoch: int
	 * [2]: learning rate : double
	 * [3]: decay rate of learning rate : double
	 * [4]: nesterov technique for optimization: boolean
	 * [5]: name
	 * @throws Exception 
	 */
	public void crearModelo(String nombre, String[] runConfigParams) throws Exception;
	
	public void guardarModelo(Modelo model, String[] runconfigParams) throws Exception;
	
}
