package co.edu.icesi.deepLeishmaniaScan.vista.backend;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos.Modelo;

public class PanelModelos extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2400780611744407951L;

	private Backend principalB;

	private JList<Modelo> listaModelos;

	private Modelo modeloSeleccionado;

	private JButton btnEntrenar;

	private JButton btnNuevoModelo;

	public PanelModelos(Backend ventana) {
		principalB = ventana;
		setLayout(new BorderLayout());
		fillDLF();
		listaModelos.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (!e.getValueIsAdjusting()) {// This line prevents double
												// events
					@SuppressWarnings("unchecked")
					JList<Modelo> source = (JList<Modelo>) e.getSource();
					modeloSeleccionado = (Modelo) source.getSelectedValue();
					habilitarEntrenar();
					principalB.modeloSeleccionado(modeloSeleccionado);
				}

			}
		});
		this.add(listaModelos, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		this.add(panel_1, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		btnEntrenar = new JButton("Entrenar Modelo");
		btnEntrenar.setActionCommand("EM");
		btnEntrenar.addActionListener(this);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 9;
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		panel_1.add(btnEntrenar, gbc_btnNewButton);

		btnNuevoModelo = new JButton("Crear nuevo modelo");
		btnNuevoModelo.addActionListener(this);
		btnNuevoModelo.setActionCommand("NM");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridwidth = 8;
		gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_1.gridx = 10;
		gbc_btnNewButton_1.gridy = 0;
		panel_1.add(btnNuevoModelo, gbc_btnNewButton_1);
		deshabilitarEntrenar();
	}

	public Modelo getModeloSeleccionado() {
		return modeloSeleccionado;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String com = e.getActionCommand();
		switch (com) {
		case "NM":
			DialogCreateModel dlg = new DialogCreateModel(principalB, this);
			dlg.setVisible(true);
			break;

		case "EM":
			principalB.entrenar();
			break;

		}
	}

	public void habilitarEntrenar() {
		btnEntrenar.setEnabled(true);
	}

	public void deshabilitarEntrenar() {
		btnEntrenar.setEnabled(false);
	}

	public void fillDLF() {
		Modelo[] list = principalB.getListaModelos().toArray(new Modelo[0]);
		if (listaModelos == null) {
			listaModelos = new JList<>(list);
		} else {
			listaModelos.setListData(list);
			listaModelos.setCellRenderer(new DefaultListCellRenderer());
		}
		listaModelos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaModelos.setCellRenderer(new DefaultListCellRenderer());
	}

}
