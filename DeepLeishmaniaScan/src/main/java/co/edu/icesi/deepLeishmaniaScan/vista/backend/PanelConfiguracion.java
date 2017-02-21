package co.edu.icesi.deepLeishmaniaScan.vista.backend;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.gson.Gson;

import co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos.Modelo;

public class PanelConfiguracion extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6938563747928859229L;

	private BackendView principal;
	/**
	 * 0: LR 1: DRL 2: MR 3: epoch num 4: imgxepoch
	 */
	private JFormattedTextField[] txts;
	private JCheckBox chkNesterov;
	private JButton btnGuardar;

	private JFileChooser jfc;

	public PanelConfiguracion(BackendView ventana) {
		
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumFractionDigits(5);
		nf.setMaximumIntegerDigits(1);
		NumberFormat intf = NumberFormat.getIntegerInstance();
		principal = ventana;
		this.setLayout(new GridLayout(0, 1));
		jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		btnGuardar = new JButton("Guardar cambios");
		chkNesterov = new JCheckBox("Tecnica Nesterov");
		txts = new JFormattedTextField[5];
		
		txts[0] = new JFormattedTextField(nf);
		txts[1] = new JFormattedTextField(nf);
		txts[2] = new JFormattedTextField(nf);
		txts[3] = new JFormattedTextField(intf);
		txts[4] = new JFormattedTextField(intf);
		
		initPnlConfig();
	}

	private void initPnlConfig() {

		this.setBorder(BorderFactory.createTitledBorder("Configuracion de hiperparametros"));
		setLayout(new GridLayout(0, 1));
		add(new JLabel("Tasa de aprendizaje"));
		add(txts[0]);

		add(new JLabel("Tasa de decadencia"));
		add(txts[1]);

		add(new JLabel("momentum"));
		add(txts[2]);

		add(chkNesterov);

		add(new JLabel("Numero de generaciones"));
		add(txts[3]);

		add(new JLabel("Numero de imagenes por generacion"));
		add(txts[4]);

		add(btnGuardar, BorderLayout.SOUTH);

	}
	
	public void mostrarHiperparametros(Modelo modelo){//TODO
		final Gson gson = new Gson();
		Object prop = gson.fromJson(modelo.getRunConfigPath(), Object.class); //malformedJson
		
		System.out.println(prop.getClass().toString());
		
	}
	
	public void showFileChooser() throws IOException{
		jfc.setDialogTitle("Seleccione la carpeta que contenga las imagenes nuevas");
		int returnVal = jfc.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			String selected = jfc.getSelectedFile().getAbsolutePath();
			principal.cargarNuevasImagenes(selected);
		}
		
	}

}
