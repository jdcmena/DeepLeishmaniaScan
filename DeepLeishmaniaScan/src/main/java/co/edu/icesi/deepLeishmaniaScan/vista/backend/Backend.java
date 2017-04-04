package co.edu.icesi.deepLeishmaniaScan.vista.backend;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
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
	private JTextArea textArea;

	/**
	 * para cargar nuevas imagenes
	 */

	public Backend(boolean isFrontend) {
		System.out.println("starting");
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
		else{
		panelConfiguracion = new PanelConfiguracion(this);
		getContentPane().add(panelConfiguracion, BorderLayout.EAST);
		}
		
		//panel.add(scroll, BorderLayout.WEST);
		

	}

	private void initPnlConfig() {
		
		panelConfiguracion = new PanelConfiguracion(this);
		panelModelos = new PanelModelos(this);
		
		
		
		JScrollPane jsp = new JScrollPane(textArea);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		getContentPane().add(panelConfiguracion, BorderLayout.EAST);
		getContentPane().add(panelModelos, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Consola", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(true);
		textArea.setRows(10);
		
		
		JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        panel.add(scroll, BorderLayout.CENTER);

	}

	public void entrenar() {
		try {
			//TODO
			orquestador.entrenar(panelModelos.getModeloSeleccionado().getRutaDirectorioModelo(),textArea);
			//orquestador.setMetrics(panelModelos.getModeloSeleccionado(), metricas[0], 0, 0);
			
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}

	public void crearModelo(String text, int gen, int imgXG, double tasaA, double tasaM, boolean selected,
			String name) {

		try {
			orquestador.crearModelo(gen, imgXG, tasaA, tasaM, selected, name);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

	}

	public void setParametros(Modelo model, int epoch, int imgPerEpoch, double learningRate, double momentumRate
			, boolean nesterov) {
		orquestador.setParametrosModelo(model, epoch, imgPerEpoch, learningRate, momentumRate, nesterov);
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
			repaint();
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
