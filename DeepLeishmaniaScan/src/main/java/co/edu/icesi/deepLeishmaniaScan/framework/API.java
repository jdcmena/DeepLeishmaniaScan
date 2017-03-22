package co.edu.icesi.deepLeishmaniaScan.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JTextArea;

import co.edu.icesi.deepLeishmaniaScan.logica.orquestador.Orquestador;
import co.edu.icesi.deepLeishmaniaScan.logica.procesamiento.IClasificacion;
import co.edu.icesi.deepLeishmaniaScan.logica.procesamiento.IEntrenamiento;

import javax.swing.JTextArea;

public class API implements IAPI {

	private static final char OS = File.separatorChar;
	public static final String CLASIFY_SCRIPT = "python classify.py ";
	public static final String TRAIN_SCRIPT = "python train.py ";

	private IClasificacion clasificacion;
	private IEntrenamiento entrenamiento;
	private Orquestador orq;

	/*
	 * private StringWriter writer; private ScriptEngineManager manager; private
	 * ScriptContext context; private ScriptEngine engine;
	 */
	public API(Orquestador orq) {
		this.orq = orq;
		/*
		 * writer = new StringWriter(); // ouput will be stored here manager =
		 * new ScriptEngineManager(); context = new SimpleScriptContext();
		 * context.setWriter(writer); // configures output redirection engine =
		 * manager.getEngineByName("python");
		 */
	}

	@Override
	public void entrenar(String modelo, JTextArea consola) throws Exception {
		runCommand(TRAIN_SCRIPT + modelo + "runconfig.json", 1, consola);
	}

	@Override
	public void clasificar(String modelo, JTextArea consola) throws Exception {
		runCommand(CLASIFY_SCRIPT + modelo, 2, consola);
	}

	private void runCommand(String command, int flag, JTextArea consola) throws Exception {

		double[] relevantOutput = new double[2];

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
						System.out.println(line);
						consola.setText(consola.getText() + "\n" + line);
						if (flag == 1) {
							if (line.contains("Accuracy")) {
								temp = line.split(" ")[1];
								relevantOutput[0] = Double.parseDouble(temp.substring(0, temp.length() - 2));
							}
						}
						if (flag == 2) {
							// TODO Classification [#CLASSID]
						}

					}
					System.out.println(temp);
					System.out.println(line);
					consola.setText(consola.getText() + "\n" + line);
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

}
