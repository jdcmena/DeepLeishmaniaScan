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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AdministradorModelos implements IAdministradorModelos {

	private static final Logger log = LoggerFactory.getLogger(AdministradorModelos.class);

	private static final char OS = File.separatorChar;
	private static final String MODELS_DIRECTORY = "." + OS + "src" + OS + "main" + OS + "resources" + OS + "models"
			+ OS + "";
	private static final String MODELS_LIST = "." + OS + "src" + OS + "main" + OS + "resources" + OS + "modelsList.txt";
	private static final String JSON_EXT = ".json";
	
	private Gson gson;
	
	private ArrayList<Modelo> listaModelos;

	public AdministradorModelos() {
		try {
			gson = new GsonBuilder().setPrettyPrinting().create();
			createRootModelsFolder();
		} catch (IOException e) {
			log.info("Constructor de AdministradorModelos");
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
	public void guardarModelo(Modelo model, int gen, int imgXG, double tasaA, double momentum, boolean nesterov)
			throws Exception {

		PrintWriter out = null;
		FileWriter fw1 = null;
		FileWriter fw2 = null;

		String folderPath = MODELS_DIRECTORY + model.getID() + OS;
		String jsonRunConfigPath = MODELS_DIRECTORY + model.getID() + OS + "runconfig" + JSON_EXT;
		model.setRutaDirectorioModelo(folderPath);
		model.setRunConfigRoute(jsonRunConfigPath);
		File modelJson = new File(folderPath);
		modelJson.mkdir();
		modelJson.createNewFile();

		modelJson = new File(folderPath + model.getID() + JSON_EXT);
		modelJson.getParentFile().mkdirs();
		modelJson.createNewFile();

		File modelJsonRunConfig = new File(jsonRunConfigPath);
		modelJsonRunConfig.getParentFile().mkdirs();
		modelJsonRunConfig.createNewFile();

		fw1 = new FileWriter(folderPath + model.getID() + JSON_EXT);
		fw2 = new FileWriter(jsonRunConfigPath);

		String j1 = gson.toJson(model);
		RunConfigDTO dto = new RunConfigDTO();
		dto.setId(model.getID());
		dto.setGeneraciones(gen);
		dto.setImagenesPorGeneracion(imgXG);
		dto.setTasaAprendizaje(tasaA);
		dto.setMomentum(momentum);
		dto.setNesterov(nesterov);

		String j2 = gson.toJson(dto);
		fw1.write(j1);
		fw2.write(j2);

		out = new PrintWriter(new FileWriter(MODELS_LIST, true));
		out.append(Integer.toString(model.getID()) + "\n");

		fw1.flush();
		fw2.flush();

		fw1.close();
		fw2.close();
		out.flush();
		out.close();

	}

	/**
	 * metodo encargado de asignar las métricas de interés al modelo que se esté
	 * utilizando
	 */
	@Override
	public void setMetrics(Modelo model, double accuracy, double sensibility, double specificity) throws Exception {
		for (Modelo m : listaModelos) {
			if (m.getID() == model.getID()) {
				m.setAccuracy(accuracy);
				m.setSensibility(sensibility);
				m.setSpecificity(specificity);
				File modelJson = new File(MODELS_DIRECTORY + model.getID() + OS + model.getID() + JSON_EXT);
				if (modelJson.exists()) {
					FileWriter fw = new FileWriter(MODELS_DIRECTORY + model.getID() + OS + model.getID() + JSON_EXT);
					String js = gson.toJson(model);
					fw.write(js);
					fw.flush();
					fw.close();
				}
			}
		}
	}

	/**
	 * Buscar un modelo ya creado
	 * 
	 * @param id
	 */
	@Override
	public Modelo getModeloPorId(String id) throws IOException {
		return gson.fromJson(new FileReader(MODELS_DIRECTORY + id + OS + id + ".json"), Modelo.class);
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
	public void crearModelo(int geneneraciones, int imagenPorGeneracion, double tasaAprendizaje, double momentum,
			boolean nesterov, String nombre) throws Exception {
		Modelo modelo = new Modelo(nombre);
		if (!modelExist(Integer.toString(modelo.getID()))) {
			createModelFolder(Integer.toString(modelo.getID()));
		}
		guardarModelo(modelo, geneneraciones, imagenPorGeneracion, tasaAprendizaje, momentum, nesterov);

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
		String line;
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
		}
		File modelList = new File(MODELS_LIST);
		if (!modelList.exists()) {
			modelList.getParentFile().mkdirs();
			modelList.createNewFile();
		}
		loadSavedModels();
	}

	/**
	 * Lee los nombres de los modelos y los convierte en clase Modelo Models
	 * must be saved as a string like this: [modelName].json
	 * 
	 * @param modelsTxt
	 * @throws IOException
	 */
	private void loadSavedModels() throws IOException {

		listaModelos = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(new File(MODELS_LIST)));
		String line;
		while ((line = reader.readLine()) != null) {
			Modelo modelo = getModeloPorId(line);
			listaModelos.add(modelo);
		}
		reader.close();
	}

	private void createModelFolder(String nombre) throws IOException {
		File mainFolder = new File(MODELS_DIRECTORY + nombre);
		if (!mainFolder.exists()) {
			mainFolder.mkdir();
			mainFolder.createNewFile();
		}
	}

	@Override
	public void setParametrosModelo(Modelo model, int nEpoch, int nImgPerEpoch, double lR, double mR,
			boolean nesterov) {
		// TODO Auto-generated method stub

	}

}