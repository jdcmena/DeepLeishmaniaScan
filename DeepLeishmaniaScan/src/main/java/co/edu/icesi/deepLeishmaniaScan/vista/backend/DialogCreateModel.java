package co.edu.icesi.deepLeishmaniaScan.vista.backend;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class DialogCreateModel extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1254961265820183478L;
	private JTextField txtGeneraciones;
	private JTextField txtImgXGeneracion;
	private JTextField txtTasaDeAprendizaje;
	private JTextField txtNombre;
	private JTextField txtMomentum;
	JRadioButton rbtnNesterov;

	private JButton btnCrearModelo;

	private Backend principal;
	private PanelModelos panelModelos;

	public DialogCreateModel(Backend ventana, PanelModelos panelModelos) {
		this.panelModelos = panelModelos;
		principal = ventana;
		setTitle("Crear nuevo modelo");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		getContentPane().setLayout(new BorderLayout());

		JLabel lblNewLabel_4 = new JLabel("Nombre");
		panel.add(lblNewLabel_4);

		txtNombre = new JTextField();
		panel.add(txtNombre);

		JLabel lblNewLabel = new JLabel("Numero de generaciones");
		panel.add(lblNewLabel);

		txtGeneraciones = new JTextField();
		txtGeneraciones.setToolTipText("N\u00FAmero de veces que el programa recorrer\u00E1 el conjunto de datos");
		panel.add(txtGeneraciones);

		JLabel lblNewLabel_1 = new JLabel("Imagenes por generacion");
		panel.add(lblNewLabel_1);

		txtImgXGeneracion = new JTextField();
		txtImgXGeneracion.setToolTipText(
				"Numero de imagenes que se tomaran como entrenamiento por cada generacion. Tenga en cuenta que entre mas imagenes, mas tiempo tomara el proceso.\r\n");
		panel.add(txtImgXGeneracion);

		JLabel lblNewLabel_2 = new JLabel("Tasa de aprendizaje(*)");
		panel.add(lblNewLabel_2);

		txtTasaDeAprendizaje = new JTextField();
		panel.add(txtTasaDeAprendizaje);
		
		JLabel lblNewLabel_41 = new JLabel("Momentum");
		panel.add(lblNewLabel_41);
		txtMomentum = new JTextField();
		panel.add(txtMomentum);

		rbtnNesterov = new JRadioButton("Tecnica de Nesterov");
		rbtnNesterov.setToolTipText("T\u00E9cnica para acelerar el proceso de optimizaci\u00F3n del entrenamiento. ");
		panel.add(rbtnNesterov);

		getContentPane().add(panel, BorderLayout.CENTER);

		JLabel lblEscribirValores = new JLabel("(*) Escribir valores decimales entre 0 y 1");
		lblEscribirValores.setHorizontalAlignment(SwingConstants.LEFT);
		lblEscribirValores.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
		panel.add(lblEscribirValores);

		btnCrearModelo = new JButton("Crear");
		btnCrearModelo.setActionCommand("C");
		btnCrearModelo.addActionListener(this);
		getContentPane().add(btnCrearModelo, BorderLayout.SOUTH);

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int gen = Integer.parseInt(txtGeneraciones.getText().trim());
		String name = txtNombre.getText();
		int imgXG = Integer.parseInt(txtImgXGeneracion.getText().trim());
		double tasaA = Double.parseDouble(txtTasaDeAprendizaje.getText().trim());
		double tasaM = Double.parseDouble(txtMomentum.getText().trim());
		if (gen <= 0) {
			JOptionPane.showMessageDialog(this, "El numero de generaciones no puede ser cero o estar vacio");
		}
		else if (name.trim().equals("")) {
			JOptionPane.showMessageDialog(this, "Elija un nombre para el modelo");
		}
		else if (imgXG<100) {
			JOptionPane.showMessageDialog(this, "El numero de imagenes por generacion debe ser al menos 100");
		}
		else if (tasaA>10 || tasaA<=0) {
			JOptionPane.showMessageDialog(this, "Defina una tasa de aprendizaje entre 0 y 10");

		}
		else if(tasaM < 0 ){
			
		}
		else{
		
		principal.crearModelo(txtNombre.getText(), gen,imgXG,tasaA,tasaM, rbtnNesterov.isSelected(), name);
		panelModelos.fillDLF();
		this.dispose();
		}
	}

}
