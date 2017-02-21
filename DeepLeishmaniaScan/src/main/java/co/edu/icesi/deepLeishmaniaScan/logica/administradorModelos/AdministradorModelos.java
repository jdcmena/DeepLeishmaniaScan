package co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class AdministradorModelos implements IAdministradorModelos {
	private static final char OS = File.separatorChar;
	private static final String MODELS_DIRECTORY = "." + OS + "src" + OS + "main" + OS + "resources" + OS + "models"
			+ OS + "";
	private static final String MODELS_LIST = "." + OS + "src" + OS + "main" + OS + "resources" + OS + "modelsList.txt";
	private static final String NEW_MODEL_TEMPLATE = MODELS_DIRECTORY;

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

	/**
	 * Cuando se va a guardar un modelo NUEVO
	 */
	@Override
	public void guardarModelo(Modelo model, String[] runConfigParams) throws Exception { // TODO
																							// Ready
																							// to
																							// Test
		final Gson gson = new Gson();
		String folderPath = NEW_MODEL_TEMPLATE + model.getID() + OS;
		String jsonRunConfigPath = NEW_MODEL_TEMPLATE + model.getID() + OS + "runconfig.json";
		model.setRunConfigRoute(jsonRunConfigPath);
		File modelJson = new File(folderPath);
		// modelJson.mkdirs();
		modelJson.mkdir();
		modelJson.createNewFile();

		modelJson = new File(folderPath + model.getID() + ".json");
		modelJson.getParentFile().mkdirs();
		modelJson.createNewFile();

		File modelJsonRunConfig = new File(jsonRunConfigPath);
		modelJsonRunConfig.getParentFile().mkdirs();
		modelJsonRunConfig.createNewFile();
		
		FileWriter fw1 = new FileWriter(folderPath + model.getID() + ".json");
		FileWriter fw2 = new FileWriter(jsonRunConfigPath);
		
		String j1 = gson.toJson(model);
		String j2 = gson.toJson(runConfigParams);
		fw1.write(j1);
		fw2.write(j2);
		fw1.flush();
		fw2.flush();
		//gson.toJson(j1, fw1);
		//gson.toJson(j2, fw2);
		
		File modelList = new File(MODELS_LIST);
		FileWriter fw = new FileWriter(modelList);

		BufferedWriter bw = new BufferedWriter(fw);
		bw.append(model.getID()+"");
		fw1.close();
		fw2.close();
		bw.close();

	}

	/**
	 * Buscar un modelo ya creado
	 * 
	 * @param id
	 */
	@Override
	public Modelo getModeloPorId(String id) throws IOException {
		final Gson gson = new Gson();
		Modelo prop = gson.fromJson(new FileReader(NEW_MODEL_TEMPLATE + id + OS + id + ".json"), Modelo.class);
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
	public void setParametrosModelo(Modelo model, int nEpoch, int nImgPerEpoch, double lR, double mR, double dLr,
			boolean nesterov) {
		// TODO - implement AdministradorModelos.setParametrosModelo
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Modelo> getListaModelos() {
		return listaModelos;
	}

	/**
	 * 
	 * @param runConfigParams
	 *            has: [0]: epoch number: int [1]: images per epoch: int [2]:
	 *            learning rate : double [3]: decay rate of learning rate :
	 *            double [4]: nesterov technique for optimization: boolean [5]:
	 *            name
	 */
	@Override
	public void crearModelo(String nombre, String[] runConfigParams) throws Exception { // TODO
		Modelo modelo = new Modelo(nombre);
		if (!modelExist(modelo.getID() + "")) {
			createModelFolder(modelo.getID() + "");
		}
		guardarModelo(modelo, runConfigParams);

	}

	/**
	 * verifica si el nombre del modelo pasado por parametro ya existe
	 * 
	 * @param modelId
	 * @return true o false
	 * @throws IOException
	 */
	private boolean modelExist(String modelId) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File(MODELS_LIST)));
		String line = "";
		while ((line = reader.readLine()) != null) {
			if (line.equals(modelId)) {
				reader.close();
				return true;
			}
		}
		reader.close();
		return false;
	}

	///////////////

	//////////////

	/**
	 * crea el directorio raiz de los modelos y crea el archivo con los nombres
	 * de modelos si no existe, y los lee
	 * 
	 * @throws IOException
	 */
	private void createRootModelsFolder() throws IOException {
		File mainFolder = new File(MODELS_DIRECTORY);
		if (!mainFolder.exists()) {
			mainFolder.mkdirs();
			mainFolder.createNewFile();
			System.out.println(mainFolder.getAbsolutePath());
		}
		File modelList = new File(MODELS_LIST);
		if (!modelList.exists()) {
			modelList.getParentFile().mkdirs();
			modelList.createNewFile();
		}
		System.out.println(modelList.getAbsolutePath());
		loadSavedModels(modelList);
	}

	/**
	 * Lee los nombres de los modelos y los convierte en clase Modelo Models
	 * must be saved as a string like this: [modelName].json
	 * 
	 * @param modelsTxt
	 * @throws IOException
	 */
	private void loadSavedModels(File modelsTxt) throws IOException { // TODO
		
		listaModelos = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(new File(MODELS_LIST)));
		String line = "";
		while ((line = reader.readLine()) != null) {
			Modelo modelo = getModeloPorId(line);
			listaModelos.add(modelo);
		}
		reader.close();
	}

	private void createModelFolder(String nombre) throws IOException {
		File mainFolder = new File(NEW_MODEL_TEMPLATE + nombre);
		if (!mainFolder.exists()) {
			mainFolder.mkdir();
			mainFolder.createNewFile();
		}
	}

}