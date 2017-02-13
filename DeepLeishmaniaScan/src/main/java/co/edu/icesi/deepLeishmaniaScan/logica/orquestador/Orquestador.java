package co.edu.icesi.deepLeishmaniaScan.logica.orquestador;

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

	public static IAdministradorImagenes administradorImagenes;
	public static IAdministradorModelos administradorModelos;
	public static IClasificacion clasificacion;
	public static IEntrenamiento entrenamiento;

	public Orquestador() {
		init();
	}

	@Override
	public String[] obtenerMetricas(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] entrenar(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double clasificar(String path) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Modelo cargarModelo(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Modelo getModeloPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParametrosModelo(Modelo model, int nEpoch, int nImgPerEpoch, double lR, double mR, double dLr,
			boolean nesterov) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Modelo> getListaModelos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void crearModelo(String[] runConfigParams) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getRutaPositivos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRutaNegativos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRutaConjuntoDeDatos() {
		// TODO Auto-generated method stub
		return null;
	}

	////////////////////////////////////////
	////////////////////////////////////////

	private static void init() {
		administradorImagenes = new AdministradorImagenes();
		administradorModelos = new AdministradorModelos();
		clasificacion = new Clasificacion();
		entrenamiento = new Entrenamiento();
	}

	@Override
	public void cargarNuevasImagenes(String path) {
		// TODO Auto-generated method stub
		
	}

}
