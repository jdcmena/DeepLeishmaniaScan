package co.edu.icesi.deepLeishmaniaScan.logica.orquestador;

import java.io.IOException;
import java.util.List;
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
	
	public static IAdministradorImagenes administradorImagenes;
	public static IAdministradorModelos administradorModelos;
	public static IClasificacion clasificacion;
	public static IEntrenamiento entrenamiento;

	public Orquestador() {
		init();
	}

	@Override
	public String[] obtenerMetricas(String path) {
		return entrenamiento.obtenerMetricas(path);
	}

	@Override
	public double[] entrenar(String path) {
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
	public void setParametrosModelo(Modelo model, int nEpoch, int nImgPerEpoch, double lR, double mR, double dLr,
			boolean nesterov) {
		administradorModelos.setParametrosModelo(model, nEpoch, nImgPerEpoch, lR, mR, dLr, nesterov);
	}

	@Override
	public List<Modelo> getListaModelos() {
		return administradorModelos.getListaModelos();
	}

	@Override
	public void crearModelo(String nombre, String[] runConfigParams) throws Exception{
		administradorModelos.crearModelo(nombre, runConfigParams);
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
	public void cargarNuevasImagenes(String path) {
		administradorImagenes.cargarNuevasImagenes(path);
	}
	
	@Override
	public void guardarModelo(Modelo model, String[] runconfigParams) throws Exception {
		administradorModelos.guardarModelo(model, runconfigParams);
	}
	
	////////////////////////////////////////
	////////////////////////////////////////
	
	private static void init() {
		administradorModelos = new AdministradorModelos();
		administradorImagenes = new AdministradorImagenes();
		clasificacion = new Clasificacion();
		entrenamiento = new Entrenamiento();
	}


	
	

}
