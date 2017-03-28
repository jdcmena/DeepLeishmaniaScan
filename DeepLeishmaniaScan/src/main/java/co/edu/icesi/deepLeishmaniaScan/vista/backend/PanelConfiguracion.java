package co.edu.icesi.deepLeishmaniaScan.vista.backend;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.lowagie.text.html.simpleparser.Img;

import co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos.Modelo;
import co.edu.icesi.deepLeishmaniaScan.logica.administradorModelos.RunConfigDTO;

public class PanelConfiguracion extends JPanel {

	private static final Logger log = LoggerFactory.getLogger(PanelConfiguracion.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -6938563747928859229L;
	
	private Gson gson;

	private Backend principal;
	/**
	 * 0: LR 1: DRL 2: MR 3: epoch num 4: imgxepoch
	 */
	private JTextField lr;
	private JTextField momentum;
	private JTextField genNum;
	private JTextField ImgxGen;
	private JCheckBox chkNesterov;
	private JButton btnGuardar;

	public PanelConfiguracion(Backend ventana) {
		
		gson = new Gson();
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumFractionDigits(5);
		nf.setMaximumIntegerDigits(1);
		NumberFormat intf = NumberFormat.getIntegerInstance();
		principal = ventana;
		this.setLayout(new GridLayout(0, 1));
		
		btnGuardar = new JButton("Guardar cambios");
		chkNesterov = new JCheckBox("Tecnica Nesterov");
		
		lr = new JTextField();
		momentum = new JTextField();
		genNum = new JTextField();
		ImgxGen = new JTextField();

		
		initPnlConfig();
	}

	private void initPnlConfig() {

		this.setBorder(BorderFactory.createTitledBorder("Configuracion de hiperparametros"));
		setLayout(new GridLayout(0, 1));
		add(new JLabel("Tasa de aprendizaje"));
		add(lr);

		add(new JLabel("momentum"));
		add(momentum);
		
		add(new JLabel("Numero de generaciones"));
		add(genNum);

		add(new JLabel("Numero de imagenes por generacion"));
		add(ImgxGen);		
		
		add(chkNesterov);
		
		add(btnGuardar, BorderLayout.SOUTH);

	}
	
	public void mostrarHiperparametros(Modelo modelo) throws FileNotFoundException{

		RunConfigDTO dto = new RunConfigDTO();
		BufferedReader br = new BufferedReader(new FileReader(modelo.getRunConfigPath()));
		dto = gson.fromJson(br, RunConfigDTO.class);
		fillTexts(dto);
	}
	
	private void fillTexts(RunConfigDTO dto){
		lr.setText(dto.getTasaAprendizaje()+"");
		momentum.setText(dto.getMomentum()+"");
		genNum.setText(dto.getGeneraciones()+"");
		ImgxGen.setText(dto.getImagenesPorGeneracion()+"");
		chkNesterov.setSelected(dto.isNesterov());
	}

}
