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

public class AdministradorModelos implements IAdministradorModelos {

	private static final Logger log = LoggerFactory.getLogger(AdministradorModelos.class);

	private static final char OS = File.separatorChar;
	private static final String MODELS_DIRECTORY = "." + OS + "src" + OS + "main" + OS + "resources" + OS + "models"
			+ OS + "";
	private static final String MODELS_LIST = "." + OS + "src" + OS + "main" + OS + "resources" + OS + "modelsList.txt";
	private static final String JSON_EXT = ".json";

	private ArrayList<Modelo> listaModelos;

	public AdministradorModelos() {
		try {
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
	public void guardarModelo(Modelo model, int gen, int imgXG, double tasaA, double tasaD, boolean nesterov) {

		PrintWriter out = null;
		FileWriter fw1 = null;
		FileWriter fw2 = null;
		try {
			final Gson gson = new Gson();
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
			dto.setGeneraciones(gen);
			dto.setImagenesPorGeneracion(imgXG);
			dto.setTasaAprendizaje(tasaA);
			dto.setTasaDecadencia(tasaD);
			dto.setNesterov(nesterov);
			dto.setNombre(model.getNombre());

			String j2 = gson.toJson(dto);
			fw1.write(j1);
			fw2.write(j2);

			out = new PrintWriter(new FileWriter(MODELS_LIST, true));
			out.append(Integer.toString(model.getID()));

		} catch (Exception e) {

		} finally {
			try {
				fw1.flush();
				fw2.flush();

				fw1.close();
				fw2.close();
				out.flush();
				out.close();
			} catch (Exception e) {

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
		final Gson gson = new Gson();
		return gson.fromJson(new FileReader(MODELS_DIRECTORY + id + OS + id + ".json"), Modelo.class);
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
	public void crearModelo(int gen, int imgXG, double tasaA, double tasaD, boolean selected, String name)
			throws Exception {
		Modelo modelo = new Modelo(name);
		if (!modelExist(Integer.toString(modelo.getID()))) {
			createModelFolder(Integer.toString(modelo.getID()));
		}
		guardarModelo(modelo, gen, imgXG, tasaA, tasaD, selected);

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

}