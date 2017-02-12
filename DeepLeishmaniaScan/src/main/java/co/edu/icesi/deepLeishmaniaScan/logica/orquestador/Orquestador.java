package co.edu.icesi.deepLeishmaniaScan.logica.orquestador;

import co.edu.icesi.deepLeishmaniaScan.logica.administradorImagenes.AdministradorImagenes;
import co.edu.icesi.deepLeishmaniaScan.logica.administradorImagenes.IAdministradorImagenes;
import co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos.AdministradorModelos;
import co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos.IAdministradorModelos;
import co.edu.icesi.deepLeishmaniaScan.logica.procesamiento.Clasificacion;
import co.edu.icesi.deepLeishmaniaScan.logica.procesamiento.Entrenamiento;
import co.edu.icesi.deepLeishmaniaScan.logica.procesamiento.IClasificacion;
import co.edu.icesi.deepLeishmaniaScan.logica.procesamiento.IEntrenamiento;

public class Orquestador {
	
	public static IAdministradorImagenes administradorImagenes;
	public static IAdministradorModelos administradorModelos;
	public static IClasificacion clasificacion;
	public static IEntrenamiento entrenamiento;

	public static void main(String[] args){
		init();
	}
	
	private static void init(){
		administradorImagenes = new AdministradorImagenes();
		administradorModelos = new AdministradorModelos();
		clasificacion = new Clasificacion();
		entrenamiento = new Entrenamiento();
	}
	
}
