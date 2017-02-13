package co.edu.icesi.deepLeishmaniaScan.vista.backend;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.plaf.MenuItemUI;

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

		setTitle("Ventana de configuración de sistema");
		menuBar = new JMenuBar();
		menu = new JMenu("Archivo");
		menuItem = new JMenuItem("Cargar Imagenes nuevas");
		menuItem.setActionCommand("CARGAR_IMAGENES");
		menuItem.addActionListener(this);
		// menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);

		this.setJMenuBar(menuBar);

		setLayout(new BorderLayout());

		initPnlConfig();

		pack();

	}

	private void initPnlConfig() {
		panelConfiguracion = new PanelConfiguracion(this);
		this.add(panelConfiguracion, BorderLayout.CENTER);

	}

	public static void main(String[] args) {
		BackendView bV = new BackendView();
		bV.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("CARGAR_IMAGENES")) {
			panelConfiguracion.showFileChooser();
		}

	}

}
