package co.edu.icesi.deepLeishmaniaScan.vista.backend;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JPanel;

import co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos.Modelo;

public class PanelModelos extends JPanel {
	
	private BackendView principal;
	
	private JList<Modelo> listaModelos;
	
	public PanelModelos(BackendView ventana){
		principal = ventana;
		setLayout(new BorderLayout());
		
		
		
	}

}
