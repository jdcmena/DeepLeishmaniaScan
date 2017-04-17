package co.edu.icesi.deepLeishmaniaScan.vista.frontend;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

import co.edu.icesi.deepLeishmaniaScan.logica.orquestador.Orquestador;
import co.edu.icesi.deepLeishmaniaScan.vista.backend.Backend;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class Frontend extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6987076966121203944L;
	private PanelModelosFrontend panelModelosFrontend;
	private PanelImagen panelImagen;
	private Backend backend;
	private JTextArea consolaF;
	private JLabel lblSiResultado;
	private JLabel lblNoResultado;
	private Orquestador orquestador;

	public Frontend() {

		setTitle("Ventana de Clasificacion");
		setSize(420, 373);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 206, 130, 0 };
		gridBagLayout.rowHeights = new int[] { 250, 36, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		GridBagConstraints gbc_panelImage = new GridBagConstraints();
		gbc_panelImage.fill = GridBagConstraints.BOTH;
		gbc_panelImage.insets = new Insets(0, 0, 5, 5);
		gbc_panelImage.gridx = 0;
		gbc_panelImage.gridy = 0;
		panelImagen = new PanelImagen(this);
		getContentPane().add(panelImagen, gbc_panelImage);

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

		consolaF = new JTextArea();
		consolaF.setLineWrap(true);
		consolaF.setEditable(false);
		consolaF.setWrapStyleWord(true);
		consolaF.setSize(400, 200);
		consolaF.setRows(6);
		DefaultCaret caret = (DefaultCaret) consolaF.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		// JProgressBar progressBar = new JProgressBar();
		// panel.add(progressBar, BorderLayout.SOUTH);

		JScrollPane jsp = new JScrollPane(consolaF);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		panel.add(jsp, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Diagnostico", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_1, BorderLayout.EAST);
		GridLayout gl_panel_1 = new GridLayout(2, 2);
		gl_panel_1.setVgap(5);
		gl_panel_1.setHgap(5);
		panel_1.setLayout(gl_panel_1);

		JLabel lblSiLbl = new JLabel("Positivo:");
		lblSiLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblSiLbl);

		lblSiResultado = new JLabel("");
		lblSiResultado.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblSiResultado);

		lblNoResultado = new JLabel("Negativo:");
		lblNoResultado.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNoResultado);

		JLabel lblNoLbl = new JLabel("");
		lblNoLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNoLbl);

		backend = new Backend(true);
		orquestador = backend.getOrquestadorInstance();
		panelModelosFrontend = new PanelModelosFrontend(backend);
		modelPanel.add(panelModelosFrontend, BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		pack();

	}

	public String[] obtenerMetricas(String path) {
		String[] array = null;
		try {
			array = orquestador.obtenerMetricasGuardadas(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	public double clasificar(String path) {
		double probability = 0;

		File image = new File(path);
		if (image.isDirectory()) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una imagen para clasificar");
		} else if (image.isFile()) {
			try {
				String modelPath = panelModelosFrontend.getModeloSeleccionado().getRunConfigPath();
				orquestador.clasificar(modelPath + " " + image.getAbsolutePath(), consolaF);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		}

		return probability;
	}

	public void setPrediccionPositiva(String text) {
		lblSiResultado.setText(text);
	}

	public void setPrediccionNegativa(String text) {
		lblNoResultado.setText(text);
	}

	public static void main(String[] args) {
		Frontend fe = new Frontend();
		fe.setVisible(true);
	}

}
