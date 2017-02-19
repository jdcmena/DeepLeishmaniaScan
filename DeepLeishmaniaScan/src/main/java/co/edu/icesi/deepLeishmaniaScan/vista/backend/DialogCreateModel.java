package co.edu.icesi.deepLeishmaniaScan.vista.backend;

import java.awt.BorderLayout;
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
import java.awt.Font;
import javax.swing.SwingConstants;

public class DialogCreateModel extends JDialog implements ActionListener {

	private JTextField txtGeneraciones;
	private JTextField txtImgXGeneracion;
	private JTextField txtTasaDeAprendizaje;
	private JTextField txtDecadencia;
	private JTextField txtNombre;
	JRadioButton rbtnNesterov;

	private JButton btnCrearModelo;

	private BackendView principal;

	public DialogCreateModel(BackendView ventana) {
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
				"N\u00FAmero de im\u00E1genes que se tomar\u00E1n como entrenamiento por cada generaci\u00F3n. Tenga en cuenta que entre m\u00E1s im\u00E1genes, mas tiempo tomar\u00E1 el proceso.\r\n");
		panel.add(txtImgXGeneracion);

		JLabel lblNewLabel_2 = new JLabel("Tasa de aprendizaje(*)");
		panel.add(lblNewLabel_2);

		txtTasaDeAprendizaje = new JTextField();
		panel.add(txtTasaDeAprendizaje);

		JLabel lblNewLabel_3 = new JLabel("tasa de decadencia(*)");
		panel.add(lblNewLabel_3);

		txtDecadencia = new JTextField();
		panel.add(txtDecadencia);

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
		// TODO verificar inputs
		if (txtGeneraciones.getText().equals("") || Integer.parseInt(txtGeneraciones.getText())==0) {
			JOptionPane.showMessageDialog(this, "El numero de generaciones no puede ser cero o estar vac�o");
		}
		else if (txtNombre.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Elija un nombre para el modelo");
		}
		else if (txtImgXGeneracion.getText().equals("") || Integer.parseInt(txtGeneraciones.getText())<100) {
			JOptionPane.showMessageDialog(this, "El numero de imagenes por generacion debe ser al menos 100");
		}
		else if (txtTasaDeAprendizaje.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Defina una tasa de aprendizaje");

		}
		else if (txtDecadencia.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Defina una tasa de decadencia");
		}
		else{
			String[] runconfig = {txtGeneraciones.getText(),txtImgXGeneracion.getText()
					,txtTasaDeAprendizaje.getText(),txtDecadencia.getText()
					,rbtnNesterov.isSelected()+"", txtNombre.getText()};
		principal.crearModelo(txtNombre.getText(), runconfig);
		}
	}

}