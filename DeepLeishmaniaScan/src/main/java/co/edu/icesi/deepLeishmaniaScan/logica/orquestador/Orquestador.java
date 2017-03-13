package co.edu.icesi.deepLeishmaniaScan.logica.orquestador;

import java.io.IOException;
import java.util.List;

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

public class Orquestador implements IAdministradorImagenes, IAdministradorModelos, IClasificacion, IEntrenamiento {
	
	
	private IAdministradorImagenes administradorImagenes;
	private IAdministradorModelos administradorModelos;
	private IClasificacion clasificacion;
	private IEntrenamiento entrenamiento;

	public Orquestador() throws Exception{
		init();
	}

	@Override
	public String[] obtenerMetricas(String path) throws Exception{
		return entrenamiento.obtenerMetricas(path);
	}

	@Override
	public double[] entrenar(String path) throws Exception{
		return entrenamiento.entrenar(path);
	}

	@Override
	public double clasificar(String path) {
		return clasificacion.clasificar(path);
	}

	@Override
	public Modelo cargarModelo(String nombre) {
		return administradorModelos.cargarModelo(nombre);
	}

	@Override
	public Modelo getModeloPorId(String id) throws IOException{
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
	public void crearModelo(int gen, int imgXG, double tasaA, double tasaM, boolean selected, String name) throws Exception{
		administradorModelos.crearModelo(gen, imgXG, tasaA,tasaM,selected,name);
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
	public void cargarNuevasImagenes(String path) throws Exception{
		administradorImagenes.cargarNuevasImagenes(path);
	}
	
	@Override
	public void nuevaClasificacion(String imgPath, boolean positivo) throws Exception{
		administradorImagenes.nuevaClasificacion(imgPath, positivo);
	}
	
	@Override
	public void guardarModelo(Modelo model, int gen, int imgXG, double tasaA, double tasaD, boolean selected) throws Exception {
		administradorModelos.guardarModelo(model, gen, imgXG, tasaA, tasaD, selected);
	}
	
	////////////////////////////////////////
	////////////////////////////////////////
	
	private void init() throws Exception{
		IAPI API = new API();
		administradorModelos = new AdministradorModelos();
		administradorImagenes = new AdministradorImagenes();
		clasificacion = new Clasificacion(API);
		entrenamiento = new Entrenamiento(API);
	}

	@Override
	public void setMetrics(Modelo model, double accuracy, double sensibility, double specificity) throws Exception {
		administradorModelos.setMetrics(model, accuracy, sensibility, specificity);
	}

	@Override
	public double getSensibility() throws Exception {
		return administradorImagenes.getSensibility();
	}

	@Override
	public double getSpecificity() throws Exception {
		return administradorImagenes.getSpecificity();
	}



	
	

}
