package co.edu.icesi.deepLeishmaniaScan.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JTextArea;

import org.apache.commons.math3.util.Decimal64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.minlog.Log;

import co.edu.icesi.deepLeishmaniaScan.logica.administradorImagenes.Filter;
import co.edu.icesi.deepLeishmaniaScan.logica.orquestador.Orquestador;
import co.edu.icesi.deepLeishmaniaScan.logica.procesamiento.IClasificacion;
import co.edu.icesi.deepLeishmaniaScan.logica.procesamiento.IEntrenamiento;

/**
 * Clase encargada de utilizar los scripts con los procedimientos de
 * entrenamiento y clasificacion. Referenciado de: Keras.io
 * 
 * @author jdcm
 *
 */
public class API implements IAPI {

	private static final Logger log = LoggerFactory.getLogger(API.class);

	
	private static final char OS = File.separatorChar;
	public static final String CLASIFY_SCRIPT = "python classify.py ";
	public static final String TRAIN_SCRIPT = "python train.py ";

	private IClasificacion clasificacion;
	private IEntrenamiento entrenamiento;
	private Orquestador orq;
	
	private double[] metricas;

	public API(Orquestador orq) {
		this.orq = orq;
	}

	@Override
	public void entrenar(String modelo, JTextArea consola) throws Exception {
		runCommand(TRAIN_SCRIPT + modelo + "runconfig.json", 1, consola);
	}

	@Override
	public void clasificar(String line, JTextArea consola) throws Exception {
		runCommand(CLASIFY_SCRIPT + line, 2, consola);
	}

	private void runCommand(String command, int flag, JTextArea consola) throws Exception {
		log.info("training script running...");
		double[] relevantOutput = new double[3];
		relevantOutput[0] = 0.0;
		relevantOutput[1] = 0.0;
		relevantOutput[2] = 0.0;

		Runtime r = Runtime.getRuntime();
		// ProcessBuilder pb = new ProcessBuilder("python","train.py",command);
		Process p = r.exec(command);
		final InputStream stream = p.getInputStream();
		new Thread(new Runnable() {

			public void run() {
				BufferedReader reader = null;

				reader = new BufferedReader(new InputStreamReader(stream));
				String line = null;
				try {
					String temp = "nothing";
					while ((line = reader.readLine()) != null) {
						consola.append(line+"\n");
						if (flag == 1) {
							if (line.contains("Global")) {
								temp = line.split(" ")[2];
								log.info(temp);
								relevantOutput[0] = Double.parseDouble(temp.substring(0, temp.length() - 2));
								Log.info("got accuracy");
							}
							if(line.contains("Sensitivity")){
								temp = line.split(" ")[1];
								relevantOutput[1] = Double.parseDouble(temp.substring(0, temp.length()-2));
								log.info(temp);
							}
							if(line.contains("Specificity")){
								temp = line.split(" ")[1];
								relevantOutput[2] = Double.parseDouble(temp.substring(0, temp.length()-2));
								log.info(temp);
							}
							if(line.contains("Saving model...")){
								metricas = relevantOutput;
								orq.notificar();
							}
						}
						if (flag == 2) {
							// TODO Classification [#CLASSID]
							
						}

					}
					
					consola.append(line);
				} catch (IOException io) {
					io.printStackTrace();
				}

				if (reader != null) {

				}
				
			}
			
			
		}).start();
	}

	@Override
	public void setClassificationModule(IClasificacion clasificacion) {
		this.clasificacion = clasificacion;
	}

	@Override
	public void setTrainingModule(IEntrenamiento entrenmaiento) {
		this.entrenamiento = entrenmaiento;
	}

	@Override
	public double[] getMetricasEntrenamiento() {
		return metricas;
	}

}
