package co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdministradorModelos implements IAdministradorModelos {
	
	private static final String MODELS_DIRECTORY = ".\\src\\main\\resources\\models";
	private static final String MODELS_LIST = ".\\src\\main\\resources\\modelsList.txt";
	private static final String NEW_MODEL_TEMPLATE = MODELS_DIRECTORY+"\\";
	
	private ArrayList<Modelo> listaModelos; 
	

	public AdministradorModelos() {
		try {
			createRootModelsFolder();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	@Override
	public void guardarModelo(String modelId, String[] runconfigParams) throws Exception { //TODO Ready to Test
		final Gson gson = new Gson();
		gson.toJson(getModeloPorId(modelId), new FileWriter(NEW_MODEL_TEMPLATE+modelId+"\\"+modelId+".json"));
		gson.toJson(runconfigParams, new FileWriter(NEW_MODEL_TEMPLATE+modelId+"\\runconfig.json"));
	}

	/**
	 * 
	 * @param id
	 */
	@Override
	public Modelo getModeloPorId(String id) throws IOException{ //TODO Ready to test
		final Gson gson = new Gson();
		Modelo prop = gson.fromJson(new FileReader(NEW_MODEL_TEMPLATE+id), Modelo.class);
		return prop;
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
	public List<Modelo> getListaModelos() {
		return listaModelos;
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
	public void crearModelo(String nombre, String[] runConfigParams) throws Exception{ //TODO
		if(!modelExist(nombre)){
			createModelFolder(nombre);
		}
		else{
			throw new Exception();
		}
	}
	private boolean modelExist(String modelId) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(new File(MODELS_LIST)));
		String line = "";
		while((line = reader.readLine())!=null){
			if(line.equals(modelId)){
				reader.close();
				return true;
			}
		}
		reader.close();
		return false;
	}
	
	///////////////
	
	//////////////
	
	
	private void createRootModelsFolder() throws IOException{
		File mainFolder = new File(MODELS_DIRECTORY);
		if(!mainFolder.exists()){
			mainFolder.getParentFile().mkdirs();
		}
		File modelList = new File(MODELS_LIST);
		if(!modelList.exists()){
		modelList.getParentFile().mkdirs();
		}
		loadSavedModels(modelList);
	}
	/**
	 * Models must be saved as a string like this: [modelName].json 
	 * @param modelsTxt
	 * @throws IOException
	 */
	private void loadSavedModels(File modelsTxt) throws IOException{ //TODO Ready to test
		listaModelos = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(new File(MODELS_LIST)));
		String line = "";
		while((line = reader.readLine())!=null){
			Modelo modelo = getModeloPorId(line);
			listaModelos.add(modelo);
		}
		reader.close();
	}
	
	private void createModelFolder(String nombre){// TODO
		File mainFolder = new File(MODELS_DIRECTORY);
		if(!mainFolder.exists()){
			mainFolder.getParentFile().mkdir();
			
			File newModel = new File(NEW_MODEL_TEMPLATE+nombre);
		}
		else{
			
		}
	}



}