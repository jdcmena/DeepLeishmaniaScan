package co.edu.icesi.deepLeishmaniaScan.vista.backend;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos.Modelo;
import co.edu.icesi.deepLeishmaniaScan.logica.orquestador.Orquestador;

public class Backend extends JFrame implements ActionListener {

	private static final Logger log = LoggerFactory.getLogger(Backend.class);

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

	public Backend(boolean isFrontend) {

		try {
			orquestador = new Orquestador();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		if (!isFrontend) {
			setTitle("Ventana de configuracion de sistema");
			menuBar = new JMenuBar();
			menu = new JMenu("Archivo");
			menuItem = new JMenuItem("Cargar Imagenes nuevas");
			menuItem.setActionCommand("CARGAR_IMAGENES");
			menuItem.addActionListener(this);
			menu.add(menuItem);
			menuBar.add(menu);
			this.setJMenuBar(menuBar);
			getContentPane().setLayout(new BorderLayout());
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			initPnlConfig();
			pack();
		}
		panelConfiguracion = new PanelConfiguracion(this);
	}

	private void initPnlConfig() {
		panelConfiguracion = new PanelConfiguracion(this);
		panelModelos = new PanelModelos(this);
		getContentPane().add(panelConfiguracion, BorderLayout.CENTER);
		getContentPane().add(panelModelos, BorderLayout.WEST);

	}

	public void entrenar() {
		try {
			orquestador.entrenar(panelModelos.getModeloSeleccionado().getRutaDirectorioModelo());
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}

	public void crearModelo(String text, int gen, int imgXG, double tasaA, double tasaM, double tasaD, boolean selected,
			String name) {

		try {
			orquestador.crearModelo(gen, imgXG, tasaA, tasaD, selected, name);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

	}

	public void setParametros(Modelo model, int epoch, int imgPerEpoch, double learningRate, double momentumRate,
			double decayRate, boolean nesterov) {
		orquestador.setParametrosModelo(model, epoch, imgPerEpoch, learningRate, momentumRate, decayRate, nesterov);
	}

	public List<Modelo> getListaModelos() {
		return orquestador.getListaModelos();
	}

	public void cargarImagenesEntrenamiento(String path) {
		try {
			orquestador.cargarNuevasImagenes(path);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}

	public void modeloSeleccionado(Modelo modelo) {
		try {
			panelConfiguracion.mostrarHiperparametros(modelo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {

			if (e.getActionCommand().equals("CARGAR_IMAGENES")) {
				showFileChooser();
			}
		} catch (IOException io) {

		} catch (Exception ex) {

		}
	}

	private void showFileChooser() throws IOException {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.setDialogTitle("Seleccione la carpeta que contenga las imagenes nuevas");
		int returnVal = jfc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String selected = jfc.getSelectedFile().getAbsolutePath();
			this.cargarImagenesEntrenamiento(selected);
		}

	}
	
	public Orquestador getOrquestadorInstance(){
		return orquestador;
	}

	//////////// MAIN

	public static void main(String[] args) {
		Backend bV = new Backend(false);
		bV.setVisible(true);
	}

}
