package co.edu.icesi.deepLeishmaniaScan.vista.backend;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos.Modelo;
import co.edu.icesi.deepLeishmaniaScan.logica.orquestador.Orquestador;

public class BackendView extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2497389222299231096L;

	private Orquestador orquestador;

	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;

	private PanelConfiguracion panelConfiguracion;
	private PanelModelos panelModelos;

	/**
	 * para cargar nuevas imagenes
	 */

	public BackendView() {

		setTitle("Ventana de configuracion de sistema");
		menuBar = new JMenuBar();
		menu = new JMenu("Archivo");
		menuItem = new JMenuItem("Cargar Imagenes nuevas");
		menuItem.setActionCommand("CARGAR_IMAGENES");
		menuItem.addActionListener(this);
		// menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		orquestador = new Orquestador();
		
		initPnlConfig();
		pack();

	}

	private void initPnlConfig() {
		panelConfiguracion = new PanelConfiguracion(this);
		panelModelos = new PanelModelos(this);
		getContentPane().add(panelConfiguracion, BorderLayout.CENTER);
		getContentPane().add(panelModelos, BorderLayout.EAST);

	}
	public List<Modelo> getListaModelos(){
		return orquestador.getListaModelos();
	}

	public void cargarNuevasImagenes(String path) {
		orquestador.cargarNuevasImagenes(path);
	}
	public void crearModelo(String nombre, String[] runConfig){
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {

			if (e.getActionCommand().equals("CARGAR_IMAGENES")) {
				panelConfiguracion.showFileChooser();
			}
		} catch (IOException io) {

		} catch (Exception ex) {

		}
	}
	
	////////////MAIN
	
	public static void main(String[] args) {
		BackendView bV = new BackendView();
		bV.setVisible(true);
	}

}
