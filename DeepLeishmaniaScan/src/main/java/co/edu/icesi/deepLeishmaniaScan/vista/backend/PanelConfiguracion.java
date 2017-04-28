package co.edu.icesi.deepLeishmaniaScan.vista.backend;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
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
	private JTextField txtLr;
	private JTextField txtMomentum;
	private JTextField txtGenNum;
	private JTextField txtImgxGen;
	private JCheckBox chkNesterov;
	private JButton btnGuardar;

	public PanelConfiguracion(Backend ventana) {
		gson = new Gson();
		// NumberFormat nf = NumberFormat.getPercentInstance();
		// nf.setMaximumFractionDigits(5);
		// nf.setMaximumIntegerDigits(1);
		// NumberFormat intf = NumberFormat.getIntegerInstance();
		principal = ventana;
		this.setLayout(new GridLayout(0, 1));

		btnGuardar = new JButton("Guardar cambios");
		chkNesterov = new JCheckBox("Tecnica Nesterov");

		txtLr = new JTextField();
		txtMomentum = new JTextField();
		txtGenNum = new JTextField();
		txtImgxGen = new JTextField();

		initPnlConfig();
	}

	private void initPnlConfig() {

		this.setBorder(BorderFactory.createTitledBorder("Configuracion de hiperparametros"));
		setLayout(new GridLayout(0, 1));
		add(new JLabel("Tasa de aprendizaje"));
		add(txtLr);

		add(new JLabel("momentum"));
		add(txtMomentum);

		add(new JLabel("Numero de generaciones"));
		add(txtGenNum);

		add(new JLabel("Numero de imagenes por generacion"));
		add(txtImgxGen);

		add(chkNesterov);

		add(btnGuardar, BorderLayout.SOUTH);

	}

	public void mostrarHiperparametros(Modelo modelo) throws FileNotFoundException {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					RunConfigDTO dto = new RunConfigDTO();
					BufferedReader br;

					br = new BufferedReader(new FileReader(modelo.getRunConfigPath()));

					dto = gson.fromJson(br, RunConfigDTO.class);
					fillTexts(dto);
					this.finalize();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		/*
		RunConfigDTO dto = new RunConfigDTO();
		BufferedReader br = new BufferedReader(new FileReader(modelo.getRunConfigPath()));
		dto = gson.fromJson(br, RunConfigDTO.class);
		fillTexts(dto);
		*/

	}

	private void fillTexts(RunConfigDTO dto) {
		txtLr.setText(dto.getTasaAprendizaje() + "");
		txtMomentum.setText(String.valueOf(dto.getMomentum()));
		txtGenNum.setText(dto.getGeneraciones() + "");
		txtImgxGen.setText(dto.getImagenesPorGeneracion() + "");
		chkNesterov.setSelected(dto.isNesterov());
		revalidate();
		repaint();
	}

}
