package co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos;

import java.io.File;
import java.util.List;
import com.google.gson.Gson;

public class AdministradorModelos implements IAdministradorModelos {
	
	public static final String MODEL_FOLDER ="/models/"; //TODO

	public AdministradorModelos() {
		// TODO - implement AdministradorModelos.AdministradorModelos
		File model = new File(MODEL_FOLDER);
		if(!model.exists()){
			model.mkdirs();
		}
		else{
			
		}
		
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nombre
	 */
	@Override
	public Modelo cargarModelo(String nombre) {
		// TODO - implement AdministradorModelos.cargarModelo
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param id
	 */
	@Override
	public Modelo getModeloPorId(int id) {
		// TODO - implement AdministradorModelos.getModeloPorId
		throw new UnsupportedOperationException();
	}

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
	@Override
	public void setParametrosModelo(Modelo model, int nEpoch, int nImgPerEpoch, double lR, double mR, double dLr, boolean nesterov) {
		// TODO - implement AdministradorModelos.setParametrosModelo
		throw new UnsupportedOperationException();
	}

	@Override
	public List getListaModelos() {
		// TODO - implement AdministradorModelos.getListaModelos
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 
	 * @param runConfigParams has:
	 * [0]: epoch number: int
	 * [1]: images per epoch: int
	 * [2]: learning rate : double
	 * [3]: decay rate of learning rate : double
	 * [4]: nesterov technique for optimization: boolean
	 * [5]: name
	 */
	@Override
	public void crearModelo(String[] runConfigParams){
		// TODO
	}

}