package co.edu.icesi.deepLeishmaniaScan.vista.backend;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos.Modelo;

public class PanelModelos extends JPanel implements ActionListener {
	
	private BackendView principal;
	
	private JList<Modelo> listaModelos;
	
	private Modelo modeloSeleccionado;

	private JButton btnEditarHiperparametros;

	private JButton btnNuevoModelo;
	
	
	public PanelModelos(BackendView ventana){
		principal = ventana;
		setLayout(new BorderLayout());
		listaModelos = new JList<>();
		listaModelos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		listaModelos.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				modeloSeleccionado = (Modelo)e.getSource();
			}
		});
		listaModelos.setListData((Modelo[]) principal.getListaModelos().toArray());
		this.add(listaModelos, BorderLayout.CENTER);
		
		
		JPanel panel_1 = new JPanel();
		this.add(panel_1, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		btnEditarHiperparametros = new JButton("Editar Hiperparametros");
		btnEditarHiperparametros.setActionCommand("EH");
		btnEditarHiperparametros.addActionListener(this);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 9;
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		panel_1.add(btnEditarHiperparametros, gbc_btnNewButton);
		
		btnNuevoModelo = new JButton("Crear nuevo modelo");
		btnNuevoModelo.addActionListener(this);
		btnNuevoModelo.setActionCommand("NM");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.gridwidth = 10;
		gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_1.gridx = 10;
		gbc_btnNewButton_1.gridy = 0;
		panel_1.add(btnNuevoModelo, gbc_btnNewButton_1);
		
	}

	public Modelo getModeloSeleccionado(){
		return modeloSeleccionado;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String com = e.getActionCommand();
		switch(com){
		case "NM":
			JOptionPane.showMessageDialog(this, "new model");
			break;
			
		case "EH":
			JOptionPane.showMessageDialog(this, "edit hiperparameters");
			break;
			
		}
	}
	

}
