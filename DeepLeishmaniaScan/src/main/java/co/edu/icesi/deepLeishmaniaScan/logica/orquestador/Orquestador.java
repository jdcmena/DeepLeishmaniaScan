package co.edu.icesi.deepLeishmaniaScan.logica.orquestador;

import java.io.IOException;
import java.util.List;
import javax.swing.JTextArea;
import co.edu.icesi.deepLeishmaniaScan.framework.API;
import co.edu.icesi.deepLeishmaniaScan.framework.IAPI;
import co.edu.icesi.deepLeishmaniaScan.logica.administradorImagenes.AdministradorImagenes;
import co.edu.icesi.deepLeishmaniaScan.logica.administradorImagenes.IAdministradorImagenes;
import co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos.AdministradorModelos;
import co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos.IAdministradorModelos;
import co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos.Modelo;
import co.edu.icesi.deepLeishmaniaScan.logica.procesamiento.Clasificacion;
import co.edu.icesi.deepLeishmaniaScan.logica.procesamiento.Entrenamiento;
import co.edu.icesi.deepLeishmaniaScan.logica.procesamiento.IClasificacion;
import co.edu.icesi.deepLeishmaniaScan.logica.procesamiento.IEntrenamiento;
import co.edu.icesi.deepLeishmaniaScan.vista.backend.Backend;

public class Orquestador implements IAdministradorImagenes, IAdministradorModelos, IClasificacion, IEntrenamiento {

	private IAdministradorImagenes administradorImagenes;
	private IAdministradorModelos administradorModelos;
	private IClasificacion clasificacion;
	private IEntrenamiento entrenamiento;
	
	private Backend backend;

	public Orquestador(Backend backend) throws Exception {
		this.backend = backend;
		init();
	}

	@Override
	public String[] obtenerMetricasGuardadas(String path) throws Exception {
		return entrenamiento.obtenerMetricasGuardadas(path);
	}

	@Override
	public void entrenar(String path, JTextArea consola) throws Exception {
		entrenamiento.entrenar(path, consola);
	}

	@Override
	public void clasificar(String path, JTextArea consola) throws Exception {
		clasificacion.clasificar(path, consola);
	}

	@Override
	public Modelo cargarModelo(String nombre) {
		return administradorModelos.cargarModelo(nombre);
	}

	@Override
	public Modelo getModeloPorId(String id) throws IOException {
		return administradorModelos.getModeloPorId(id);
	}

	@Override
	public void setParametrosModelo(Modelo model, int nEpoch, int nImgPerEpoch, double lR, double mR,
			boolean nesterov) {
		administradorModelos.setParametrosModelo(model, nEpoch, nImgPerEpoch, lR, mR, nesterov);
	}

	@Override
	public List<Modelo> getListaModelos() {
		return administradorModelos.getListaModelos();
	}

	@Override
	public void crearModelo(int gen, int imgXG, double tasaA, double tasaM, boolean selected, String name)
			throws Exception {
		administradorModelos.crearModelo(gen, imgXG, tasaA, tasaM, selected, name);
	}

	@Override
	public String getRutaPositivos() {
		return administradorImagenes.getRutaPositivos();
	}

	@Override
	public String getRutaNegativos() {
		return administradorImagenes.getRutaNegativos();
	}

	@Override
	public String getRutaConjuntoDeDatos() {
		return administradorImagenes.getRutaConjuntoDeDatos();
	}

	@Override
	public void cargarNuevasImagenes(String path, boolean leishmaniasis) throws Exception {
		administradorImagenes.cargarNuevasImagenes(path, leishmaniasis);
	}

	@Override
	public void nuevaClasificacion(String imgPath, boolean positivo) throws Exception {
		administradorImagenes.nuevaClasificacion(imgPath, positivo);
	}

	@Override
	public void guardarModelo(Modelo model, int gen, int imgXG, double tasaA, double tasaD, boolean selected)
			throws Exception {
		administradorModelos.guardarModelo(model, gen, imgXG, tasaA, tasaD, selected);
	}

	////////////////////////////////////////
	////////////////////////////////////////

	private void init() throws Exception {
		administradorModelos = new AdministradorModelos(this);
		administradorImagenes = new AdministradorImagenes();
		IAPI API = new API(this);
		clasificacion = new Clasificacion(API);
		entrenamiento = new Entrenamiento(API);
	}

	@Override
	public void setMetrics(Modelo model, double accuracy, double sensibility, double specificity) throws Exception {
		administradorModelos.setMetrics(model, accuracy, sensibility, specificity);
	}
	
	@Override
	public void setMetrics(Modelo model) throws Exception{
		administradorModelos.setMetrics(model);
	}

	@Override
	public double getSensibility() throws Exception {
		return administradorImagenes.getSensibility();
	}

	@Override
	public double getSpecificity() throws Exception {
		return administradorImagenes.getSpecificity();
	}

	public void notificar() {
		backend.trainNotified();
	}

	@Override
	public double[] getMetricasEntrenamiento() {
		return entrenamiento.getMetricasEntrenamiento();
	}

}
