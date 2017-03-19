package co.edu.icesi.deepLeishmaniaScan.vista.frontend;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import co.edu.icesi.deepLeishmaniaScan.logica.orquestador.Orquestador;
import co.edu.icesi.deepLeishmaniaScan.vista.backend.Backend;

public class Frontend extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6987076966121203944L;
	private PanelModelosFrontend panelModelosFrontend;
	private Backend backend;
	private Orquestador orquestador;

	public Frontend() {

		setTitle("Ventana de Clasificacion");
		setSize(480, 430);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 206, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 250, 36, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);
		JPanel panelImage = new JPanel();
		panelImage.setBorder(
				new TitledBorder(null, "Opciones de imagen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelImage = new GridBagConstraints();
		gbc_panelImage.fill = GridBagConstraints.BOTH;
		gbc_panelImage.insets = new Insets(0, 0, 5, 5);
		gbc_panelImage.gridx = 0;
		gbc_panelImage.gridy = 0;
		getContentPane().add(panelImage, gbc_panelImage);
		GridBagLayout gbl_panelImage = new GridBagLayout();
		gbl_panelImage.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelImage.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panelImage.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelImage.rowWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelImage.setLayout(gbl_panelImage);

		JLabel lblImage = new JLabel("sumtext");
		GridBagConstraints gbc_lblImage = new GridBagConstraints();
		gbc_lblImage.fill = GridBagConstraints.VERTICAL;
		gbc_lblImage.gridwidth = 2;
		gbc_lblImage.gridheight = 8;
		gbc_lblImage.insets = new Insets(0, 0, 5, 0);
		gbc_lblImage.gridx = 0;
		gbc_lblImage.gridy = 0;
		panelImage.add(lblImage, gbc_lblImage);

		JButton btnCargarImagen = new JButton("Cargar Imagen");
		GridBagConstraints gbc_btnCargarImagen = new GridBagConstraints();
		gbc_btnCargarImagen.insets = new Insets(0, 0, 0, 5);
		gbc_btnCargarImagen.gridx = 0;
		gbc_btnCargarImagen.gridy = 8;
		panelImage.add(btnCargarImagen, gbc_btnCargarImagen);

		JButton btnClasificar = new JButton("Clasificar");
		GridBagConstraints gbc_btnClasificar = new GridBagConstraints();
		gbc_btnClasificar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClasificar.gridx = 1;
		gbc_btnClasificar.gridy = 8;
		panelImage.add(btnClasificar, gbc_btnClasificar);

		JPanel modelPanel = new JPanel();
		GridBagConstraints gbc_modelPanel = new GridBagConstraints();
		gbc_modelPanel.insets = new Insets(0, 0, 5, 0);
		gbc_modelPanel.fill = GridBagConstraints.BOTH;
		gbc_modelPanel.gridx = 1;
		gbc_modelPanel.gridy = 0;
		getContentPane().add(modelPanel, gbc_modelPanel);
		modelPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Consola", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridwidth = 2;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		getContentPane().add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setSize(400, 200);
		panel.add(textArea, BorderLayout.CENTER);

		JProgressBar progressBar = new JProgressBar();
		panel.add(progressBar, BorderLayout.SOUTH);

		JScrollPane jsp = new JScrollPane(textArea);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		backend = new Backend(true);
		orquestador = backend.getOrquestadorInstance();
		panelModelosFrontend = new PanelModelosFrontend(backend);
		modelPanel.add(panelModelosFrontend, BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// pack();

	}

	public String[] obtenerMetricas(String path) {
		String[] array = null;
		try {
			array = orquestador.obtenerMetricas(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}

	public double clasificar(String path) {
		double probability = 0;

		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setDialogTitle("Seleccione la imagen que quiere clasificar");
		int returnVal = jfc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File selected = jfc.getSelectedFile();
			if (selected.isDirectory()) {
				JOptionPane.showMessageDialog(this, "Debe seleccionar una imagen para clasificar");
			} else if (selected.isFile()) {
				try{
				String modelPath = panelModelosFrontend.getModeloSeleccionado().getRunConfigPath();
				probability = orquestador.clasificar(selected.getAbsolutePath()+" "+modelPath);
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(this, e.getMessage());
				}
			}
		}
		return probability;
	}

	public static void main(String[] args) {
		Frontend fe = new Frontend();
		fe.setVisible(true);
	}

}
