package co.edu.icesi.deepLeishmaniaScan.vista.frontend;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class PanelImagen extends JPanel implements ActionListener{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7924271980165911848L;
	
	private JLabel lblImage;

	public PanelImagen(){
		
		this.setBorder(new TitledBorder(null, "Opciones de imagen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		GridBagLayout gbl_panelImage = new GridBagLayout();
		gbl_panelImage.columnWidths = new int[]{0, 0, 0};
		gbl_panelImage.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelImage.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelImage.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		this.setLayout(gbl_panelImage);
		
		lblImage = new JLabel();
		GridBagConstraints gbc_lblImage = new GridBagConstraints();
		gbc_lblImage.fill = GridBagConstraints.BOTH;
		gbc_lblImage.gridwidth = 2;
		gbc_lblImage.gridheight = 8;
		gbc_lblImage.insets = new Insets(0, 0, 5, 0);
		gbc_lblImage.gridx = 0;
		gbc_lblImage.gridy = 0;
		this.add(lblImage, gbc_lblImage);
		
		JButton btnCargarImagen = new JButton("Cargar Imagen");
		btnCargarImagen.setActionCommand("CI");
		btnCargarImagen.addActionListener(this);
		GridBagConstraints gbc_btnCargarImagen = new GridBagConstraints();
		gbc_btnCargarImagen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCargarImagen.insets = new Insets(0, 0, 0, 5);
		gbc_btnCargarImagen.gridx = 0;
		gbc_btnCargarImagen.gridy = 8;
		this.add(btnCargarImagen, gbc_btnCargarImagen);
		
		JButton btnClasificar = new JButton("Clasificar");
		btnClasificar.setActionCommand("C");
		btnClasificar.addActionListener(this);
		GridBagConstraints gbc_btnClasificar = new GridBagConstraints();
		gbc_btnClasificar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClasificar.gridx = 1;
		gbc_btnClasificar.gridy = 8;
		this.add(btnClasificar, gbc_btnClasificar);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String com = arg0.getActionCommand();
		
		switch(com){
		case "CI":
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.setDialogTitle("Seleccione la imagen que quiere clasificar");
			int returnVal = jfc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File selected = jfc.getSelectedFile();
				lblImage.setIcon(new ImageIcon(selected.getAbsolutePath()));
			}
			break;
		case "C":
			
			break;
		default:
			break;
		}
	}
	
}
