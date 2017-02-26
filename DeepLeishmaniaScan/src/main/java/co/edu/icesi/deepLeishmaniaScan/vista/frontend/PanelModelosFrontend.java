package co.edu.icesi.deepLeishmaniaScan.vista.frontend;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos.Modelo;
import co.edu.icesi.deepLeishmaniaScan.vista.backend.Backend;

public class PanelModelosFrontend extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6168025265530375137L;

	private Backend principalB;
	
	private JList<Modelo> listaModelos;

	private Modelo modeloSeleccionado;

	private DefaultListModel<Modelo> dlm;
	
	public PanelModelosFrontend(Backend ventana) {
		
		principalB = ventana;
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(null, "Lista de modelos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		fillDLF();
		listaModelos = new JList<>(dlm);
		listaModelos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaModelos.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				JList<Modelo> source = (JList<Modelo>) e.getSource();
				modeloSeleccionado = (Modelo) source.getSelectedValue();
				principalB.modeloSeleccionado(modeloSeleccionado);
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
		
		
	}


	public Modelo getModeloSeleccionado() {
		return modeloSeleccionado;
	}

	public void fillDLF() {
		dlm = new DefaultListModel<>();
		for (Modelo modelo : principalB.getListaModelos()) {
			dlm.addElement(modelo);
		}
		this.repaint();
	}

}
